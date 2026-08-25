package com.xht.workflow.flowable.deploy.converter;

import com.xht.workflow.flowable.common.converter.ModelConverter;
import com.xht.workflow.flowable.model.common.ModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Component
public class FlowableDeployConverter implements ModelConverter<Object> {
    /**
     * 将模型转换为模型DTO
     *
     * @param model 模型
     * @return 模型DTO
     */
    @Override
    public ModelDTO convert(Object model) {
        return null;
    }
}
