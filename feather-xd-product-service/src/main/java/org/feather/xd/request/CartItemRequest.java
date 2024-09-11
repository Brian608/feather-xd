package org.feather.xd.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: CartItemRequest
 * @author: feather
 * @description:
 * @since: 2024-09-10 11:04
 * @version: 1.0
 */
@ApiModel
@Data
public class CartItemRequest implements Serializable {

    @ApiModelProperty(value = "商品id",example = "11")
    @NotNull(message = "商品id不能为空")
    private long productId;

    @ApiModelProperty(value = "购买数量",example = "1")
    @Min(value = 1,message = "最少一件商品")
    private int buyNum;
}