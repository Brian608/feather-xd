package org.feather.xd.component;

import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import lombok.RequiredArgsConstructor;
import org.feather.xd.enums.ProductOrderPayTypeEnum;
import org.feather.xd.enums.ProductOrderStateEnum;
import org.feather.xd.vo.PayInfoVO;
import org.springframework.stereotype.Component;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.component
 * @className: PayFactory
 * @author: feather
 * @description:
 * @since: 2024-12-23 21:29
 * @version: 1.0
 */
@RequiredArgsConstructor
@Component
public class PayFactory {
    private  final  AlipayStrategy alipayStrategy;
    private final WechatPayStrategy wechatPayStrategy;


    /**
     * description: 创建支付，简单工厂模式
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 22:00
     **/
    public String pay(PayInfoVO payInfoVO){
        String payType = payInfoVO.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)){
            //支付宝支付
            PayStrategyContext payStrategyContext=new PayStrategyContext(alipayStrategy);
            return payStrategyContext.executeUnifiedOrder(payInfoVO);
        }else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)){
            //微信支付  暂未实现
            PayStrategyContext payStrategyContext=new PayStrategyContext(wechatPayStrategy);
            return payStrategyContext.executeUnifiedOrder(payInfoVO);
        }
        return "";
    }
    /**
     * description: 查询订单支付状态
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 22:02
     **/

    public String queryPaySuccess(PayInfoVO payInfoVO){
        String payType = payInfoVO.getPayType();
        if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)){
            //支付宝支付
            PayStrategyContext payStrategyContext=new PayStrategyContext(alipayStrategy);
            return payStrategyContext.executedQueryOrderPaySuccess(payInfoVO);
        }else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)){
            //微信支付  暂未实现
            PayStrategyContext payStrategyContext=new PayStrategyContext(wechatPayStrategy);
            return payStrategyContext.executedQueryOrderPaySuccess(payInfoVO);
        }
        return "";
    }

}
