package org.feather.xd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.service.IProductOrderService;
import org.feather.xd.util.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.controller
 * @className: ProductOrderController
 * @author: feather
 * @description:
 * @since: 2024-09-12 9:57
 * @version: 1.0
 */
@RequiredArgsConstructor
@Api("订单模块")
@RestController
@RequestMapping("/api/order/v1")
@Slf4j
public class ProductOrderController {

    private final IProductOrderService orderService;

    @ApiOperation("提交订单")
    @PostMapping("/confirmOrder")
    public  void confirmOrder(@RequestBody @Validated ConfirmOrderRequest request , HttpServletResponse response){

        orderService.confirmOrder(request,response);

    }

    /**
     * description: 查询订单状态
     此接口没有登录拦截，可以增加一个秘钥进行rpc通信
     * @param outTradeNo
     * @return {@link JsonResult<String>}
     * @author: feather
     * @since: 2024-09-30 9:48
     **/
    @ApiOperation("查询订单状态")
    @GetMapping("/queryProductOrderState")
    public JsonResult<String> queryProductOrderState(@ApiParam("订单号") @RequestParam("out_trade_no")String outTradeNo){
       return JsonResult.buildSuccess(orderService.queryProductOrderState(outTradeNo));

    }
}
