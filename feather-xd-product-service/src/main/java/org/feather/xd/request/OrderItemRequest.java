package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: OrderItemRequest
 * @author: feather
 * @description:
 * @since: 2024-10-12 15:10
 * @version: 1.0
 */
@ApiModel(value = "商品子项")
@Data
public class OrderItemRequest {


    @ApiModelProperty(value = "商品id",example = "1")
    @JsonProperty("product_id")
    private long productId;

    @ApiModelProperty(value = "购买数量",example = "2")
    @JsonProperty("buy_num")
    private int buyNum;
}