package org.feather.xd.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.feather.xd.enums.CouponCategoryEnum;
import org.feather.xd.model.BasePage;
import org.feather.xd.model.CouponDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.request.NewUserCouponRequest;
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


    /**
     * description: 领取优惠券
     * 1：获取优惠券是否存在
     * 2:是否可以领取 时间  库存 超过限制
     * 3：扣减库存
     * 4：保存领取记录
     * @param couponId
     * @return
     * @author: feather
     * @since: 2024-08-23 16:08
     **/
    void getCoupon(long couponId);

    /**
     * description: 新用户注册发放优惠券
     * @param newUserCouponRequest
     * @return
     * @author: feather
     * @since: 2024-09-05 10:57
     **/
    void initNewUserCoupon(NewUserCouponRequest newUserCouponRequest);


}
