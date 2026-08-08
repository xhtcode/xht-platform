package com.xht.framework.cache.utils;

import com.xht.framework.cache.repository.RedisRepository;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * RedisRepositoryUtils 类
 *
 * @author xht
 **/
@Slf4j
public class RedisRepositoryUtils implements ApplicationContextAware {

    private static RedisRepository redisRepository;

    public static RedisRepository redisRepository() {
        if (redisRepository == null) {
            log.warn("RedisRepositoryUtils redisRepository is null");
            throw new RuntimeException("RedisRepositoryUtils redisRepository is null");
        }
        return redisRepository;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        RedisRepositoryUtils.redisRepository = applicationContext.getBean(RedisRepository.class);
    }
}
