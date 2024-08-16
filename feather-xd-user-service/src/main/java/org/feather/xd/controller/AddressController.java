package org.feather.xd.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.request.AddressAddRequest;
import org.feather.xd.service.IAddressService;
import org.feather.xd.util.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

