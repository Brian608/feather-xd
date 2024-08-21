package org.feather.xd.model;

import lombok.Data;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.model
 * @className: BasePage
 * @author: feather
 * @description:
 * @since: 2024-08-21 20:03
 * @version: 1.0
 */
@Data
public class BasePage {
    /**
     * 页码
     */
    private Integer pageNo = 1;
    /**
     * 每页条数
     */
    private Integer pageSize = 10;

}
