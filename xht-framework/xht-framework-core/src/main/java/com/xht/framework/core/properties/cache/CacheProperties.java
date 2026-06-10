package com.xht.framework.core.properties.cache;

import com.xht.framework.utils.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 字典项缓存属性
 *
 * @param keyPrefix  字典项缓存Key
 * @param timeOut    字典项缓存时间
 * @param unit       字典项缓存时间单位
 * @param properties 扩展属性
 */
public record CacheProperties(String keyPrefix, Long timeOut, TimeUnit unit, Map<String, Object> properties) {
    /**
     * 缓存key前缀
     */
    private final static String DEFAULT_KEY_PREFIX = "xht:";

    /**
     * 缓存时间
     */
    private final static long DEFAULT_TIME_OUT = 60 * 60;

    /**
     * 缓存时间单位
     */
    private final static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
    
    /**
     * 默认构造函数
     */
    public CacheProperties() {
        this(DEFAULT_KEY_PREFIX, DEFAULT_TIME_OUT, DEFAULT_UNIT, Collections.emptyMap());
    }

    /**
     * 构造函数
     *
     * @param keyPrefix  字典项缓存Key
     * @param timeOut    字典项缓存时间
     * @param unit       字典项缓存时间单位
     * @param properties 扩展属性
     */
    public CacheProperties(String keyPrefix, Long timeOut, TimeUnit unit, Map<String, Object> properties) {
        this.keyPrefix = StringUtils.emptyToDefault(keyPrefix, DEFAULT_KEY_PREFIX);
        this.timeOut = Objects.requireNonNullElse(timeOut, DEFAULT_TIME_OUT);
        this.unit = Objects.requireNonNullElse(unit, DEFAULT_UNIT);
        this.properties = Objects.isNull(properties) ? Collections.emptyMap() : Collections.unmodifiableMap(properties);
    }

    /**
     * 获取扩展属性
     *
     * @param key   属性key
     * @param clazz 属性类型
     * @param <T>   属性类型
     * @return 属性值
     */
    public <T> T getProperty(String key, Class<T> clazz) {
        return clazz.cast(this.properties.get(key));
    }

    /**
     * 获取字典项缓存Key
     *
     * @param dictCode 字典项编码
     * @return 字典项缓存Key
     */
    public String getDictCacheKey(String dictCode) {
        return String.format(keyPrefix, dictCode);
    }

}