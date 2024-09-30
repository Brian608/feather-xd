package org.feather.xd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.enums.BizCodeEnum;
import org.feather.xd.enums.ClientType;
import org.feather.xd.enums.ProductOrderPayTypeEnum;
import org.feather.xd.exception.BizException;
import org.feather.xd.model.ProductOrderDO;
import org.feather.xd.mapper.ProductOrderMapper;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.service.IProductOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author feather
 * @since 2024-09-11
 */
@Slf4j
@Service
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO> implements IProductOrderService {


    @Override
    public void confirmOrder(ConfirmOrderRequest request,HttpServletResponse response) {

        String clientType = request.getClientType();
        String payType = request.getPayType();
        //如果是支付宝支付  都是跳转网页 ，APP除外
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)){
            log.info("创建支付宝订单成功:[{}]",request);

            //H5支付
            if (ClientType.H5.name().equalsIgnoreCase(clientType)){
                writeData(response);
            } else if (ClientType.APP.name().equalsIgnoreCase(clientType)) {
                //TODO APP SDK支付
            }

        } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
            //TODO 微信支付
        }

    }
    private void writeData(HttpServletResponse response) {

        try {
            response.setContentType("text/html;charset=UTF8");
            response.getWriter().write("请支付");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("写出Html异常:[{}]", e.getMessage());
        }
    }

    @Override
    public String queryProductOrderState(String outTradeNo) {
        ProductOrderDO productOrderDO = Optional.ofNullable(this.baseMapper.selectOne(new LambdaQueryWrapper<ProductOrderDO>().eq(ProductOrderDO::getOutTradeNo, outTradeNo))).orElseThrow(() -> new BizException(BizCodeEnum.ORDER_CONFIRM_NOT_EXIST));
        return productOrderDO.getState();
    }
}
