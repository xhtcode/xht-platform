package com.xht.framework.oauth2.token;


import java.time.Instant;

/**
 * Token信息闪电缓存接口
 * 提供token信息的快速缓存和获取功能
 */
public interface TokenInfoLightningCache {

    /**
     * 根据键获取token信息
     *
     * @param <T> 泛型类型参数
     * @param key 缓存的键值，用于标识特定的token信息
     * @return 返回与指定键关联的token信息，如果不存在则返回null
     */
    <T> T getTokenInfo(String key);

    /**
     * 设置token信息到缓存中
     *
     * @param <T>       泛型类型参数
     * @param key       缓存的键值，用于标识特定的token信息
     * @param expiresAt token过期时间
     * @param value     要缓存的token信息值
     */
    <T> void setTokenInfo(String key, Instant expiresAt, T value);

}
