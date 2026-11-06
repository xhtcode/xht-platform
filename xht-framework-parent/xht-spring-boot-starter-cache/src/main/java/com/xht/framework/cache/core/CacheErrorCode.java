package com.xht.framework.cache.core;

import com.xht.framework.exception.core.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存模块错误码
 * 编码规则：3 + 模块(04) + 序号(2位)
 * 3：固定前缀，缓存模块专属域，借鉴HTTP 304 Not Modified语义，仅业务编码，不是HTTP状态码
 * 04：缓存模块
 * <p>
 * 重要：http响应状态固定返回200，此code放在响应体内部，避免和HTTP 304混淆
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum CacheErrorCode implements ErrorCode {
    /**
     * 缓存key非法
     */
    CACHE_KEY_ILLEGAL(30401, "缓存key非法"),
    /**
     * 缓存值非法
     */
    CACHE_VALUE_ILLEGAL(30402, "缓存值非法"),
    /**
     * 缓存过期时间非法
     */
    CACHE_TTL_ILLEGAL(30403, "缓存过期时间非法"),
    /**
     * 缓存服务连接失败
     */
    CACHE_CONNECT_FAIL(30404, "缓存服务连接失败"),
    /**
     * 缓存读取超时
     */
    CACHE_READ_TIMEOUT(30405, "缓存读取超时"),
    /**
     * 缓存写入超时
     */
    CACHE_WRITE_TIMEOUT(30406, "缓存写入超时"),
    /**
     * 缓存序列化失败
     */
    CACHE_SERIALIZE_ERROR(30407, "缓存序列化失败"),
    /**
     * 缓存反序列化失败
     */
    CACHE_DESERIALIZE_ERROR(30408, "缓存反序列化失败"),
    /**
     * 缓存写入失败
     */
    CACHE_WRITE_FAIL(30409, "缓存写入失败"),
    /**
     * 缓存删除失败
     */
    CACHE_DELETE_FAIL(30410, "缓存删除失败"),
    /**
     * Lua脚本执行异常
     */
    CACHE_LUA_SCRIPT_ERROR(30411, "缓存Lua脚本执行异常"),
    /**
     * 获取分布式锁失败
     */
    CACHE_LOCK_ACQUIRE_FAIL(30412, "获取分布式锁失败"),
    /**
     * 释放分布式锁失败
     */
    CACHE_LOCK_RELEASE_FAIL(30413, "释放分布式锁失败"),
    /**
     * 缓存击穿保护，拒绝回源查询
     */
    CACHE_BREAKDOWN_PROTECT(30414, "缓存击穿保护，暂不允许查询"),
    /**
     * 缓存请求限流
     */
    CACHE_RATE_LIMIT(30415, "缓存请求限流，请稍后重试");;

    /**
     * 业务错误码
     * <p>
     * 编码规则：5 + 模块(2位) + 序号(2位)
     * <p>
     * 注意：这是业务编码，不是HTTP响应状态码
     */
    private final int code;

    /**
     * 错误描述信息
     */
    private final String message;
}
