package org.feather.xd.model;

import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.model
 * @className: ProductMessage
 * @author: feather
 * @description:
 * @since: 2024-10-22 15:15
 * @version: 1.0
 */
@Data
public class ProductMessage {
    /**
     * 消息队列id
     */
    private long messageId;

    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 库存锁定taskId
     */
    private long taskId;
}
