package org.feather.xd.enums;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.enums
 * @className: StockTaskStateEnum
 * @author: feather
 * @description:
 * @since: 2024-09-18 16:40
 * @version: 1.0
 */
public enum StockTaskStateEnum {
    /**
     * 锁定
     */
    LOCK,

    /**
     * 完成
     */
    FINISH,

    /**
     * 取消，释放库存
     */
    CANCEL;
}
