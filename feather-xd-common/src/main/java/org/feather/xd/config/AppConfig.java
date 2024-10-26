package org.feather.xd.config;
import feign.RequestInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import lombok.Data;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: AppConfig
 * @author: feather
 * @description:
 * @since: 2024-08-27 17:53
 * @version: 1.0
 */
@Data
@Configuration
public class AppConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

//
//    @Value("${spring.redis.password}")
//    private String redisPwd;

    /**
     * description: 配置分布式所锁的Redisson
     * @param
     * @return {@link RedissonClient}
     * @author: feather
     * @since: 2024-08-27 19:01
     **/
    @Bean
    public RedissonClient redissonClient(){
        Config config=new Config();
        //单机方式
        config.useSingleServer().setAddress("redis://"+redisHost+":"+redisPort);

        //集群
        //config.useClusterServers().addNodeAddress("redis://192.31.21.1:6379","redis://192.31.21.2:6379")

        return  Redisson.create(config);
    }
    /**
     * 避免存储的key乱码，hash结构依旧会乱码
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        RedisSerializer redisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);

        return redisTemplate;
    }


    /**
     * description: feign 调用丢失token解决方式，新增拦截器
     **/
    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> {
            ServletRequestAttributes attributes=   (ServletRequestAttributes)  RequestContextHolder.getRequestAttributes();
            if (attributes!=null){
                HttpServletRequest httpServletRequest = attributes.getRequest();
                String token = httpServletRequest.getHeader("token");
                template.header("token",token);
            }
        };
    }


}
