package com.xht.framework.core.converter;

/**
 * 转换器接口
 *
 * @author xht
 **/
public interface IConverter<S, T> {


    /**
     * 转换
     *
     * @param source 源对象
     * @return 转换后的对象
     */
    T convert(S source);

    /**
     * 反转
     *
     * @param target 目标对象
     * @return 反转后的对象
     */
    default S reverse(T target) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

}
