package org.feather.xd.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.config.AlipayConfig;
import org.feather.xd.config.PayUrlConfig;
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
import java.util.HashMap;
import java.util.UUID;

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

    private  final PayUrlConfig payUrlConfig;

     private final AlipayConfig alipayConfig;


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

    @GetMapping("test_pay")
    public void testAlipay(HttpServletResponse response) throws AlipayApiException, IOException {

        HashMap<String,String> content = new HashMap<>();
        //商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
        String no = UUID.randomUUID().toString();

        log.info("订单号:{}",no);
        content.put("out_trade_no", no);

        content.put("product_code", "FAST_INSTANT_TRADE_PAY");

        //订单总金额，单位为元，精确到小数点后两位
        content.put("total_amount", String.valueOf("111.99"));

        //商品标题/交易标题/订单标题/订单关键字等。 注意：不可使用特殊字符，如 /，=，&amp; 等。
        content.put("subject", "杯子");

        //商品描述，可空
        content.put("body", "好的杯子");

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        content.put("timeout_express", "5m");


        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizContent(JSON.toJSONString(content));
        request.setNotifyUrl(payUrlConfig.getAlipayCallbackUrl());
        request.setReturnUrl(payUrlConfig.getAlipaySuccessReturnUrl());

        AlipayTradeWapPayResponse alipayResponse  = AlipayConfig.getInstance().pageExecute(request);

        if(alipayResponse.isSuccess()){
            System.out.println("调用成功");

            String form = alipayResponse.getBody();

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();

        } else {
            System.out.println("调用失败");
        }
    }
}
