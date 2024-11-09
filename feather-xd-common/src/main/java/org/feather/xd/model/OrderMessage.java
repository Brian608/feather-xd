package org.feather.xd.model;

import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.model
 * @className: OrderMessage
 * @author: feather
 * @description:
 * @since: 2024-11-09 19:28
 * @version: 1.0
 */
@Data
public class OrderMessage {

    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 订单号
     */
    private String outTradeNo;

}
