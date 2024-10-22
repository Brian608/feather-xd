package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.config.RabbitMQConfig;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.ProductOrderStateEnum;
import org.feather.xd.enums.StockTaskStateEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.feign.ProductOrderFeignService;
import org.feather.xd.model.ProductDO;
import org.feather.xd.mapper.ProductMapper;
import org.feather.xd.model.ProductMessage;
import org.feather.xd.model.ProductTaskDO;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.request.LockProductRequest;
import org.feather.xd.request.OrderItemRequest;
import org.feather.xd.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.service.IProductTaskService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.ProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements IProductService {

    private final ProductMapper productMapper;

    private final IProductTaskService  productTaskService;

    private final RabbitTemplate rabbitTemplate;

    private  final RabbitMQConfig rabbitMQConfig;

    private final ProductOrderFeignService orderFeignService;

    @Override
    public Page<ProductVO> pageProduct(ProductQuery query) {
        Page<ProductDO> page=new Page<>(query.getPageNo(),query.getPageSize());
        LambdaQueryWrapper<ProductDO> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ProductDO::getCreateTime);
        Page<ProductDO> productDOPage= this.page(page,queryWrapper);
        List<ProductDO> records = productDOPage.getRecords();
        records.forEach(p->p.setStock(p.getStock()-p.getLockStock()));
        Page<ProductVO> productVOPage = new Page<>(query.getPageNo(), query.getPageSize(), productDOPage.getTotal());
        productVOPage.setRecords(BeanUtil.copyToList(records, ProductVO.class));
        return productVOPage;
    }

    @Override
    public ProductVO findDetailById(long productId) {
        ProductDO productDO = Optional.ofNullable(this.getById(productId)).orElseThrow(() -> new BizException("商品不存在"));
        return BeanUtil.copyProperties(productDO, ProductVO.class);
    }

    @Override
    public List<ProductVO> findProductsByIdBatch(List<Long> productIdList) {
        List<ProductDO> productDOList =  this.list(new LambdaQueryWrapper<ProductDO>().in(ProductDO::getId,productIdList));

        return BeanUtil.copyToList(productDOList, ProductVO.class);
    }

    @Override
    public Boolean lockProductStock(LockProductRequest request) {
        String orderOutTradeNo = request.getOrderOutTradeNo();
        List<OrderItemRequest> orderItemList = request.getOrderItemList();
        List<Long> productIdList = orderItemList.stream().map(OrderItemRequest::getProductId).collect(Collectors.toList());
        List<ProductVO> productVOList = this.findProductsByIdBatch(productIdList);
        Map<Long, ProductVO> productVOMap = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));
        for (OrderItemRequest orderItemRequest : orderItemList) {
            //锁定商品记录
            int effectRows = this.productMapper.lockProductStock(orderItemRequest.getProductId(), orderItemRequest.getBuyNum());
            if (effectRows!=1){
                throw new BizException(BizCodeEnum.ORDER_CONFIRM_LOCK_PRODUCT_FAIL);
            }else {
                //插入商品 task
                ProductVO productVO = productVOMap.get(orderItemRequest.getProductId());
                ProductTaskDO taskDO=new ProductTaskDO();
                taskDO.setBuyNum(orderItemRequest.getBuyNum());
                taskDO.setProductId(orderItemRequest.getProductId());
                taskDO.setLockState(StockTaskStateEnum.LOCK.name());
                taskDO.setOutTradeNo(orderOutTradeNo);
                taskDO.setProductName(productVO.getTitle());
                boolean result = productTaskService.save(taskDO);
                log.info("商品库存锁定-插入商品product_task结果:【{}】,taskInfo:[{}]",
                        result,taskDO);
                //发送延迟消息
                ProductMessage productMessage=new ProductMessage();
                productMessage.setOutTradeNo(orderOutTradeNo);
                productMessage.setTaskId(taskDO.getId());
                rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(),rabbitMQConfig.getStockReleaseDelayRoutingKey(),productMessage);
                log.info("商品库存锁定信息延迟消息发送成功:{}",productMessage);

            }
        }

        return true;
    }

    @Override
    public boolean releaseProductStock(ProductMessage productMessage) {
        ProductTaskDO taskDO = Optional.ofNullable(productTaskService.getById(productMessage.getTaskId())).orElseThrow(() -> new BizException("工作单不存在"));
        //lock  状态才处理
        if (StockTaskStateEnum.LOCK.name().equalsIgnoreCase(taskDO.getLockState())){
            JsonResult<String> jsonResult = orderFeignService.queryProductOrderState(productMessage.getOutTradeNo());
            if (jsonResult.getCode().equals(200)){
                String state = jsonResult.getData();
                if (ProductOrderStateEnum.NEW.name().equalsIgnoreCase(state)){
                    //状态是new 新建状态，则返回消息队列，重新投递
                    log.warn("订单状态是NEW,返回给消息队列，重新投递:{}",productMessage);
                    return false;
                }
                //如果是已支付状态
                if (ProductOrderStateEnum.PAY.name().equalsIgnoreCase(state)){
                    //如果已支付，修改task 状态为finish
                    taskDO.setLockState(StockTaskStateEnum.FINISH.name());
                    productTaskService.updateById(taskDO);
                    log.info("订单已经支付，修改库存锁定工作单FINISH状态:{}",productMessage);
                    return true;
                }

            }
            //订单不存在。或者被取消，确认消息，修改task 状态为CANCEL，恢复优惠券使用记录为NEW
            log.warn("订单不存在，或者订单被取消，确认消息,修改task状态为CANCEL,恢复优惠券使用记录为NEW,message:{}",productMessage);
            taskDO.setLockState(StockTaskStateEnum.CANCEL.name());
            productTaskService.updateById(taskDO);
            //恢复商品库存，集锁定库存的值减去当前购买的值
            productMapper.unlockProductStock(taskDO.getProductId(),taskDO.getBuyNum());

            return true;

        }else {
            log.warn("工作单状态不是LOCK,state={},消息体={}",taskDO.getLockState(),productMessage);
            return true;
        }

    }
}
