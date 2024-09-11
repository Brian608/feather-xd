package org.feather.xd.config;

import lombok.extern.slf4j.Slf4j;
import org.feather.xd.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: InterceptorConfig
 * @author: feather
 * @description:
 * @since: 2024-08-13 14:59
 * @version: 1.0
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                //拦截的路径
                .addPathPatterns("/api/order/*/**")

                //排查不拦截的路径
                .excludePathPatterns("/api/callback/*/**","/api/order/*/query_state", "/api/order/*/test_pay");

    }




}