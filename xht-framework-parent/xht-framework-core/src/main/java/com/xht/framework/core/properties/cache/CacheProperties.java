package com.xht.framework.core.properties.cache;

import com.xht.framework.utils.StringUtils;
import lombok.Data;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 字典项缓存属性
 * @author xht
 */
@Data
public class CacheProperties {


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
     * 字典项缓存Key
     */
    private String keyPrefix;
    /**
     * 字典项缓存时间
     */
    private Long timeOut;
    /**
     * 字典项缓存时间单位
     */
    private TimeUnit unit;

    /**
     * 默认构造函数
     */
    public CacheProperties() {
        this(DEFAULT_KEY_PREFIX, DEFAULT_TIME_OUT, DEFAULT_UNIT);
    }

    /**
     * 构造函数
     *
     * @param keyPrefix  字典项缓存Key
     * @param timeOut    字典项缓存时间
     * @param unit       字典项缓存时间单位
     * @param properties 扩展属性
     */
    public CacheProperties(String keyPrefix, Long timeOut, TimeUnit unit) {
        this.keyPrefix = StringUtils.emptyToDefault(keyPrefix, DEFAULT_KEY_PREFIX);
        this.timeOut = Objects.requireNonNullElse(timeOut, DEFAULT_TIME_OUT);
        this.unit = Objects.requireNonNullElse(unit, DEFAULT_UNIT);
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