package com.xht.framework.utils;

import com.xht.framework.exception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;

/**
 * 描述： Map工具类
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("unused")
public final class MapUtils {

    private MapUtils() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 判断Map是否为空
     *
     * @param map 待校验对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空
     *
     * @param map 待校验对象
     * @return {@code true}: 不为空<br>{@code false}: 为空
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }


    /**
     * 获取Map的大小
     *
     * @param map 待校验对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static int size(final Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }


    /**
     * 反转Map，返回一个新的HashMap，使得输入映射中的键与值互换
     *
     * @param map 待反转Map
     * @return 反转后Map
     */
    public static <K, V> Map<V, K> invertMap(final Map<K, V> map) {
        Objects.requireNonNull(map, "map");
        final Map<V, K> out = new HashMap<>(map.size());
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            out.put(entry.getValue(), entry.getKey());
        }
        return out;
    }

    //-----------------------------------取值------------------------------------

    /**
     * 通用取值模板：优先使用取值函数获取结果，取不到（为 {@code null}）时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param getFunction  实际取值函数
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @param <R>          返回值类型
     * @return 取值结果，为空时返回默认值
     */
    private static <K, R> R applyDefaultFunction(final Map<? super K, ?> map, final K key, final BiFunction<Map<? super K, ?>, K, R> getFunction, final R defaultValue) {
        final R value = MapUtils.isNotEmpty(map) && Objects.nonNull(getFunction) ? getFunction.apply(map, key) : null;
        return Objects.nonNull(value) ? value : defaultValue;
    }

    /**
     * 从Map中获取指定键的原始对象
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 对应的值，Map为空或键不存在时返回 {@code null}
     */
    public static <K, V> Object getObject(final Map<? super K, V> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 从Map中获取指定键的原始对象，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @param <V>          值类型
     * @return 对应的值，取不到时返回默认值
     */
    public static <K, V> Object getObject(final Map<K, V> map, final K key, final Object defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getObject, defaultValue);
    }


    /**
     * 从Map中获取指定键的字符串值
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return 值的字符串表示（通过 {@link Object#toString()}），Map为空或值为 {@code null} 时返回 {@code null}
     */
    public static <K> String getString(final Map<? super K, ?> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * 从Map中获取指定键的字符串值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return 字符串值，取不到时返回默认值
     */
    public static <K> String getString(final Map<? super K, ?> map, final K key, final String defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getString, defaultValue);
    }

    //-----------------------------------Boolean------------------------------------

    /**
     * 从Map中获取指定键的布尔值
     * <p>转换规则：{@link Boolean} 直接返回；{@link String} 按 {@link Boolean#valueOf(String)} 解析；
     * {@link Number} 非0为 {@code true}，0为 {@code false}；其余类型返回 {@code null}。</p>
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return 布尔值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        final Object answer = map.get(key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Boolean) {
            return (Boolean) answer;
        }
        if (answer instanceof String) {
            return Boolean.valueOf((String) answer);
        }
        if (answer instanceof Number n) {
            return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return null;
    }

    /**
     * 从Map中获取指定键的布尔值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return 布尔值，取不到时返回默认值
     */
    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key, final Boolean defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getBoolean, defaultValue);
    }

    /**
     * 从Map中获取指定键的布尔基本类型值，取不到时返回 {@code false}
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return 布尔值，取不到时返回 {@code false}
     */
    public static <K> boolean getBooleanValue(final Map<? super K, ?> map, final K key) {
        return getBooleanValue(map, key, false);
    }

    /**
     * 从Map中获取指定键的布尔基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return 布尔值，取不到时返回默认值
     */
    public static <K> boolean getBooleanValue(final Map<? super K, ?> map, final K key, final boolean defaultValue) {
        final Boolean value = getBoolean(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Number------------------------------------

    /**
     * 从Map中获取指定键的数字值
     * <p>转换规则：{@link Number} 直接返回；{@link String} 按 {@link Double#valueOf(String)} 解析（空串或解析失败时返回 {@code null}）；
     * 其余类型返回 {@code null}。</p>
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return 数字值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Number getNumber(final Map<? super K, ?> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        final Object answer = map.get(key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Number) {
            return (Number) answer;
        }
        if (answer instanceof String) {
            try {
                final String text = ((String) answer).trim();
                return text.isEmpty() ? null : Double.valueOf(text);
            } catch (final NumberFormatException e) {
                log.warn("Map取值转换Number失败, key={}, value={}", key, answer);
                return null;
            }
        }
        return null;
    }

    /**
     * 从Map中获取指定键的数字值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return 数字值，取不到时返回默认值
     */
    public static <K> Number getNumber(final Map<? super K, ?> map, final K key, final Number defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getNumber, defaultValue);
    }

    //-----------------------------------Byte------------------------------------

    /**
     * 从Map中获取指定键的 {@link Byte} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Byte} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Byte getByte(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return Optional.of(answer)
                .map(Number::byteValue)
                .orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Byte} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Byte} 值，取不到时返回默认值
     */
    public static <K> Byte getByte(final Map<? super K, ?> map, final K key, final Byte defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getByte, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code byte} 基本类型值，取不到时返回 0
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code byte} 值，取不到时返回 0
     */
    public static <K> byte getByteValue(final Map<? super K, ?> map, final K key) {
        return getByteValue(map, key, (byte) 0);
    }

    /**
     * 从Map中获取指定键的 {@code byte} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code byte} 值，取不到时返回默认值
     */
    public static <K> byte getByteValue(final Map<? super K, ?> map, final K key, final byte defaultValue) {
        final Byte value = getByte(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Short------------------------------------

    /**
     * 从Map中获取指定键的 {@link Short} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Short} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Short getShort(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Short) {
            return (Short) answer;
        }
        return Optional.of(answer)
                .map(Number::shortValue)
                .orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Short} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Short} 值，取不到时返回默认值
     */
    public static <K> Short getShort(final Map<? super K, ?> map, final K key, final Short defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getShort, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code short} 基本类型值，取不到时返回 0
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code short} 值，取不到时返回 0
     */
    public static <K> short getShortValue(final Map<? super K, ?> map, final K key) {
        return getShortValue(map, key, (short) 0);
    }

    /**
     * 从Map中获取指定键的 {@code short} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code short} 值，取不到时返回默认值
     */
    public static <K> short getShortValue(final Map<? super K, ?> map, final K key, final short defaultValue) {
        final Short value = getShort(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Integer------------------------------------

    /**
     * 从Map中获取指定键的 {@link Integer} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Integer} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Integer getInteger(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return Optional.of(answer)
                .map(Number::intValue)
                .orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Integer} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Integer} 值，取不到时返回默认值
     */
    public static <K> Integer getInteger(final Map<? super K, ?> map, final K key, final Integer defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getInteger, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code int} 基本类型值，取不到时返回 0
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code int} 值，取不到时返回 0
     */
    public static <K> int getIntValue(final Map<? super K, ?> map, final K key) {
        return getIntValue(map, key, 0);
    }

    /**
     * 从Map中获取指定键的 {@code int} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code int} 值，取不到时返回默认值
     */
    public static <K> int getIntValue(final Map<? super K, ?> map, final K key, final int defaultValue) {
        final Integer value = getInteger(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Long------------------------------------

    /**
     * 从Map中获取指定键的 {@link Long} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Long} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Long getLong(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Long) {
            return (Long) answer;
        }
        return Optional.of(answer)
                .map(Number::longValue)
                .orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Long} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Long} 值，取不到时返回默认值
     */
    public static <K> Long getLong(final Map<? super K, ?> map, final K key, final Long defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getLong, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code long} 基本类型值，取不到时返回 0
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code long} 值，取不到时返回 0
     */
    public static <K> long getLongValue(final Map<? super K, ?> map, final K key) {
        return getLongValue(map, key, 0L);
    }

    /**
     * 从Map中获取指定键的 {@code long} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code long} 值，取不到时返回默认值
     */
    public static <K> long getLongValue(final Map<? super K, ?> map, final K key, final long defaultValue) {
        final Long value = getLong(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Float------------------------------------

    /**
     * 从Map中获取指定键的 {@link Float} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Float} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Float getFloat(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Float) {
            return (Float) answer;
        }
        return Optional.of(answer).map(Number::floatValue).orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Float} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Float} 值，取不到时返回默认值
     */
    public static <K> Float getFloat(final Map<? super K, ?> map, final K key, final Float defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getFloat, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code float} 基本类型值，取不到时返回 0.0f
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code float} 值，取不到时返回 0.0f
     */
    public static <K> float getFloatValue(final Map<? super K, ?> map, final K key) {
        return getFloatValue(map, key, 0.0f);
    }

    /**
     * 从Map中获取指定键的 {@code float} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code float} 值，取不到时返回默认值
     */
    public static <K> float getFloatValue(final Map<? super K, ?> map, final K key, final float defaultValue) {
        final Float value = getFloat(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Double------------------------------------

    /**
     * 从Map中获取指定键的 {@link Double} 值（基于 {@link #getNumber} 转换）
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Double} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Double getDouble(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Double) {
            return (Double) answer;
        }
        return Optional.of(answer).map(Number::doubleValue).orElse(null);
    }

    /**
     * 从Map中获取指定键的 {@link Double} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Double} 值，取不到时返回默认值
     */
    public static <K> Double getDouble(final Map<? super K, ?> map, final K key, final Double defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getDouble, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@code double} 基本类型值，取不到时返回 0.0d
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@code double} 值，取不到时返回 0.0d
     */
    public static <K> double getDoubleValue(final Map<? super K, ?> map, final K key) {
        return getDoubleValue(map, key, 0.0d);
    }

    /**
     * 从Map中获取指定键的 {@code double} 基本类型值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@code double} 值，取不到时返回默认值
     */
    public static <K> double getDoubleValue(final Map<? super K, ?> map, final K key, final double defaultValue) {
        final Double value = getDouble(map, key);
        return value != null ? value : defaultValue;
    }

    //-----------------------------------Map------------------------------------

    /**
     * 从Map中获取指定键的嵌套Map值
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return 嵌套Map，当值不是 {@link Map} 类型、源Map为空或键不存在时返回 {@code null}
     */
    public static <K> Map<?, ?> getMap(final Map<? super K, ?> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        final Object answer = map.get(key);
        return answer instanceof Map ? (Map<?, ?>) answer : null;
    }

    /**
     * 从Map中获取指定键的嵌套Map值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return 嵌套Map，取不到时返回默认值
     */
    public static <K> Map<?, ?> getMap(final Map<? super K, ?> map, final K key, final Map<?, ?> defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getMap, defaultValue);
    }

    /**
     * 从Map中获取指定键的 {@link Collection} 值
     *
     * @param map 源Map
     * @param key 键
     * @param <K> 键类型
     * @return {@link Collection} 值，取不到或无法转换时返回 {@code null}
     */
    public static <K> Collection<?> getCollection(final Map<? super K, ?> map, final K key) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        Object answer = map.get(key);
        if (answer instanceof Collection<?> answer1) {
            return answer1;
        }
        return null;
    }

    /**
     * 从Map中获取指定键的 {@link Collection} 值，取不到时返回默认值
     *
     * @param map          源Map
     * @param key          键
     * @param defaultValue 默认值
     * @param <K>          键类型
     * @return {@link Collection} 值，取不到时返回默认值
     */
    public static <K> Collection<?> getCollection(final Map<? super K, ?> map, final K key, final Collection<?> defaultValue) {
        return applyDefaultFunction(map, key, MapUtils::getCollection, defaultValue);
    }

}
