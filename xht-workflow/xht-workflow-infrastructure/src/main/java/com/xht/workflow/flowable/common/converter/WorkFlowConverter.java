package com.xht.workflow.flowable.common.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述： 工作流转换器
 *
 * @author xht
 **/
public interface WorkFlowConverter<S, T> {

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
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        return sourceList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

}
