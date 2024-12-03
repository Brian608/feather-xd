package org.feather.xd.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.feather.xd.config.RabbitMQConfig;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.*;
import org.feather.xd.exception.BizException;
import org.feather.xd.feign.CouponFeignService;
import org.feather.xd.feign.ProductOrderFeignService;
import org.feather.xd.feign.UserFeignService;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.mapper.ProductOrderMapper;
import org.feather.xd.model.LoginUser;
import org.feather.xd.model.OrderMessage;
import org.feather.xd.model.ProductOrderDO;
import org.feather.xd.model.ProductOrderItemDO;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.request.LockCouponRecordRequest;
import org.feather.xd.request.LockProductRequest;
import org.feather.xd.request.OrderItemRequest;
import org.feather.xd.service.IProductOrderItemService;
import org.feather.xd.service.IProductOrderService;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.AddressVO;
import org.feather.xd.vo.CartItemVO;
import org.feather.xd.vo.CouponRecordVO;
import org.feather.xd.vo.ProductOrderAddressVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-09-11
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements IProductOrderService {
    private final UserFeignService userFeignService;

    private  final ProductOrderFeignService productOrderFeignService;

    private final CouponFeignService couponFeignService;

    private  final IProductOrderItemService productOrderItemService;

    private final RabbitMQConfig rabbitMQConfig;

    private final RabbitTemplate rabbitTemplate;


    @Override
    public JsonResult confirmOrder(ConfirmOrderRequest request) {
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);

        ProductOrderAddressVO addressVO=this.getUserAddress( request.getAddressId());
        log.info("收获地址信息:[{}]",addressVO);
        List<Long> productIdList = request.getProductIdList();
        JsonResult<List<CartItemVO>> jsonResult=  productOrderFeignService.confirmOrderCartItem(productIdList);
        if (!jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
            List<CartItemVO> cartItemVOList = jsonResult.getData();
            if (CollectionUtils.isEmpty(cartItemVOList)){
                //购物车商品数据不存在
                throw  new BizException(BizCodeEnum.ORDER_CONFIRM_CART_ITEM_NOT_EXIST);
            }
            //验证价格 ，减去商品优惠券
            this.checkPrice(cartItemVOList,request);
            //锁定优惠券
            this.lockCouponRecords(request,orderOutTradeNo);
            //锁定库存
            this.lockProductStocks(cartItemVOList,orderOutTradeNo);

            //创建订单
            ProductOrderDO productOrderDO = this.saveProductOrder(request, loginUser, orderOutTradeNo, addressVO);
            //创建订单项
            this.saveProductOrderItems(orderOutTradeNo,productOrderDO.getId(),cartItemVOList);
            //发送延迟消息， 用于自动关单
            OrderMessage orderMessage=new OrderMessage();
            orderMessage.setOutTradeNo(orderOutTradeNo);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getEventExchange(),rabbitMQConfig.getOrderCloseDelayRoutingKey(),orderMessage);

            //创建支付

        }
        return JsonResult.buildSuccess();


    }

    private void saveProductOrderItems(String orderOutTradeNo, Long orderId, List<CartItemVO> cartItemVOList) {
        List<ProductOrderItemDO> orderItemDOList = cartItemVOList.stream().map(
                obj->{
                    ProductOrderItemDO itemDO = new ProductOrderItemDO();
                    itemDO.setBuyNum(obj.getBuyNum());
                    itemDO.setProductId(obj.getProductId());
                    itemDO.setProductImg(obj.getProductImg());
                    itemDO.setProductName(obj.getProductTitle());

                    itemDO.setOutTradeNo(orderOutTradeNo);

                    //单价
                    itemDO.setAmount(obj.getAmount());
                    //总价
                    itemDO.setTotalAmount(obj.getTotalAmount());
                    itemDO.setProductOrderId(orderId);
                    return itemDO;
                }
        ).collect(Collectors.toList());


        productOrderItemService.saveBatch(orderItemDOList);

    }

    /**
     * description: 创建订单
     * @param request
     * @param loginUser
     * @param orderOutTradeNo
     * @param addressVO
     * @return
     * @author: feather
     * @since: 2024-11-09 12:36
     **/

    private ProductOrderDO saveProductOrder(ConfirmOrderRequest request, LoginUser loginUser, String orderOutTradeNo, ProductOrderAddressVO addressVO) {
        ProductOrderDO productOrderDO = new ProductOrderDO();
        productOrderDO.setUserId(loginUser.getId());
        productOrderDO.setHeadImg(loginUser.getHeadImg());
        productOrderDO.setNickname(loginUser.getName());

        productOrderDO.setOutTradeNo(orderOutTradeNo);
        productOrderDO.setCreateTime(new Date());
        productOrderDO.setDel(0);
        productOrderDO.setOrderType(ProductOrderTypeEnum.DAILY.name());

        //实际支付的价格
        productOrderDO.setPayAmount(request.getRealPayAmount());

        //总价，未使用优惠券的价格
        productOrderDO.setTotalAmount(request.getTotalAmount());
        productOrderDO.setState(ProductOrderStateEnum.NEW.name());
        productOrderDO.setPayType(ProductOrderPayTypeEnum.valueOf(request.getPayType()).name());

        productOrderDO.setReceiverAddress(JSON.toJSONString(addressVO));

       this.save(productOrderDO);
        return productOrderDO;

    }

    /**
     * description: 锁定库存
     * @param cartItemVOList
     * @param orderOutTradeNo
     * @return
     * @author: feather
     * @since: 2024-10-29 7:49
     **/
    private void lockProductStocks(List<CartItemVO> cartItemVOList, String orderOutTradeNo) {
        List<OrderItemRequest> orderItemRequestList = cartItemVOList.stream().map(item -> {
            OrderItemRequest orderItemRequest = new OrderItemRequest();
            orderItemRequest.setBuyNum(item.getBuyNum());
            orderItemRequest.setProductId(item.getProductId());
            return orderItemRequest;
        }).collect(Collectors.toList());
        LockProductRequest lockProductRequest=new LockProductRequest();
        lockProductRequest.setOrderOutTradeNo(orderOutTradeNo);
        lockProductRequest.setOrderItemList(orderItemRequestList);

       JsonResult<Boolean> jsonResult= productOrderFeignService.lockProductStock(lockProductRequest);
        if (!jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
            log.error("锁定商品库存失败：{}",lockProductRequest);
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_LOCK_PRODUCT_FAIL);
        }

    }

    /**
     * description: 锁定优惠券
     * @param request
     * @param orderOutTradeNo
     * @return
     * @author: feather
     * @since: 2024-10-29 7:34
     **/
    private void lockCouponRecords(ConfirmOrderRequest request, String orderOutTradeNo) {
        List<Long> lockCouponRecords=new ArrayList<>();
        if (request.getCouponRecordId()>0){
            lockCouponRecords.add(request.getCouponRecordId());
            LockCouponRecordRequest lockCouponRecordRequest=new LockCouponRecordRequest();
            lockCouponRecordRequest.setOrderOutTradeNo(orderOutTradeNo);
            lockCouponRecordRequest.getLockCouponRecordIds().addAll(lockCouponRecords);
            //发起锁定优惠券请求
            JsonResult<Object> jsonResult = couponFeignService.lockCouponRecords(lockCouponRecordRequest);
            if (!jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
                throw new BizException(BizCodeEnum.COUPON_RECORD_LOCK_FAIL);
            }
        }
    }

    /**
     * description: 验证价格，
     * 1：统计全部商品的价格
     * 2：获取优惠券(判断是否满足优惠券的条件)，总价再减去优惠券的价格，就是最终的价格
     * @param cartItemVOList
     * @param request
     * @return
     * @author: feather
     * @since: 2024-10-26 13:11
     **/

    private void checkPrice(List<CartItemVO> cartItemVOList, ConfirmOrderRequest request) {
        //统计价格
        BigDecimal realPayAmount=new BigDecimal(0);
        if (CollectionUtils.isNotEmpty(cartItemVOList)){
            for (CartItemVO cartItemVO : cartItemVOList) {
                realPayAmount=realPayAmount.add(cartItemVO.getTotalAmount());
            }
        }
        //获取优惠券 判断是否可以使用
      CouponRecordVO couponRecordVO= getCatCouponRecord(request.getCouponRecordId());
        //计算购物车价格，是否满足优惠券减免条件
        if (Objects.nonNull(couponRecordVO)){
            //判断是否满足优惠券减免条件
            BigDecimal conditionPrice=couponRecordVO.getConditionPrice();
            if (realPayAmount.compareTo(conditionPrice)<0){
                throw new BizException(BizCodeEnum.ORDER_CONFIRM_COUPON_FAIL);
            }
            if (conditionPrice.compareTo(realPayAmount)>0){
                realPayAmount=BigDecimal.ZERO;
            }else {
                realPayAmount=realPayAmount.subtract(conditionPrice);
            }
            if (realPayAmount.compareTo(request.getRealPayAmount())!=0){
                log.error("订单验价失败:[{}]",request);
                throw  new BizException(BizCodeEnum.ORDER_CONFIRM_PRICE_FAIL);
            }
        }
   }

    /**
     * description: 获取优惠券
     * @param couponRecordId
     * @return {@link CouponRecordVO}
     * @author: feather
     * @since: 2024-10-26 13:17
     **/
    private CouponRecordVO getCatCouponRecord(Long couponRecordId) {
        if (Objects.isNull(couponRecordId)){
            return null;
        }
        JsonResult<CouponRecordVO> jsonResult = couponFeignService.findUserCouponRecordById(couponRecordId);
        if (!jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_COUPON_FAIL);
        }
        CouponRecordVO couponRecordVO = jsonResult.getData();
        if(!couponAvailable(couponRecordVO)){
            log.error("优惠券使用失败");
            throw new BizException(BizCodeEnum.COUPON_UNAVAILABLE);
        }
        return couponRecordVO;

    }
    /**
     * description: 判断优惠券是否可用
     * @param couponRecordVO
     * @return {@link boolean}
     * @author: feather
     * @since: 2024-10-26 13:31
     **/

    private boolean couponAvailable(CouponRecordVO couponRecordVO) {
        if(couponRecordVO.getUseState().equalsIgnoreCase(CouponStateEnum.NEW.name())){
            long currentTimestamp = CommonUtil.getCurrentTimestamp();
            long end = couponRecordVO.getEndTime().getTime();
            long start = couponRecordVO.getStartTime().getTime();
            return currentTimestamp >= start && currentTimestamp <= end;
        }
        return false;

    }

    /**
     * description: 获取收获地址详情
     * @param addressId
     * @return {@link ProductOrderAddressVO}
     * @author: feather
     * @since: 2024-10-24 21:33
     **/
    private ProductOrderAddressVO getUserAddress(long addressId) {
        JsonResult<AddressVO> jsonResult = userFeignService.detail(addressId);
        if (!jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
            log.error("获取收获地址失败，msg:[{}]",jsonResult);
            throw  new BizException(BizCodeEnum.ADDRESS_NO_EXITS);
        }
        AddressVO addressVO = jsonResult.getData();
        ProductOrderAddressVO productOrderAddressVO = new ProductOrderAddressVO();
        BeanUtils.copyProperties(addressVO,productOrderAddressVO);
        return productOrderAddressVO;
    }



    @Override
    public String queryProductOrderState(String outTradeNo) {
        ProductOrderDO productOrderDO = Optional.ofNullable(this.baseMapper.selectOne(new LambdaQueryWrapper<ProductOrderDO>().eq(ProductOrderDO::getOutTradeNo, outTradeNo))).orElseThrow(() -> new BizException(BizCodeEnum.ORDER_CONFIRM_NOT_EXIST));
        return productOrderDO.getState();
    }

    @Override
    public Boolean closeProductOrder(OrderMessage orderMessage) {
        ProductOrderDO productOrderDO =
                this.baseMapper.selectOne(
                        new LambdaQueryWrapper<ProductOrderDO>()
                                .eq(ProductOrderDO::getOutTradeNo, orderMessage.getOutTradeNo()));
        if (Objects.isNull(productOrderDO)) {
            log.warn("直接确认消息，订单不存在:[{}]",orderMessage);
            return  true;
        }
        if (ProductOrderStateEnum.PAY.name().equalsIgnoreCase(productOrderDO.getState())){
            log.info("直接确认消息，订单已支付:[{}]",orderMessage);
            return true;
        }
        //TODO向第三方查询订单是否真的支付
        String payResult="";
        if(StringUtils.isBlank(payResult)){
            updateOrderPayState(productOrderDO.getOutTradeNo(),ProductOrderStateEnum.CANCEL.name(),ProductOrderStateEnum.NEW.name());
            log.info("结果为空，则未支付成功，本地取消订单:{}",orderMessage);
            return true;
        }else {
            //支付成功，主动的把订单状态改成UI就支付，造成该原因的情况可能是支付通道回调有问题
            log.warn("支付成功，主动的把订单状态改成UI就支付，造成该原因的情况可能是支付通道回调有问题:{}",orderMessage);
            updateOrderPayState(productOrderDO.getOutTradeNo(),ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.NEW.name());
            return true;
        }
    }
    private   void  updateOrderPayState(String outTradeNo,  String newState,  String oldState){
        this.update(
                new LambdaUpdateWrapper<ProductOrderDO>().eq(ProductOrderDO::getOutTradeNo, outTradeNo).eq(ProductOrderDO::getState, oldState)
                        .set(ProductOrderDO::getState, newState)
        );

    }
}
