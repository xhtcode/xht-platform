package com.xht.framework.core.converter;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象转换器接口，用于在源对象和目标对象之间进行双向转换
 *
 * @param <S> 源对象类型，不能为null
 * @param <T> 目标对象类型，不能为null
 * @author xht
 */
@SuppressWarnings("unused")
public interface IConverter<S, T> {

    /**
     * 将源对象转换为目标对象
     *
     * @param source 源对象，非null
     * @return 转换后的目标对象，非null
     */
    T convert(S source);

    /**
     * 将源对象列表转换为目标对象列表
     *
     * @param sourceList 源对象列表，可为null或空
     * @return 转换后的目标对象列表，非null（空列表而非null）
     */
    default List<T> convert(List<S> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 将目标对象反转转换为源对象
     *
     * @param target 目标对象，非null
     * @return 反转转换后的源对象，非null
     */
    S reverse(T target);

    /**
     * 将目标对象列表反转转换为源对象列表
     *
     * @param targetList 目标对象列表，可为null或空
     * @return 反转转换后的源对象列表，非null（空列表而非null）
     */
    default List<S> reverse(List<T> targetList) {
        if (CollectionUtils.isEmpty(targetList)) {
            return Collections.emptyList();
        }
        return targetList.stream()
                .map(this::reverse)
                .collect(Collectors.toList());
    }
}