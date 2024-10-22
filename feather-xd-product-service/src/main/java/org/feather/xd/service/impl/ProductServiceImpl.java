package org.feather.xd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.StockTaskStateEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.model.ProductDO;
import org.feather.xd.mapper.ProductMapper;
import org.feather.xd.model.ProductTaskDO;
import org.feather.xd.query.ProductQuery;
import org.feather.xd.request.LockProductRequest;
import org.feather.xd.request.OrderItemRequest;
import org.feather.xd.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.feather.xd.service.IProductTaskService;
import org.feather.xd.vo.ProductVO;
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



            }

        }

        return true;
    }
}
