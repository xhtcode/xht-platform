package com.xht.framework.cache.utils;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ArrayUtil;
import com.xht.framework.cache.core.CacheConstant;
import com.xht.framework.cache.core.CacheErrorCode;
import com.xht.framework.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Redis Key 工具类
 * <p>统一规范 Redis Key 的构建、格式化，同时提供缓存随机过期时间生成方法，
 * 打散缓存过期时间，避免大量缓存同时失效引发缓存雪崩。</p>
 *
 * @author xht
 */
@Slf4j
@SuppressWarnings("unused")
public abstract class Keys {

    /**
     * 安全随机数，用于生成随机过期时间，降低缓存雪崩风险
     */
    private static final Random random = new SecureRandom();

    /**
     * 拼接生成 Redis Key
     * <p>多个片段使用统一分隔符 {@link CacheConstant#REDIS_KEY_DELIMITED} 拼接</p>
     *
     * @param keyParts key 片段数组，支持传null，空数组返回空字符串
     * @return 拼接完成的 Redis Key
     */
    public static String createKey(@Nullable Object... keyParts) {
        AssertUtils.notEmpty(keyParts, CacheErrorCode.CACHE_KEY_ILLEGAL);
        return ArrayUtil.join(keyParts, CacheConstant.REDIS_KEY_DELIMITED);
    }

    /**
     * 模板方式格式化 Redis Key
     * <p>模板示例：user:{}:info，使用 util StrFormatter 填充占位符</p>
     *
     * @param keyTemplate key模板，不能为空
     * @param params      占位符填充参数
     * @return 格式化后的 Redis Key
     * @throws IllegalArgumentException 模板为空时抛出
     */
    public static String createKeyTemplate(@NonNull final String keyTemplate, @NonNull Object... params) {
        AssertUtils.hasText(keyTemplate, CacheErrorCode.CACHE_KEY_TEMPLATE_ILLEGAL);
        if (ArrayUtil.isEmpty(params)) {
            return keyTemplate;
        }
        return StrFormatter.format(keyTemplate, Arrays.stream(params).toArray());
    }

    /**
     * 以基准时间上下浮动生成随机过期秒数
     * <p>取值区间：[baseSeconds - randomRange, baseSeconds + randomRange)，左闭右开，取不到上限值</p>
     * <p>兜底规则：如果 baseSeconds - randomRange ≤ 0，则下限自动改为 baseSeconds，只向上浮动，防止过期时间≤0</p>
     * <p>示例：rangeExpire(600,100) → 500 ~ 700 之间随机（不含700）</p>
     *
     * @param baseSeconds 基准过期秒数
     * @param randomRange 上下浮动幅度
     * @return 随机过期秒数（Long）
     */
    public static Long rangeExpire(long baseSeconds, long randomRange) {
        AssertUtils.isTrue(baseSeconds > 0, CacheErrorCode.CACHE_BASE_TTL_ILLEGAL);
        AssertUtils.isTrue(randomRange > 0, CacheErrorCode.CACHE_RANDOM_FLOAT_RANGE_ILLEGAL);
        long min = baseSeconds - randomRange;
        // 保护下限，避免过期时间为负数
        if (min <= 0) {
            min = baseSeconds;
        }
        return random.nextLong(min, baseSeconds + randomRange);
    }

    /**
     * 在指定闭区间 [min, max] 内生成随机过期秒数
     * <p>min 和 max 都有机会取到，适合直接指定过期时间的最小、最大值场景</p>
     * <p>示例：randomExpire(600,720) → 600 ~ 720 之间随机（包含600和720）</p>
     *
     * @param min 最小过期秒数（>0）
     * @param max 最大过期秒数（> min）
     * @return 区间内随机过期秒数（Long）
     */
    public static Long randomExpire(long min, long max) {
        AssertUtils.isTrue(min > 0, CacheErrorCode.CACHE_MIN_TTL_ILLEGAL);
        AssertUtils.isTrue(max > min, CacheErrorCode.CACHE_MAX_TTL_ILLEGAL);
        return min + random.nextLong(max - min + 1);
    }
}
