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
 * @className: OrderApplication
 * @author: feather
 * @description:
 * @since: 2024-09-11 18:06
 * @version: 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("org.feather.xd.mapper")
@EnableFeignClients
@EnableDiscoveryClient
public class OrderApplication {

    public static void main(String [] args){

        SpringApplication.run(OrderApplication.class,args);
    }

}
