package org.feather.xd.constant;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.constant
 * @className: CacheKey
 * @author: feather
 * @description:
 * @since: 2024-08-10 9:11
 * @version: 1.0
 */
public interface CacheKey   {
    /**
     * 注册验证码，第一个是类型，第二个是接收号码
     */
   String CHECK_CODE_KEY = "code:%s:%s";


    /**
     * 购物车 hash 结果，key是用户唯一标识
     */
     String CART_KEY = "cart:%s";


    /**
     * 提交表单的token key
     */
   String SUBMIT_ORDER_TOKEN_KEY = "order:submit:%s";
}
