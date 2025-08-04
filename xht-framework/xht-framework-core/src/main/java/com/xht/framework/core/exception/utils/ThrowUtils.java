package com.xht.framework.core.exception.utils;

import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.code.ErrorCode;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 异常处理工具类
 */
@SuppressWarnings("unused")
public final class ThrowUtils {
    /**
     * 立则抛异常
     *
     * @param errorCode 错误码
     */
    public static void throwDirect(ErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }

    /**
     * 立则抛异常
     *
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void throwDirect(ErrorCode errorCode, String message) {
        throw new BusinessException(errorCode, message);
    }


    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param supplier  异常
     */
    public static <T extends RuntimeException> void throwIf(boolean condition, Supplier<T> supplier) {
        if (condition) {
            throw supplier.get();
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition         条件
     * @param businessException 异常
     */
    public static void throwIf(boolean condition, BusinessException businessException) {
        if (condition) {
            throw businessException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }


    /**
     * 条件成立则抛异常
     *
     * @param condition         条件
     * @param businessException 异常
     */
    public static void throwIf(Supplier<Boolean> condition, BusinessException businessException) {
        if (condition.get()) {
            throw businessException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     */
    public static void throwIf(Supplier<Boolean> condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param errorCode 错误码
     * @param message   错误信息
     */
    public static void throwIf(Supplier<Boolean> condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }

    // -----------------------------------------------字符串相关 start-----------------------------------------------

    /**
     * 字符串为空抛出异常
     *
     * @param str        字符串
     * @param errMessage 异常描述
     */
    public static void hasText(String str, String errMessage) {
        if (!StringUtils.hasLength(str)) throw new BusinessException(GlobalErrorStatusCode.PARAM_INVALID, errMessage);
    }

    /**
     * 字符串为空抛出异常
     *
     * @param str           字符串
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void hasText(String str, Supplier<X> errorSupplier) throws X {
        if (!StringUtils.hasLength(str)) throw errorSupplier.get();
    }

    /**
     * 字符串为空抛出异常
     *
     * @param str             字符串
     * @param errorStatusCode 异常状态
     */
    public static <X extends Throwable> void hasText(String str, ErrorCode errorStatusCode) throws X {
        if (!StringUtils.hasLength(str)) throw new BusinessException(errorStatusCode);
    }

    // -----------------------------------------------字符串相关 end-----------------------------------------------
    // -----------------------------------------------对象 start-----------------------------------------------

    /**
     * 对象为空抛出异常
     *
     * @param object     对象
     * @param errMessage 异常描述
     */
    public static void notNull(Object object, String errMessage) {
        if (Objects.isNull(object)) throw new BusinessException(errMessage);
    }

    /**
     * 对象为空抛出异常
     *
     * @param object        对象
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notNull(Object object, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(object)) throw errorSupplier.get();
    }

    /**
     * 对象为空抛出异常
     *
     * @param object 对象
     */
    public static <X extends Throwable> void notNull(Object object) throws X {
        if (Objects.isNull(object)) throw new BusinessException(BusinessErrorCode.PARAM_ERROR);
    }

    /**
     * 对象为空抛出异常
     *
     * @param object          对象
     * @param errorStatusCode {@link ErrorCode} 异常状态
     */
    public static <X extends Throwable> void notNull(Object object, ErrorCode errorStatusCode) throws X {
        if (Objects.isNull(object)) throw new BusinessException(errorStatusCode);
    }

    // -----------------------------------------------对象 end-----------------------------------------------
    // -----------------------------------------------集合 start-----------------------------------------------

    /**
     * 集合为空抛出异常
     *
     * @param collection 集合
     * @param errMessage 异常信息
     */
    public static void notEmpty(Collection<?> collection, String errMessage) {
        if (Objects.isNull(collection) || collection.isEmpty()) throw new BusinessException(errMessage);
    }

    /**
     * 集合为空抛出异常
     *
     * @param collection    集合
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notEmpty(Collection<?> collection, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(collection) || collection.isEmpty()) throw errorSupplier.get();
    }


    /**
     * map集合为空抛出异常
     *
     * @param map        map集合
     * @param errMessage 异常信息
     */
    public static void notEmpty(Map<?, ?> map, String errMessage) {
        if (Objects.isNull(map) || map.isEmpty()) throw new BusinessException(errMessage);
    }


    /**
     * map集合为空抛出异常
     *
     * @param map           map集合
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <X extends Throwable> void notEmpty(Map<?, ?> map, Supplier<X> errorSupplier) throws X {
        if (Objects.isNull(map) || map.isEmpty()) throw errorSupplier.get();
    }

    // -----------------------------------------------集合 end-----------------------------------------------
    // -----------------------------------------------数组 start-----------------------------------------------

    /**
     * 数组为空抛出异常
     *
     * @param array      数组
     * @param errMessage 异常信息
     */
    public static <T> void notEmpty(T[] array, String errMessage) {
        if (array == null || array.length == 0) throw new BusinessException(errMessage);
    }


    /**
     * 数组为空抛出异常
     *
     * @param array         数组
     * @param errorSupplier 指定断言不通过时抛出的异常
     */
    public static <T, X extends Throwable> void notEmpty(T[] array, Supplier<X> errorSupplier) throws X {
        if (array == null || array.length == 0) throw errorSupplier.get();
    }

    // -----------------------------------------------数组 end-----------------------------------------------
}
