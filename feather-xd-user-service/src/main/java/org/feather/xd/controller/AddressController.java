package org.feather.xd.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.request.AddressAddRequest;
import org.feather.xd.service.IAddressService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.AddressVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author feather
 * @since 2024-08-04
 */
@RequiredArgsConstructor
@Api(tags = "收获地址模块")
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {

    private final IAddressService addressService;

    @ApiOperation(value = "添加收获地址",httpMethod = "POST", produces = "application/json")
    @PostMapping("/add")
    public JsonResult<Boolean> register(@ApiParam("添加收获地址") @RequestBody @Validated AddressAddRequest request){
        return JsonResult.buildSuccess( addressService.add(request));
    }

    @ApiOperation(value = "通过id查询你用户地址",httpMethod = "GET", produces = "application/json")
    @GetMapping("/address/{id}")
    public JsonResult<AddressVO> detail(@ApiParam(value = "收获地址id",required = true)@PathVariable Long id){
        return JsonResult.buildSuccess( addressService.detail(id));
    }

    @ApiOperation(value = "通过id删除用户地址",httpMethod = "DELETE", produces = "application/json")
    @DeleteMapping("/del/{id}")
    public JsonResult<Boolean> del(@ApiParam(value = "收获地址id",required = true)@PathVariable Long id){
        return JsonResult.buildSuccess( addressService.del(id));
    }

    @ApiOperation(value = "查找用户全部收货地址",httpMethod = "GET", produces = "application/json")
    @GetMapping("/listUserAllAddress")
    public JsonResult<List<AddressVO>> listUserAllAddress(){
        return JsonResult.buildSuccess( addressService.listUserAllAddress());
    }


}

