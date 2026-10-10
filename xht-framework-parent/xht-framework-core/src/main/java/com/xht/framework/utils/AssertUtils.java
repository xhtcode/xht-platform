package com.xht.framework.utils;


import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.ErrorCode;
import org.springframework.lang.Contract;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 描述： 断言工具类
 * <p>
 * 模仿{@link org.springframework.util.Assert} 但是抛出 {@link BusinessException}；
 * 本类为静态工具类，abstract 声明用于防止实例化，直接通过类名调用方法即可
 * <p>
 * 每个断言方法均提供四种重载：
 * <ul>
 *     <li>传入提示信息：失败时抛出默认错误码（500）的 {@link BusinessException}</li>
 *     <li>传入 {@link ErrorCode} 错误码：失败时错误码与提示信息均取自错误枚举</li>
 *     <li>传入 {@link ErrorCode} 错误码与提示信息：失败时错误码取自错误枚举，提示信息使用自定义文案</li>
 *     <li>传入 {@link Supplier}：失败时抛出 Supplier 提供的自定义运行时异常</li>
 * </ul>
 * 提示：{@link Supplier} 为 null 或返回 null 时，为保证断言语义，统一抛出提示明确的 {@link BusinessException}
 *
 * @author xht
 **/
public abstract class AssertUtils {

    /**
     * 直接抛出携带提示信息的 {@link BusinessException}（无条件抛出）
     *
     * @param message 异常提示信息
     */
    @Contract("_ -> fail")
    public static void fail(String message) {
        throw new BusinessException(message);
    }

    /**
     * 直接抛出携带错误码的 {@link BusinessException}（无条件抛出）
     *
     * @param errorCode 错误码
     */
    @Contract("_ -> fail")
    public static void fail(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }

    /**
     * 直接抛出携带错误码和提示信息的 {@link BusinessException}（无条件抛出）
     *
     * @param errorCode 错误码
     * @param message   异常提示信息
     */
    @Contract("_, _ -> fail")
    public static void fail(ErrorCode errorCode, String message) {
        throw new BusinessException(errorCode, message);
    }

    /**
     * 直接抛出 自定义运行时{@link RuntimeException}（无条件抛出）
     *
     * @param supplier 无条件抛出时提供异常对象
     */
    @Contract("_ -> fail")
    public static <T extends RuntimeException> void fail(Supplier<T> supplier) {
        throw getException(supplier);
    }

    /**
     * 断言布尔表达式为 true，否则抛出 {@link BusinessException}
     *
     * @param expression 布尔表达式
     * @param message    断言失败时的异常提示信息
     */
    @Contract("false, _ -> fail")
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言布尔表达式为 true，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param expression 布尔表达式
     * @param errorCode  断言失败时的错误码
     */
    @Contract("false, _ -> fail")
    public static void isTrue(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言布尔表达式为 true，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param expression 布尔表达式
     * @param errorCode  断言失败时的错误码
     * @param message    断言失败时的异常提示信息
     */
    @Contract("false, _, _ -> fail")
    public static void isTrue(boolean expression, ErrorCode errorCode, String message) {
        if (!expression) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言布尔表达式为 true，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param expression 布尔表达式
     * @param supplier   断言失败时提供异常对象
     */
    @Contract("false, _ -> fail")
    public static <T extends RuntimeException> void isTrue(boolean expression, Supplier<T> supplier) {
        if (!expression) {
            throw getException(supplier);
        }
    }

    /**
     * 断言对象为 null，否则抛出 {@link BusinessException}
     *
     * @param object  待检查的对象
     * @param message 断言失败时的异常提示信息
     */
    @Contract("!null, _ -> fail")
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言对象为 null，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param object    待检查的对象
     * @param errorCode 断言失败时的错误码
     */
    @Contract("!null, _ -> fail")
    public static void isNull(Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言对象为 null，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param object    待检查的对象
     * @param errorCode 断言失败时的错误码
     * @param message   断言失败时的异常提示信息
     */
    @Contract("!null, _, _ -> fail")
    public static void isNull(Object object, ErrorCode errorCode, String message) {
        if (object != null) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言对象为 null，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param object   待检查的对象
     * @param supplier 断言失败时提供异常对象
     */
    @Contract("!null, _ -> fail")
    public static <T extends RuntimeException> void isNull(Object object, Supplier<T> supplier) {
        if (object != null) {
            throw getException(supplier);
        }
    }

    /**
     * 断言对象不为 null，否则抛出 {@link BusinessException}
     *
     * @param object  待检查的对象
     * @param message 断言失败时的异常提示信息
     */
    @Contract("null, _ -> fail")
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言对象不为 null，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param object    待检查的对象
     * @param errorCode 断言失败时的错误码
     */
    @Contract("null, _ -> fail")
    public static void notNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言对象不为 null，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param object    待检查的对象
     * @param errorCode 断言失败时的错误码
     * @param message   断言失败时的异常提示信息
     */
    @Contract("null, _, _ -> fail")
    public static void notNull(Object object, ErrorCode errorCode, String message) {
        if (object == null) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言对象不为 null，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param object   待检查的对象
     * @param supplier 断言失败时提供异常对象
     */
    @Contract("null, _ -> fail")
    public static <T extends RuntimeException> void notNull(Object object, Supplier<T> supplier) {
        if (object == null) {
            throw getException(supplier);
        }
    }

    /**
     * 断言字符串包含有效文本（非 null 且至少包含一个非空白字符），否则抛出 {@link BusinessException}
     *
     * @param text    待检查的字符串
     * @param message 断言失败时的异常提示信息
     */
    @Contract("null, _ -> fail")
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言字符串包含有效文本（非 null 且至少包含一个非空白字符），否则抛出携带错误码的 {@link BusinessException}
     *
     * @param text      待检查的字符串
     * @param errorCode 断言失败时的错误码
     */
    @Contract("null, _ -> fail")
    public static void hasText(String text, ErrorCode errorCode) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言字符串包含有效文本（非 null 且至少包含一个非空白字符），否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param text      待检查的字符串
     * @param errorCode 断言失败时的错误码
     * @param message   断言失败时的异常提示信息
     */
    @Contract("null, _, _ -> fail")
    public static void hasText(String text, ErrorCode errorCode, String message) {
        if (!StringUtils.hasText(text)) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言字符串包含有效文本（非 null 且至少包含一个非空白字符），否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param text     待检查的字符串
     * @param supplier 断言失败时提供异常对象
     */
    @Contract("null, _ -> fail")
    public static <T extends RuntimeException> void hasText(String text, Supplier<T> supplier) {
        if (!StringUtils.hasText(text)) {
            throw getException(supplier);
        }
    }

    /**
     * 断言数组非 null 且至少包含一个元素，否则抛出 {@link BusinessException}
     *
     * @param array   待检查的数组
     * @param message 断言失败时的异常提示信息
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言数组非 null 且至少包含一个元素，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param array     待检查的数组
     * @param errorCode 断言失败时的错误码
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        if (array == null || array.length == 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言数组非 null 且至少包含一个元素，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param array     待检查的数组
     * @param errorCode 断言失败时的错误码
     * @param message   断言失败时的异常提示信息
     */
    @Contract("null, _, _ -> fail")
    public static void notEmpty(Object[] array, ErrorCode errorCode, String message) {
        if (array == null || array.length == 0) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言数组非 null 且至少包含一个元素，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param array    待检查的数组
     * @param supplier 断言失败时提供异常对象
     */
    @Contract("null, _ -> fail")
    public static <T extends RuntimeException> void notEmpty(Object[] array, Supplier<T> supplier) {
        if (array == null || array.length == 0) {
            throw getException(supplier);
        }
    }

    /**
     * 断言集合非 null 且至少包含一个元素，否则抛出 {@link BusinessException}
     *
     * @param collection 待检查的集合
     * @param message    断言失败时的异常提示信息
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言集合非 null 且至少包含一个元素，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param collection 待检查的集合
     * @param errorCode  断言失败时的错误码
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言集合非 null 且至少包含一个元素，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param collection 待检查的集合
     * @param errorCode  断言失败时的错误码
     * @param message    断言失败时的异常提示信息
     */
    @Contract("null, _, _ -> fail")
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言集合非 null 且至少包含一个元素，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param collection 待检查的集合
     * @param supplier   断言失败时提供异常对象
     */
    @Contract("null, _ -> fail")
    public static <T extends RuntimeException> void notEmpty(Collection<?> collection, Supplier<T> supplier) {
        if (collection == null || collection.isEmpty()) {
            throw getException(supplier);
        }
    }

    /**
     * 断言 Map 非 null 且至少包含一个键值对，否则抛出 {@link BusinessException}
     *
     * @param map     待检查的 Map
     * @param message 断言失败时的异常提示信息
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    /**
     * 断言 Map 非 null 且至少包含一个键值对，否则抛出携带错误码的 {@link BusinessException}
     *
     * @param map       待检查的 Map
     * @param errorCode 断言失败时的错误码
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (map == null || map.isEmpty()) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 断言 Map 非 null 且至少包含一个键值对，否则抛出携带错误码和提示信息的 {@link BusinessException}
     *
     * @param map       待检查的 Map
     * @param errorCode 断言失败时的错误码
     * @param message   断言失败时的异常提示信息
     */
    @Contract("null, _, _ -> fail")
    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode, String message) {
        if (map == null || map.isEmpty()) {
            throw new BusinessException(errorCode, message);
        }
    }

    /**
     * 断言 Map 非 null 且至少包含一个键值对，否则抛出 自定义运行时{@link RuntimeException}
     *
     * @param map      待检查的 Map
     * @param supplier 断言失败时提供异常对象
     */
    @Contract("null, _ -> fail")
    public static <T extends RuntimeException> void notEmpty(Map<?, ?> map, Supplier<T> supplier) {
        if (map == null || map.isEmpty()) {
            throw getException(supplier);
        }
    }

    /**
     * 获取 {@link Supplier} 提供的异常对象，供自定义异常重载内部调用；
     * Supplier 为 null 或返回 null 时，统一抛出提示明确的 {@link BusinessException}
     *
     * @param supplier 提供异常对象
     * @return 异常对象
     */
    private static <T extends RuntimeException> T getException(Supplier<T> supplier) {
        if (supplier == null) {
            throw new BusinessException("断言失败时，Supplier 不能为 null");
        }
        T exception = supplier.get();
        if (exception == null) {
            throw new BusinessException("断言失败时，Supplier 提供的异常对象不能为 null");
        }
        return exception;
    }
}
