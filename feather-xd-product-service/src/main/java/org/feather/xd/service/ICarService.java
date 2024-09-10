package org.feather.xd.service;

import org.feather.xd.request.CartItemRequest;
import org.feather.xd.vo.CartVO;

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

    /**
     * description: 清空购物车
     * @param
     * @return
     * @author: feather
     * @since: 2024-09-10 17:12
     **/
    void clear();

    /**
     * description: 查看我的购物车
     * @param
     * @return {@link CartVO}
     * @author: feather
     * @since: 2024-09-10 17:32
     **/
    CartVO getMyCart();
}
