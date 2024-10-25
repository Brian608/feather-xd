package org.feather.xd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.ClientType;
import org.feather.xd.enums.ProductOrderPayTypeEnum;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.service.IProductOrderService;
import org.feather.xd.util.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        JsonResult jsonResult = orderService.confirmOrder(request);
        if ( jsonResult.getCode().equals(CommonConstant.SUCCESS_CODE)){
            String clientType = request.getClientType();
            String payType = request.getPayType();
            //如果是支付宝支付  都是跳转网页 ，APP除外
            if (ProductOrderPayTypeEnum.ALIPAY.name().equalsIgnoreCase(payType)){
                log.info("创建支付宝订单成功:[{}]",request);

                //H5支付
                if (ClientType.H5.name().equalsIgnoreCase(clientType)){
                    writeData(response);
                } else if (ClientType.APP.name().equalsIgnoreCase(clientType)) {
                    //TODO APP SDK支付
                }

            } else if (ProductOrderPayTypeEnum.WECHAT.name().equalsIgnoreCase(payType)) {
                //TODO 微信支付
            }
        }

    }
    private void writeData(HttpServletResponse response) {

        try {
            response.setContentType("text/html;charset=UTF8");
            response.getWriter().write("请支付");
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error("写出Html异常:[{}]", e.getMessage());
        }
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
