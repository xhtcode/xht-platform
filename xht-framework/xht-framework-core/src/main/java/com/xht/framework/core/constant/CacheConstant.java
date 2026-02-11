package com.xht.framework.core.constant;

import java.util.concurrent.TimeUnit;

/**
 * 缓存常量
 *
 * @author xht
 **/
public interface CacheConstant {

    /**
     * 缓存key前缀
     */
    String DEFAULT_KEY_PREFIX = "xht:";

    /**
     * 缓存时间
     */
    long DEFAULT_TIME_OUT = 60 * 60;

    /**
     * 缓存时间单位
     */
    TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;
}
