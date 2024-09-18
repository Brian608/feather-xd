package org.feather.xd.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author feather
 * @since 2024-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("coupon_task")
public class CouponTaskDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 优惠券记录id
     */
    private Long couponRecordId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 锁定状态 锁定LOCK-完成FINISH 取消CANCEL
     */
    private String lockState;


}
