package com.xht.workflow.flowable.core.converter;

import com.xht.workflow.flowable.core.model.ModelDTO;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 描述： 流程模型转换器
 *
 * @author xht
 **/
public interface ModelConverter<T> {

    /**
     * 将模型转换为模型DTO
     * @param model 模型
     * @return 模型DTO
     */
    ModelDTO convert(T model);

    /**
     * 将模型列表转换为模型DTO列表
     * @param models 模型列表
     * @return 模型DTO列表
     */
    default List<ModelDTO> convert(List<T> models) {
        if (CollectionUtils.isEmpty(models)) {
            return Collections.emptyList();
        }
        return models.stream().map(this::convert).toList();
    }

}
