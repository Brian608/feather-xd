package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: ConfirmOrderRequest
 * @author: feather
 * @description:
 * @since: 2024-09-12 10:00
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmOrderRequest {

    /**
     * 购物车使用的优惠券，集满减劵
     *
     * 注意：如果传空或者小于0,则不用优惠券
     */
    @JsonProperty("coupon_record_id")
    private Long couponRecordId;


    /**
     * 最终购买的商品列表
     * 传递id，购买数量从购物车中读取
     */
    @JsonProperty("product_ids")
    @NotEmpty(message = "最终购买的商品列表不能为空")
    private List<Long> productIdList;


    /**
     * 支付方式
     */
    @JsonProperty("pay_type")
    @NotBlank(message = "支付方式不能为空")
    private String payType;


    /**
     * 端类型
     */
    @NotBlank(message = "端类型不能为空")
    @JsonProperty("client_type")
    private String clientType;


    /**
     * 收货地址id
     */
    @NotNull(message = "收货地址不能为空")
    @JsonProperty("address_id")
    private long addressId;


    /**
     * 总价格，前端传递，后端需要验价
     */
    @JsonProperty("total_amount")
    @NotNull(message = "总价格不能为空")
    private BigDecimal totalAmount;


    /**
     * 实际支付的价格，
     * 如果用了优惠劵，则是减去优惠券后端价格，如果没的话，则是totalAmount一样
     */
    @JsonProperty("real_pay_amount")
    @NotNull(message = "实际支付价格不能为空")
    private BigDecimal realPayAmount;


    /**
     * 防重令牌
     */
    @JsonProperty("token")
    private String token;

}
