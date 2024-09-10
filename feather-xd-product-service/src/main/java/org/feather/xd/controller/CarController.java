package org.feather.xd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.feather.xd.request.CartItemRequest;
import org.feather.xd.service.ICarService;
import org.feather.xd.util.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.controller
 * @className: CarController
 * @author: feather
 * @description:
 * @since: 2024-09-10 11:02
 * @version: 1.0
 */
@Api(tags = "购物车")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/car/v1")
public class CarController {

    private final ICarService carService;


    @ApiOperation(value = "添加商品到购物车",httpMethod = "POST", produces = "application/json")
    @PostMapping("/addToCart")
    public JsonResult<Object> addToCart(@RequestBody @Validated CartItemRequest cartItemRequest){
        carService.addToCart(cartItemRequest);
        return JsonResult.buildSuccess( );
    }

}
