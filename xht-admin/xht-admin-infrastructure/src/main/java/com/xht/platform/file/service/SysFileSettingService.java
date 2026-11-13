package com.xht.platform.file.service;

import com.xht.platform.file.dao.SysFileSettingDao;
import com.xht.platform.file.entity.SysFileSettingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述： 文件模块全局配置服务
 * <p>本地 TTL 缓存，避免每次预览都查库；管理端保存后调用 refresh 立即生效。
 * 多实例部署时可由管理端接口额外广播刷新，当前实现先保证单实例一致性。</p>
 *
 * @author xht
 **/
@Slf4j
@Service
public class SysFileSettingService {

    /**
     * 本地缓存有效期（毫秒）
     */
    private static final long CACHE_TTL_MS = 60_000L;

    private final SysFileSettingDao sysFileSettingDao;

    private volatile Map<String, String> cache = Map.of();

    private volatile Set<String> overridableKeys = Set.of();

    private volatile long loadedAt = 0L;

    public SysFileSettingService(SysFileSettingDao sysFileSettingDao) {
        this.sysFileSettingDao = sysFileSettingDao;
    }

    /**
     * 获取字符串配置
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    public String getString(String key, String defaultValue) {
        String value = ensureLoaded().get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * 获取整数配置
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    public int getInt(String key, int defaultValue) {
        String value = getString(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("文件配置 [{}] 不是合法整数：{}，使用默认值 {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    /**
     * 获取布尔配置
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return "true".equalsIgnoreCase(value.trim()) || "1".equals(value.trim());
    }

    /**
     * 获取小数配置
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    public BigDecimal getDecimal(String key, BigDecimal defaultValue) {
        String value = getString(key, null);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            log.warn("文件配置 [{}] 不是合法数字：{}，使用默认值 {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    /**
     * 刷新本地缓存
     */
    public void refresh() {
        this.loadedAt = 0L;
    }

    /**
     * 确保缓存已加载
     *
     * @return 配置 Map
     */
    private Map<String, String> ensureLoaded() {
        long now = System.currentTimeMillis();
        if (now - loadedAt < CACHE_TTL_MS && !cache.isEmpty()) {
            return cache;
        }
        synchronized (this) {
            now = System.currentTimeMillis();
            if (now - loadedAt < CACHE_TTL_MS && !cache.isEmpty()) {
                return cache;
            }
            try {
                List<SysFileSettingEntity> list = sysFileSettingDao.findAll();
                Map<String, String> loaded = new HashMap<>(Math.max(16, list.size()));
                Set<String> overridable = new HashSet<>();
                for (SysFileSettingEntity entity : list) {
                    if (entity.getSettingKey() == null) {
                        continue;
                    }
                    String value = entity.getSettingValue();
                    if (value == null) {
                        value = entity.getDefaultValue();
                    }
                    loaded.put(entity.getSettingKey(), value);
                    if (Objects.equals(entity.getUserOverridable(), 1)) {
                        overridable.add(entity.getSettingKey());
                    }
                }
                this.cache = Collections.unmodifiableMap(loaded);
                this.overridableKeys = Set.copyOf(overridable);
                this.loadedAt = now;
            } catch (Exception e) {
                log.error("加载文件模块全局配置失败，沿用旧缓存", e);
                if (cache.isEmpty()) {
                    this.loadedAt = now;
                }
            }
            return cache;
        }
    }

    /**
     * 判断配置项是否允许用户覆盖
     *
     * @param key 配置键
     * @return true：允许
     */
    public boolean isUserOverridable(String key) {
        ensureLoaded();
        return !overridableKeys.contains(key);
    }

}
