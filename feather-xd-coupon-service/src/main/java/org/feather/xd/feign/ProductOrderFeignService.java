package org.feather.xd.feign;

import org.feather.xd.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign
 * @className: ProductOrderFeignService
 * @author: feather
 * @description:
 * @since: 2024-09-29 16:37
 * @version: 1.0
 */
@FeignClient(name = "feather-xd-order-service")
public interface ProductOrderFeignService {

    /**
     * 查询订单状态
     * @param outTradeNo
     * @return
     */
    @GetMapping("/api/order/v1/query_state")
    JsonResult<String> queryProductOrderState(@RequestParam("out_trade_no")String outTradeNo);
}
