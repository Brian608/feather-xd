package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: CouponVO
 * @author: feather
 * @description:
 * @since: 2024-08-21 20:21
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CouponVO  implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 优惠卷类型[NEW_USER注册赠券，TASK任务卷，PROMOTION促销劵]
     */
    private String category;



    /**
     * 优惠券图片
     */
    @JsonProperty("coupon_img")
    private String couponImg;

    /**
     * 优惠券标题
     */
    @JsonProperty("coupon_title")
    private String couponTitle;

    /**
     * 抵扣价格
     */
    private BigDecimal price;

    /**
     * 每人限制张数
     */
    @JsonProperty("user_limit")
    private Integer userLimit;

    /**
     * 优惠券开始有效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",locale = "zh",timezone = "GMT+8")
    @JsonProperty("start_time")
    private Date startTime;

    /**
     * 优惠券失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",locale = "zh",timezone = "GMT+8")
    @JsonProperty("end_time")
    private Date endTime;

    /**
     * 优惠券总量
     */
    @JsonProperty("publish_count")
    private Integer publishCount;

    /**
     * 库存
     */
    private Integer stock;



    /**
     * 满多少才可以使用
     */
    @JsonProperty("condition_price")
    private BigDecimal conditionPrice;
}
