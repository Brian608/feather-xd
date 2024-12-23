package org.feather.xd.component;

import org.feather.xd.vo.PayInfoVO;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.component
 * @className: PayStrategy
 * @author: feather
 * @description:
 * @since: 2024-12-23 21:09
 * @version: 1.0
 */
public interface PayStrategy {
    /**
     * description: 下单
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 21:10
     **/

      String unifiedOrder(PayInfoVO payInfoVO);


      /** 退款
       * description:
       * @param payInfoVO
       * @return {@link String}
       * @author: feather
       * @since: 2024-12-23 21:11
       **/
    default   String refound(PayInfoVO payInfoVO){return  "";};
    /**
     * description: 查询支付是否成功
     * @param payInfoVO
     * @return {@link String}
     * @author: feather
     * @since: 2024-12-23 21:12
     **/
    default  String queryOrderPaySuccess(PayInfoVO payInfoVO){return  "";}

}
