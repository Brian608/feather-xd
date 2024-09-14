package org.feather.xd.feign.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.feign.CouponFeignClient;
import org.feather.xd.request.NewUserCouponRequest;
import org.feather.xd.util.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign.hystrix
 * @className: CouponFeignFallback
 * @author: feather
 * @description:
 * @since: 2024-09-14 15:58
 * @version: 1.0
 */
@Slf4j
@Component
public class CouponFeignFallback implements CouponFeignClient {
    @Override
    public JsonResult<Object> addNewUserCoupon(NewUserCouponRequest newUserCouponRequest) {
        log.error("新用户:[{}]注册发送优惠券远程调用出错了",newUserCouponRequest);
        return JsonResult.buildError("新用户注册发送优惠券远程调用出错了");
    }
}
