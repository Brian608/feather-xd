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
@TableName("product_task")
public class ProductTaskDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 商品标题
     */
    private String productName;

    /**
     * 锁定状态锁定LOCK  完成FINISH-取消CANCEL
     */
    private String lockState;

    private String outTradeNo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
