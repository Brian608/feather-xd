package org.feather.xd.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.feather.xd.model.BasePage;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.query
 * @className: CouponQuery
 * @author: feather
 * @description:
 * @since: 2024-08-21 20:07
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CouponQuery  extends BasePage {


    private String couponTitle;




}
