package org.feather.xd.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: MybatisPlusPageConfig
 * @author: feather
 * @description:
 * @since: 2024-08-21 19:53
 * @version: 1.0
 */
@Configuration
public class MybatisPlusPageConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
