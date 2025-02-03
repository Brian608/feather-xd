package org.feather.xd.constant;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.constant
 * @className: TimeConstant
 * @author: feather
 * @description:
 * @since: 2025-02-03 10:07
 * @version: 1.0
 */
public interface TimeConstant  {

    /**
     *
     * 支付订单的有效时长，超过未支付则关闭订单
     *
     * 订单超时，毫秒，默认30分钟
     */
    //public static final long ORDER_PAY_TIMEOUT_MILLS = 30*60*1000;
    long ORDER_PAY_TIMEOUT_MILLS = 5*60*1000;
}
