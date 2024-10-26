package org.feather.xd.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.feather.xd.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: InterceptorConfig
 * @author: feather
 * @description:
 * @since: 2024-09-06 16:23
 * @version: 1.0
 */
@Configuration
@Slf4j
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                //拦截的路径
                .addPathPatterns("/api/car/*/**","/api/prodcut/*/lock_products")

                //排查不拦截的路径
                .excludePathPatterns("");

    }


}
