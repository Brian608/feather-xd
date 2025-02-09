package org.feather.xd.service;

import org.feather.xd.enums.ProductOrderPayTypeEnum;
import org.feather.xd.model.OrderMessage;
import org.feather.xd.model.ProductOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.feather.xd.request.ConfirmOrderRequest;
import org.feather.xd.util.JsonResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    JsonResult confirmOrder(ConfirmOrderRequest request);


    /**
     * 查询订单状态
     * @param outTradeNo
     * @return
     */
    String queryProductOrderState(String outTradeNo);


    /**
     * description: 队列监听，定时关单
     * @param orderMessage
     * @return {@link Boolean}
     * @author: feather
     * @since: 2024-12-03 20:33
     **/
    Boolean closeProductOrder(OrderMessage orderMessage);

    /**
     * description:支付结果回调通知
     * @param payType
     * @param paramsMap
     * @return {@link JsonResult}
     * @author: feather
     * @since: 2025-02-03 9:51
     **/

    JsonResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum payType, Map<String, String> paramsMap);

    /**
     * description: 分页查询我的订房列表
     * @param page
     * @param size
     * @param state
     * @return {@link Map<String, Object>}
     * @author: feather
     * @since: 2025-02-09 10:06
     **/
    Map<String, Object> pagePOrder(int page, int size, String state);
}
