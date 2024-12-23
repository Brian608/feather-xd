package org.feather.xd.component;

import org.feather.xd.vo.PayInfoVO;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.component
 * @className: PayStrategyContext
 * @author: feather
 * @description:
 * @since: 2024-12-23 21:13
 * @version: 1.0
 */
public class PayStrategyContext {
    private PayStrategy payStrategy;
    public PayStrategyContext (PayStrategy payStrategy){
        this.payStrategy=payStrategy;
    }

    /**
     * description: 根据支付策略，调用不同的支付
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 21:15
     **/

    public String executeUnifiedOrder(PayInfoVO payInfoVO){
        return payStrategy.unifiedOrder(payInfoVO);
    }

    /**
     * description: 根据支付的策略，调用不同的查询订单支持状态
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 21:16
     **/
    public String executedQueryOrderPaySuccess(PayInfoVO payInfoVO){
        return payStrategy.queryOrderPaySuccess(payInfoVO);
    }
}
