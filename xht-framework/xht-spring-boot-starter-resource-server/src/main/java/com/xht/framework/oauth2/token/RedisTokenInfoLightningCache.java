package com.xht.framework.oauth2.token;

import com.xht.framework.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
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

    private final TokenLightningCacheProperties tokenLightningCacheProperties;

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
        log.debug("从缓存中获取token信息，key:{}", key);
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
        long timeOut = tokenLightningCacheProperties.getExpired();
        if (Objects.nonNull(expiresAt)) {
            Duration between = Duration.between(Instant.now(), expiresAt);
            long emp = between.getSeconds() - 1;// 减去1秒 程序默认耗时 为1秒 实际不到1秒
            if (emp <= tokenLightningCacheProperties.getExpired()) {
                timeOut = emp;
            }
        }
        log.debug("将token信息保存到缓存中，key:{},value:{},timeOut:{}", key, value, timeOut);
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 删除缓存的token信息
     *
     * @param key 缓存的键值，用于标识特定的token信息
     */
    @Override
    public void deleteTokenInfo(String key) {
        if (StringUtils.hasText(key)) {
            log.debug("从缓存中删除token信息，key:{}", key);
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除缓存的token信息
     *
     * @param keys 缓存的键值，用于标识特定的token信息
     */
    @Override
    public void deleteTokenInfo(Collection<String> keys) {
        if (!CollectionUtils.isEmpty(keys)) {
            log.debug("从缓存中批量删除token信息，keys:{}", keys);
            redisTemplate.delete(keys);
        }
    }

}
