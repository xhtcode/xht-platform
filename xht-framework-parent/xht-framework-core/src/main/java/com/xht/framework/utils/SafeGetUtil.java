package com.xht.framework.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 安全取值工具类，避免空指针，获取字段值为空时返回默认值
 *
 * @author xht
 */
public abstract class SafeGetUtil {

    /**
     * 安全获取对象属性值
     * <p>对象为null 或者 属性值为null，都会返回默认值</p>
     *
     * @param e      源对象
     * @param getter 属性getter方法引用，例：SysFileTypeEntity::getFileSuffix
     * @return 属性值，为空则返回默认值
     */
    public static <E, T> T getSafe(E e, Function<E, T> getter) {
        if (e == null) {
            return null;
        }
        return getter.apply(e);
    }

    /**
     * 安全获取对象属性值
     * <p>对象为null 或者 属性值为null，都会返回默认值</p>
     *
     * @param e            源对象
     * @param getter       属性getter方法引用，例：SysFileTypeEntity::getFileSuffix
     * @param defaultValue 默认值提供者，惰性加载
     * @return 属性值，为空则返回默认值
     */
    public static <E, T> T getSafe(E e, Function<E, T> getter, Supplier<T> defaultValue) {
        if (e == null) {
            return defaultValue.get();
        }
        T val = getter.apply(e);
        return Objects.requireNonNullElseGet(val, defaultValue);
    }


    /**
     * 取值不为null时执行消费逻辑
     *
     * @param e        源对象
     * @param getter   属性getter方法引用
     * @param consumer 值不为null时执行的消费逻辑
     */
    public static <E, T> void ifPresent(E e, Function<E, T> getter, Consumer<T> consumer) {
        if (e == null) {
            return;
        }
        T val = getter.apply(e);
        if (val != null) {
            consumer.accept(val);
        }
    }

}
