package org.feather.xd.service;

import org.feather.xd.request.CartItemRequest;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.service
 * @className: ICarService
 * @author: feather
 * @description:
 * @since: 2024-09-10 11:03
 * @version: 1.0
 */
public interface ICarService {

  /**
   * description: 添加商品到购物车
   * @param cartItemRequest
   * @return
   * @author: feather
   * @since: 2024-09-10 11:05
   **/
    void addToCart(CartItemRequest cartItemRequest);
}
