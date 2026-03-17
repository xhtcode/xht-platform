package com.xht.framework.cache.autoconfigure;

import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.repository.impl.RedisRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisRepository 自动配置类
 *
 * <p>使用规则：
 * 1. 实现 RedisRepository 接口并注入 Spring 容器，即可覆盖默认实现<br/>
 * 2. 未自定义时，自动注册 RedisRepositoryImpl 作为默认实现
 * </p>
 *
 * @author xht
 */
@Slf4j
@Order
@AutoConfiguration
@ConditionalOnMissingBean(RedisRepository.class)
public class RedisRepositoryConfiguration {

    public RedisRepositoryConfiguration() {
        log.debug("【xht-redis-spring-boot-starter】RedisRepository 自动配置");
    }

    @Bean
    public RedisRepository redisRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        log.debug("【xht-redis-spring-boot-starter】未检测到自定义 RedisRepository 实现，启用默认实现 RedisRepositoryImpl");
        return new RedisRepositoryImpl(redisTemplate);
    }

}
