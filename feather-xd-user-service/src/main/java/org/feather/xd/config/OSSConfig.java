package org.feather.xd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: OSSConfig
 * @author: feather
 * @description:
 * @since: 2024-08-10 17:36
 * @version: 1.0
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketname;

}