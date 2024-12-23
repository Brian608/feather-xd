package org.feather.xd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: PayInfoVO
 * @author: feather
 * @description:
 * @since: 2024-12-23 21:05
 * @version: 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayInfoVO  implements Serializable {
    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 订单总金额
     */
    private BigDecimal payFee;

    /**
     * 支付类型 微信-支付宝-银行-其他
     */
    private String payType;

    /**
     * 端类型 APP/H5/PC
     */
    private String clientType;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;


    /**
     * 订单支付超时时间，毫秒
     */
    private long orderPayTimeoutMills;



}
