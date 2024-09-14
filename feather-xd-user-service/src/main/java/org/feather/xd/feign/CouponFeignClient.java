package org.feather.xd.feign;

import org.feather.xd.feign.hystrix.CouponFeignFallback;
import org.feather.xd.request.NewUserCouponRequest;
import org.feather.xd.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign
 * @className: CouponService
 * @author: feather
 * @description:
 * @since: 2024-09-14 15:26
 * @version: 1.0
 */
@FeignClient(value = "feather-xd-coupon-service",contextId = "CouponFeignClient",fallback = CouponFeignFallback.class)
public interface CouponFeignClient {

    /**
     * description: 新用户注册发放优惠券
     * @param newUserCouponRequest
     * @return {@link JsonResult<Object>}
     * @author: feather
     * @since: 2024-09-14 15:31
     **/
    @PostMapping("/api/coupon/v1/new_user_coupon")
    JsonResult<Object> addNewUserCoupon( @RequestBody @Validated NewUserCouponRequest newUserCouponRequest);

}
