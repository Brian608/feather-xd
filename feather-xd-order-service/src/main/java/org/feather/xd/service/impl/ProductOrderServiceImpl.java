package org.feather.xd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.feign.ProductOrderFeignService;
import org.feather.xd.feign.UserFeignService;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.mapper.ProductOrderMapper;
import org.feather.xd.model.LoginUser;
import org.feather.xd.model.ProductOrderDO;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.service.IProductOrderService;
import org.feather.xd.util.CommonUtil;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.AddressVO;
import org.feather.xd.vo.CartItemVO;
import org.feather.xd.vo.ProductOrderAddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        }
        return JsonResult.buildSuccess();


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
}
