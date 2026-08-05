package com.xht.framework.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：复制转换器
 *
 * @author xht
 **/
public interface CopyConverter<T> {

    /**
     * 复制转换
     *
     * @param t 待转换对象
     * @return 转换后对象
     */
    T copySource(T t);

    /**
     * 复制转换列表
     *
     * @param list 待转换列表
     * @return 转换后列表
     */
    default List<T> copySourceList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map(this::copySource).collect(Collectors.toList());
    }

}
