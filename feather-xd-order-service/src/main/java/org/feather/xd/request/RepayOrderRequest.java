package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: RepayOrderRequest
 * @author: feather
 * @description:
 * @since: 2025-02-09 11:25
 * @version: 1.0
 */
@Data
public class RepayOrderRequest {


    /**
     * 订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;



    /**
     * 支付类型- 微信-银行卡-支付宝
     */
    @JsonProperty("pay_type")
    private String payType;



    /**
     * 订单号
     */
    @JsonProperty("client_type")
    private String clientType;
}
