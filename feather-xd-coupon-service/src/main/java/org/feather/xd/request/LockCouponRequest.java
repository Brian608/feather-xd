package org.feather.xd.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: LockCouponRequest
 * @author: feather
 * @description:
 * @since: 2024-09-18 15:52
 * @version: 1.0
 */
@ApiModel(value = "优惠券锁定对象",description = "优惠券锁定对象")
@Data
public class LockCouponRequest implements Serializable {

    /**
     * 优惠券记录id列表
     */
    @ApiModelProperty(value = "优惠券记录id列表",example = "[1,2,3]")
    private List<Long> lockCouponRecordIds;


    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号",example = "3234fw234rfd232")
    private String orderOutTradeNo;
}
