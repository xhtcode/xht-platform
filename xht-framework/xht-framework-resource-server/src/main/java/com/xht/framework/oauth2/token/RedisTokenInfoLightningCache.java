package com.xht.framework.oauth2.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 缓存token信息 redis 实现
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
public class RedisTokenInfoLightningCache implements TokenInfoLightningCache {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 默认超时时间 5分钟
     */
    private final static Long DEFAULT_TIME_OUT = 60 * 5L;

    /**
     * 根据键获取token信息
     *
     * @param key 缓存的键值，用于标识特定的token信息
     * @return 返回与指定键关联的token信息，如果不存在则返回null
     */
    @Override
    public <T> T getTokenInfo(String key) {
        Long expire = redisTemplate.getExpire(key);
        if (expire <= 0) {
            return null;
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置token信息到缓存中
     *
     * @param key       缓存的键值，用于标识特定的token信息
     * @param expiresAt token过期时间
     * @param value     要缓存的token信息值
     */
    @Override
    public <T> void setTokenInfo(String key, Instant expiresAt, T value) {
        long timeOut = DEFAULT_TIME_OUT;
        if (Objects.isNull(expiresAt)) {
            Duration between = Duration.between(Instant.now(), expiresAt);
            long emp = between.getSeconds() - 1;//减去1秒 程序默认耗时 为1秒 实际不到1秒
            if (emp <= DEFAULT_TIME_OUT) {
                timeOut = emp;
            }
        }
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }
}
