package org.feather.xd.enums;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.enums
 * @className: ProductOrderStateEnum
 * @author: feather
 * @description:
 * @since: 2024-09-11 18:16
 * @version: 1.0
 */
public enum ProductOrderStateEnum {
    /**
     * 未支付订单
     */
    NEW,


    /**
     * 已经支付订单
     */
    PAY,

    /**
     * 超时取消订单
     */
    CANCEL;
}
