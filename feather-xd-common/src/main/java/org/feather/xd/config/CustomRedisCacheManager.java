package org.feather.xd.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @projectName: feather-xd
 * @package: org.feather.xd.config
 * @className: CustomRedisCacheManager
 * @author: feather
 * @description:
 * @since: 2024-08-27 17:49
 * @version: 1.0
 */
@Configuration
@EnableCaching
public class CustomRedisCacheManager extends CachingConfigurerSupport {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).entryTtl(Duration.ofMinutes(1));
        return configuration;
    }


}
