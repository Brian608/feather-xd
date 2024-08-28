package org.feather.xd.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.interceptor.LoginInterceptor;
import org.feather.xd.model.LoginUser;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.service.ICouponService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CouponVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@Slf4j
@RequiredArgsConstructor
@Api(tags = "优惠券")
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    private final ICouponService couponService;

    private  final RedissonClient redissonClient;

    @ApiOperation(value = "优惠券分页",httpMethod = "POST", produces = "application/json")
    @PostMapping("/pageCoupon")
    public JsonResult<Page<CouponVO>> pageCoupon(@RequestBody @Validated CouponQuery query){
        return JsonResult.buildSuccess( couponService.pageCoupon(query));
    }

    @ApiOperation(value = "领取优惠券")
    @GetMapping("/getCoupon/{couponId}")
    public JsonResult<Object> getCoupon(@ApiParam(value = "优惠券id", required = true) @PathVariable long couponId){
        LoginUser loginUser = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        String lockKey = "lock:coupon:" + couponId + ":" + loginUser.getId();

        RLock lock = redissonClient.getLock(lockKey);
        //多个线程进入，会阻塞等待释放锁，默认30秒，然后有watch dog自动续期
        lock.lock();
        log.info("领劵接口加锁成功:{}",Thread.currentThread().getId());
        try {
            couponService.getCoupon(couponId);
        }catch (Exception e){
            lock.unlock();
            log.info("解锁成功");
        }
        return JsonResult.buildSuccess( );
    }


}

