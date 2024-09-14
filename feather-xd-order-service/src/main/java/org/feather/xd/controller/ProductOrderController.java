package org.feather.xd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.service.IProductOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
