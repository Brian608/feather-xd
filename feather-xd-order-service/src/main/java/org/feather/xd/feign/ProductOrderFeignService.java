package org.feather.xd.feign;

import org.feather.xd.request.LockProductRequest;
import org.feather.xd.util.JsonResult;
import org.feather.xd.vo.CartItemVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.feign
 * @className: ProductOrderFeignService
 * @author: feather
 * @description:
 * @since: 2024-10-26 10:13
 * @version: 1.0
 */
@FeignClient(name = "feather-xd-product-service")
public interface ProductOrderFeignService {

    /**
     * 获取购物车的最新商品价格（也会清空对应的购物车商品）
     * @param productIdList
     * @return
     */
    @PostMapping("/api/cart/v1/confirm_order_cart_items")
    JsonResult<List<CartItemVO>> confirmOrderCartItem(@RequestBody List<Long> productIdList);

    /**
     * 锁定商品购物项库存
     * @param lockProductRequest
     * @return
     */
    @PostMapping("/api/product/v1/lockProductStock")
    JsonResult<Boolean> lockProductStock(@RequestBody LockProductRequest lockProductRequest);


}
