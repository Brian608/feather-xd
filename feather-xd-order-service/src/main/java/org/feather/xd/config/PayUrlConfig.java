package org.feather.xd.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: PayUrlConfig
 * @author: feather
 * @description:
 * @since: 2024-12-18 21:01
 * @version: 1.0
 */
@Data
@Configuration
public class PayUrlConfig {
    /**
     * 支付成功页面跳转
     */
    @Value("${alipay.success_return_url}")
    private String alipaySuccessReturnUrl;


    /**
     * 支付成功，回调通知
     */
    @Value("${alipay.callback_url}")
    private String alipayCallbackUrl;
}
