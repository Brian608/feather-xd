package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: ProductOrderAddressVO
 * @author: feather
 * @description:
 * @since: 2024-10-24 21:30
 * @version: 1.0
 */
@Data
public class ProductOrderAddressVO {

    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @JsonProperty("default_status")
    private Integer defaultStatus;

    /**
     * 收发货人姓名
     */
    @JsonProperty("receive_name")
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

    @JsonProperty("detail_address")
    private String detailAddress;

}
