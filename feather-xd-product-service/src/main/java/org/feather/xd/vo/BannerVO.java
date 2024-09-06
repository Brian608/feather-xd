package org.feather.xd.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.vo
 * @className: BannerVO
 * @author: feather
 * @description:
 * @since: 2024-09-06 11:37
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BannerVO {

    private Long id;

    /**
     * 图片
     */
    private String img;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 权重
     */
    private Integer weight;
}
