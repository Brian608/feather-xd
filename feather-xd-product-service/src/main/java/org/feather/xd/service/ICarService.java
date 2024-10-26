package org.feather.xd.service;

import org.feather.xd.request.CartItemRequest;
import org.feather.xd.vo.CartItemVO;
import org.feather.xd.vo.CartVO;

import java.util.List;

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


    /**
     * description: 删除购物项
     * @param productId
     * @return
     * @author: feather
     * @since: 2024-09-11 10:50
     **/
    void  deleteItem(Long productId);

    /**
     * description: 修改购物车数量
     * @param cartItemRequest
     * @return
     * @author: feather
     * @since: 2024-09-11 11:05
     **/

    void changeItemNum(CartItemRequest cartItemRequest);

    /**
     * description:   用于订单服务，确认订单，获取对应的商品项详情信息
     *      *
     *      * 会清空购物车的商品数据
     * @param productIdList
     * @return {@link List< CartItemVO>}
     * @author: feather
     * @since: 2024-10-26 10:24
     **/
    List<CartItemVO> confirmOrderCartItems(List<Long> productIdList);
}
