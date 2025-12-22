package com.xht.framework.core.utils.cache;

import com.xht.framework.core.exception.UtilException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地数据缓存工具类（基于内存的线程安全缓存）
 * <p>使用ConcurrentHashMap实现，提供基础的缓存增删查、清空等操作</p>
 *
 * @author xht
 */
@SuppressWarnings("unused")
public final class LocalDataCache {

    /**
     * 缓存容器（线程安全的ConcurrentHashMap，并发性能优于Hashtable和synchronizedMap）
     */
    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 私有构造器，禁止实例化
     */
    private LocalDataCache() {
        throw new UtilException("禁止实例化工具类 LocalDataCache");
    }

    // ========================= 缓存写入操作 =========================

    /**
     * 放入缓存（若缓存键已存在，会先清空原有集合/映射类型的缓存值，再覆盖）
     *
     * @param cacheKey   缓存键（不可为null）
     * @param cacheValue 缓存值（可为null）
     * @throws IllegalArgumentException 当缓存键为null时抛出
     */
    public static void putCache(String cacheKey, Object cacheValue) {
        // 参数校验：缓存键不可为null
        if (cacheKey == null) {
            throw new IllegalArgumentException("缓存键cacheKey不能为null");
        }

        // 获取旧值，若为集合/映射类型则清空（保留原有业务逻辑）
        Object oldValue = CACHE_MAP.get(cacheKey);
        clearOldValueIfCollectionOrMap(oldValue);
        // 放入新值
        CACHE_MAP.put(cacheKey, cacheValue);
    }

    // ========================= 缓存读取操作 =========================

    /**
     * 获取缓存值（泛型方法，避免调用方强制类型转换）
     *
     * @param cacheKey 缓存键（不可为null）
     * @param <T>      缓存值类型
     * @return 缓存值，若键不存在则返回null
     * @throws IllegalArgumentException 当缓存键为null时抛出
     */
    @SuppressWarnings("unchecked")
    public static <T> T getCache(String cacheKey) {
        if (cacheKey == null) {
            throw new IllegalArgumentException("缓存键cacheKey不能为null");
        }
        return (T) CACHE_MAP.get(cacheKey);
    }

    /**
     * 获取所有缓存键的集合
     *
     * @return 缓存键集合（不可修改，若需修改可自行转换为新集合）
     */
    public static Set<String> getCacheKeys() {
        return CACHE_MAP.keySet();
    }

    /**
     * 判断缓存键是否存在
     *
     * @param cacheKey 缓存键（不可为null）
     * @return true表示存在，false表示不存在
     * @throws IllegalArgumentException 当缓存键为null时抛出
     */
    public static boolean containsCacheKey(String cacheKey) {
        if (cacheKey == null) {
            throw new IllegalArgumentException("缓存键cacheKey不能为null");
        }
        return CACHE_MAP.containsKey(cacheKey);
    }

    // ========================= 缓存删除操作 =========================

    /**
     * 移除指定缓存键的缓存
     *
     * @param cacheKey 缓存键（不可为null）
     * @return 被移除的缓存值，若键不存在则返回null
     * @throws IllegalArgumentException 当缓存键为null时抛出
     */
    public static Object removeCache(String cacheKey) {
        if (cacheKey == null) {
            throw new IllegalArgumentException("缓存键cacheKey不能为null");
        }
        return CACHE_MAP.remove(cacheKey);
    }

    /**
     * 清空所有缓存
     */
    public static void clearAllCache() {
        CACHE_MAP.clear();
    }

    // ========================= 私有辅助方法 =========================

    /**
     * 若旧值为集合或映射类型，清空其内容（保留原有业务逻辑）
     *
     * @param oldValue 缓存旧值
     */
    @SuppressWarnings("unchecked")
    private static void clearOldValueIfCollectionOrMap(Object oldValue) {
        if (oldValue == null) {
            return;
        }

        // 处理Map类型旧值
        if (oldValue instanceof Map) {
            ((Map<Object, Object>) oldValue).clear();
        }
        // 处理Collection类型旧值（List、Set等都实现了Collection）
        else if (oldValue instanceof Collection) {
            ((Collection<Object>) oldValue).clear();
        }
    }
}