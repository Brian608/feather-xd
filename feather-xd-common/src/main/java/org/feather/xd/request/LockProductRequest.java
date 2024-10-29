package org.feather.xd.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.request
 * @className: LockProductRequest
 * @author: feather
 * @description:
 * @since: 2024-10-12 15:10
 * @version: 1.0
 */
@ApiModel(value = "商品锁定对象",description = "商品锁定对象协议")
@Data
public class LockProductRequest {

    @ApiModelProperty(value = "订单id",example = "12312312312")
    @JsonProperty("order_out_trade_no")
    private String orderOutTradeNo;

    @ApiModelProperty(value = "订单项")
    @JsonProperty("order_item_list")
    private List<OrderItemRequest> orderItemList;

}
