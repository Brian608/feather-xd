package org.feather.xd.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.query.CouponRecordQuery;
import org.feather.xd.request.LockCouponRequest;
import org.feather.xd.service.ICouponRecordService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CouponRecordVO;
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
@Api(tags = "优惠券记录")
@RestController
@RequestMapping("/api/couponRecord/v1")
public class CouponRecordController {

    private final ICouponRecordService couponRecordService;


    @ApiOperation(value = "分页查询优惠券领取记录",httpMethod = "POST", produces = "application/json")
    @PostMapping("/pageCouponRecord")
    public JsonResult<Page<CouponRecordVO>> pageCoupon(@RequestBody @Validated CouponRecordQuery query){
        return JsonResult.buildSuccess( couponRecordService.pageCouponRecord(query));
    }

    @ApiOperation("查询优惠券记录详情")
    @GetMapping("detail/{recordId}")
    public JsonResult<CouponRecordVO> getCouponRecordDetail(@ApiParam(value = "记录id")  @PathVariable("recordId") long recordId){
       return JsonResult.buildSuccess(couponRecordService.findById(recordId));
    }

    @ApiOperation(value = "RPC-锁定优惠券",httpMethod = "POST", produces = "application/json")
    @PostMapping("/lockCouponRecords")
    public JsonResult<Object> lockCouponRecords(@RequestBody @Validated LockCouponRequest request){
        couponRecordService.lockCouponRecords(request);
        return JsonResult.buildSuccess( );
    }



}

