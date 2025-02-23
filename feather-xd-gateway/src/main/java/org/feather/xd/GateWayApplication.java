package org.feather.xd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd
 * @className: GateWayApplication
 * @author: feather
 * @description:
 * @since: 2025-02-23 14:24
 * @version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class);
    }
}
