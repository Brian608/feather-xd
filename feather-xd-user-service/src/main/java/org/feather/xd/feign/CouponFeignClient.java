package org.feather.xd.feign;

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
//@FeignClient(value = "feather-xd-coupon-service",contextId = "CouponFeignClient",fallback = CouponFeignFallback.class)
    //分布式事务下，如果配置了服务降级 分布式事务就不会生鲜，会认为当前A服务没有异常
@FeignClient(value = "feather-xd-coupon-service",contextId = "CouponFeignClient")
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
