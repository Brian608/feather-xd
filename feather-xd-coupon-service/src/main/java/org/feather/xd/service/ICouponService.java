package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.feather.xd.model.BasePage;
import org.feather.xd.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.vo.CouponVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
public interface ICouponService extends IService<CouponDO> {

    /**
     * description:    分页查询优惠券
     * @param query
     * @return {@link Page<CouponVO>}
     * @author: feather
     * @since: 2024-08-21 20:06
     **/
    Page<CouponVO> pageCoupon(CouponQuery query);


}
