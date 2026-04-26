package com.xht.framework.cache.utils;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import com.xht.framework.cache.constant.CacheConstant;
import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Redis 键生成工具类
 * 提供统一的 Redis 键生成和格式化方法，确保键命名规范一致
 *
 * @author xht
 */
@Slf4j
@SuppressWarnings("unused")
public final class Keys {

    private static final Random random = new SecureRandom();

    /**
     * 私有构造方法，防止工具类被实例化
     */
    private Keys() {
        throw new UtilException("禁止实例化工具类");
    }

    /**
     * 将多个字符串片段拼接成 Redis 键
     * 片段之间使用全局统一的分隔符连接
     *
     * @param keyParts 键的各个部分，不可为 null，但允许空数组（返回空字符串）
     * @return 拼接后的 Redis 键
     */
    public static String createKey(@Nullable Object... keyParts) {
        // 处理空数组情况
        if (ArrayUtil.isEmpty(keyParts)) {
            return "";
        }
        // 使用全局统一的分隔符拼接键片段
        return ArrayUtil.join(keyParts, CacheConstant.REDIS_KEY_DELIMITED);
    }

    /**
     * 根据模板和参数格式化生成 Redis 键
     * 支持类似 "user:{}:info" 的模板格式，使用指定参数替换占位符
     *
     * @param keyTemplate 键模板，不可为 null 且不能为空字符串
     * @param params      替换模板中占位符的参数，不可为 null（空数组表示无参数）
     * @return 格式化后的 Redis 键
     * @throws IllegalArgumentException 如果模板为空字符串
     */
    public static String createKeyTemplate(@NonNull final String keyTemplate, @NonNull Object... params) {
        // 验证模板有效性
        if (!StringUtils.hasText(keyTemplate)) {
            throw new IllegalArgumentException("键模板不能为空字符串");
        }

        // 无参数时直接返回模板
        if (ArrayUtil.isEmpty(params)) {
            return keyTemplate;
        }

        // 转换参数数组并格式化模板
        return StrFormatter.format(keyTemplate, Arrays.stream(params).toArray());
    }

    /**
     * 生成 Redis 随机过期时间（基础时间 + 浮动范围）
     * randomExpire(600,720) 生成600 ~ 720 秒随机
     *
     * @param baseSeconds 基础过期时间 数字
     * @param randomRange 随机浮动范围 数字
     * @return baseSeconds ~ baseSeconds+randomRange 之间的随机值
     */
    public static Long rangeExpire(long baseSeconds, long randomRange) {
        if (baseSeconds <= 0) {
            throw new UtilException("基础过期时间不能小于等于0");
        }
        if (randomRange <= 0) {
            throw new UtilException("随机浮动范围不能小于等于0");
        }
        long min = baseSeconds - randomRange;
        if (min <= 0) {
            min = baseSeconds;
        }
        // 保证过期时间不能为负数
        return random.nextLong(min, baseSeconds + randomRange);
    }

    /**
     * 获取随机过期时间 [min, max]
     * @param min 最小过期时间
     * @param max 最大过期时间
     * @return 随机过期时间
     */
    public static Long randomExpire(long min, long max) {
        if (min <= 0) {
            throw new UtilException("最小过期时间不能小于0");
        }
        if (max <= min) {
            throw new UtilException("最大过期时间不能小于等于最小过期时间");
        }
        return min + random.nextLong(max - min + 1);
    }
}
