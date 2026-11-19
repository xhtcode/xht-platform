package com.xht.auth.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 描述： Redis配置类 spring security 使用
 *
 * @author xht
 */
@Slf4j
@Configuration
public class SecurityRedisConfiguration {

    /**
     * 自定义RedisTemplate.
     *
     * @param redisConnectionFactory redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // value
        redisTemplate.setValueSerializer(RedisSerializer.java());
        // hash-key
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // hash-value
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        // 初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}