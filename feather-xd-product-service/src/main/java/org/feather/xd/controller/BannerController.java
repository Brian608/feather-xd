package org.feather.xd.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.feather.xd.service.IBannerService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.BannerVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-09-05
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/banner/v1")
public class BannerController {

    private final IBannerService bannerService;



    @ApiOperation("轮播图列表接口")
    @GetMapping("/bannerList")
    public JsonResult<List<BannerVO>> bannerList(){
        return JsonResult.buildSuccess(bannerService.bannerList());

    }

}

