package org.feather.xd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd
 * @className: UserApplication
 * @author: feather
 * @description:
 * @since: 2024-08-05 15:37
 * @version: 1.0
 */
@SpringBootApplication
@MapperScan("org.feather.xd.mapper")
public class UserApplication {
    public static void main(String [] args){
        SpringApplication.run(UserApplication.class,args);
    }
}
