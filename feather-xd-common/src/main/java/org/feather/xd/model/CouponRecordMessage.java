package org.feather.xd.model;

import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.model
 * @className: CouponRecordMessage
 * @author: feather
 * @description:
 * @since: 2024-09-19 10:40
 * @version: 1.0
 */
@Data
public class CouponRecordMessage {


    /**
     * 消息id
     */
    private String messageId;

    /**
     * 订单号
     */
    private String outTradeNo;


    /**
     * 库存锁定任务id
     */
    private Long taskId;


}