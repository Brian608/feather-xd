package org.feather.xd.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.feather.xd.query.CouponQuery;
import org.feather.xd.service.ICouponService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CouponVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-08-19
 */
@RequiredArgsConstructor
@Api(tags = "优惠券")
@RestController
@RequestMapping("/api/coupon/v1")
public class CouponController {

    private final ICouponService couponService;

    @ApiOperation(value = "优惠券分页",httpMethod = "POST", produces = "application/json")
    @PostMapping("/pageCoupon")
    public JsonResult<Page<CouponVO>> pageCoupon(@RequestBody @Validated CouponQuery query){
        return JsonResult.buildSuccess( couponService.pageCoupon(query));
    }


}

