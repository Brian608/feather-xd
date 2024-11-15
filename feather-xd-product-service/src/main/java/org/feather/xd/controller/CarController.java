package org.feather.xd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.feather.xd.request.CartItemRequest;
import org.feather.xd.service.ICarService;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CartItemVO;
import org.feather.xd.vo.CartVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("清空购物车")
    @DeleteMapping("/clear")
    public JsonResult<Object> cleanMyCart(){
        carService.clear();
        return JsonResult.buildSuccess();
    }

    @ApiOperation("查看我的购物车")
    @GetMapping("/mycart")
    public JsonResult<CartVO> getMyCart(){
        return JsonResult.buildSuccess(carService.getMyCart());
    }

    @ApiOperation("删除购物项")
    @DeleteMapping("/delete/{product_id}")
    public JsonResult<Object> deleteItem( @ApiParam(value = "商品id",required = true)@PathVariable("product_id")long productId ){
        carService.deleteItem(productId);
        return JsonResult.buildSuccess();
    }

    @ApiOperation("修改购物车数量")
    @PostMapping("/change")
    public JsonResult<Object> changeItemNum(  @RequestBody @Validated CartItemRequest cartItemRequest){

        carService.changeItemNum(cartItemRequest);

        return JsonResult.buildSuccess();
    }

    @ApiOperation("获取对应订单的商品信息")
    @PostMapping("/confirm_order_cart_items")
    public JsonResult<List<CartItemVO>> confirmOrderCartItems(@ApiParam("商品id列表") @RequestBody List<Long> productIdList){
        return JsonResult.buildSuccess(carService.confirmOrderCartItems(productIdList));

    }


}
