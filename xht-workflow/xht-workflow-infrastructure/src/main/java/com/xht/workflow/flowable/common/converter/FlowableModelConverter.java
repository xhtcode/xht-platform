package com.xht.workflow.flowable.common.converter;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.xht.workflow.flowable.model.common.ModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.repository.Model;

import java.util.Objects;

/**
 * 描述： 流程模型转换器
 *
 * @author xht
 **/
@Slf4j
public class FlowableModelConverter implements ModelConverter<Model> {

    /**
     * 将模型转换为模型DTO
     *
     * @param model 模型
     * @return 模型DTO
     */
    @Override
    public ModelDTO convert(Model model) {
        if (Objects.isNull(model)) {
            return null;
        }
        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setModelId(model.getId());
        modelDTO.setModelName(model.getName());
        modelDTO.setModelKey(model.getKey());
        modelDTO.setCategory(model.getCategory());
        modelDTO.setCreateTime(LocalDateTimeUtil.of(model.getCreateTime()));
        modelDTO.setLastUpdateTime(LocalDateTimeUtil.of(model.getLastUpdateTime()));
        modelDTO.setVersion(model.getVersion());
        modelDTO.setMetaInfo(model.getMetaInfo());
        modelDTO.setDeploymentId(model.getDeploymentId());
        modelDTO.setTenantId(model.getTenantId());
        return modelDTO;
    }

}
