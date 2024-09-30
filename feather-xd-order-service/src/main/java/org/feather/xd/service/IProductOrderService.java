package org.feather.xd.service;

import org.feather.xd.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.ConfirmOrderRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author feather
 * @since 2024-09-11
 */
public interface IProductOrderService extends IService<ProductOrderDO> {

    /**
     * description: 提交订单
     *
     *   /**
     *      * * 防重提交
     *      * * 用户微服务-确认收货地址
     *      * * 商品微服务-获取最新购物项和价格
     *      * * 订单验价
     *      *   * 优惠券微服务-获取优惠券
     *      *   * 验证价格
     *      * * 锁定优惠券
     *      * * 锁定商品库存
     *      * * 创建订单对象
     *      * * 创建子订单对象
     *      * * 发送延迟消息-用于自动关单
     *      * * 创建支付信息-对接三方支付
     *
     * @param request
     * @return
     * @author: feather
     * @since: 2024-09-12 10:10
     **/
    void confirmOrder(ConfirmOrderRequest request, HttpServletResponse response);


    /**
     * 查询订单状态
     * @param outTradeNo
     * @return
     */
    String queryProductOrderState(String outTradeNo);
}
