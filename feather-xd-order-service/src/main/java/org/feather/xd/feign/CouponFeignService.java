package org.feather.xd.feign;

import org.feather.xd.request.LockCouponRecordRequest;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CouponRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign
 * @className: CouponFeignService
 * @author: feather
 * @description:
 * @since: 2024-10-26 13:18
 * @version: 1.0
 */
@FeignClient(name = "feather-xd-coupon-service")
public interface CouponFeignService {
    /**
     * 查询用户的优惠券是否可用，防止水平权限
     * @param recordId
     * @return
     */
    @GetMapping("/api/coupon_record/v1/detail/{record_id}")
    JsonResult<CouponRecordVO> findUserCouponRecordById(@PathVariable("record_id") long recordId);

    /**
     * 锁定优惠券记录
     * @param lockCouponRecordRequest
     * @return
     */
    @PostMapping("/api/coupon_record/v1/lockCouponRecords")
    JsonResult<Object> lockCouponRecords(@RequestBody LockCouponRecordRequest lockCouponRecordRequest);
}
