package org.feather.xd.enums;

import lombok.Getter;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.enums
 * @className: AddressStatusEnum
 * @author: feather
 * @description:
 * @since: 2024-08-16 11:44
 * @version: 1.0
 */
@Getter
public enum AddressStatusEnum {
    /**
     * 是默认收货地址
     */
    DEFAULT_STATUS(1),

    /**
     * 非默认收货地址
     */
    COMMON_STATUS(0);

    private final int status;

     AddressStatusEnum(int status){
        this.status = status;
    }

}
