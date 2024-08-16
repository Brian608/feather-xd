package org.feather.xd.model;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 电商-公司收发货地址表
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("address")
public class AddressDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    private String receiveName;

    /**
     * 收货人电话
     */
    private String phone;

    /**
     * 省/直辖市
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址
     */
    private String detailAddress;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
