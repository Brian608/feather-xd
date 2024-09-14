package org.feather.xd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd
 * @className: CouponApplication
 * @author: feather
 * @description:
 * @since: 2024-08-19 20:36
 * @version: 1.0
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("org.feather.xd.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
