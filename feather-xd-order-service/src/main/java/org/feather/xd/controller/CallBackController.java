package org.feather.xd.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.config.AlipayConfig;
import org.feather.xd.constant.CommonConstant;
import org.feather.xd.enums.ProductOrderPayTypeEnum;
import org.feather.xd.service.IProductOrderService;
import org.feather.xd.util.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.controller
 * @className: CallBackController
 * @author: feather
 * @description:
 * @since: 2025-02-03 9:28
 * @version: 1.0
 */
@Slf4j
@Api("订单回调通知模块")
@Controller
@RequestMapping("/api/callback/order/v1")
@RequiredArgsConstructor
public class CallBackController {
    private final IProductOrderService productOrderService;

    /**
     * 支付回调通知 post方式
     * @param request
     * @param response
     * @return
     */
  @RequestMapping("/alipay")
  public String alipayCallback(HttpServletRequest request, HttpServletResponse response){
      //将异步通知中收到的所有参数存储到map中
      Map<String,String> paramsMap = convertRequestParamsToMap(request);
      log.info("支付宝回调通知结果:{}",paramsMap);
      //调用SDK验证签名
      try {
          boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.ALIPAY_PUB_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
          if(signVerified){

              JsonResult jsonResult = productOrderService.handlerOrderCallbackMsg(ProductOrderPayTypeEnum.ALIPAY,paramsMap);

              if(jsonResult.getCode() == CommonConstant.SUCCESS_CODE){
                  //通知结果确认成功，不然会一直通知，八次都没返回success就认为交易失败
                  return "success";
              }

          }

      } catch (AlipayApiException e) {
          log.info("支付宝回调验证签名失败:异常：{}，参数:{}",e,paramsMap);
      }

      return "failure";
  }

    /**
     * 将request中的参数转换成Map
     * @param request
     * @return
     */
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> paramsMap = new HashMap<>(16);
        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int size = values.length;
            if (size == 1) {
                paramsMap.put(name, values[0]);
            } else {
                paramsMap.put(name, "");
            }
        }
        return paramsMap;
    }


}
