package org.feather.xd.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: ProductVO
 * @author: feather
 * @description:
 * @since: 2024-09-06 15:59
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductVO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面图
     */
    @JsonProperty("cover_img")
    private String coverImg;

    /**
     * 详情
     */
    private String detail;

    /**
     * 老价格
     */
    @JsonProperty("old_amount")
    private BigDecimal oldAmount;

    /**
     * 新价格
     */
    private BigDecimal amount;

    /**
     * 库存
     */
    private Integer stock;
}
