package com.xht.workflow.model.converter;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.flowable.core.MetaInfoBO;
import com.xht.workflow.flowable.core.model.ModelDTO;
import com.xht.workflow.model.domain.response.FlowModelResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述： 流程模型转换器
 *
 * @author xht
 **/
@Component
public class FlowModelConverter {

    /**
     * 将流程模型DTO转换为流程模型响应对象
     *
     * @param modelDTO 流程模型DTO
     * @return 流程模型响应对象，当入参为null时返回null
     */
    public FlowModelResponse toResponse(ModelDTO modelDTO) {
        if (Objects.isNull(modelDTO)) {
            return null;
        }
        MetaInfoBO metaInfoBO = MetaInfoBO.builder().of(modelDTO.getMetaInfo());
        FlowModelResponse response = new FlowModelResponse();
        response.setModelId(modelDTO.getModelId());
        response.setModelName(modelDTO.getModelName());
        response.setModelKey(modelDTO.getModelKey());
        response.setCategory(modelDTO.getCategory());
        response.setCategoryId(metaInfoBO.get("categoryId", String.class));
        response.setCreateTime(modelDTO.getCreateTime());
        response.setLastUpdateTime(modelDTO.getLastUpdateTime());
        response.setVersion(modelDTO.getVersion());
        response.setMetaInfo(metaInfoBO.getMetaInfo());
        response.setDeploymentId(modelDTO.getDeploymentId());
        response.setTenantId(modelDTO.getTenantId());
        return response;
    }


    /**
     * 将流程模型DTO列表转换为流程模型响应对象列表
     *
     * @param modelDTOS 流程模型DTO列表
     * @return 流程模型响应对象列表，当入参为空时返回不可变空列表
     */
    public List<FlowModelResponse> toResponse(List<ModelDTO> modelDTOS) {
        if (CollectionUtils.isEmpty(modelDTOS)) {
            return List.of(); // 使用Java 9+的不可变空列表
        }
        return modelDTOS.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将流程模型DTO分页结果转换为流程模型响应分页结果
     *
     * @param modelDTOPage 流程模型DTO分页结果
     * @return 流程模型响应分页结果，当入参为null时返回空分页结果
     */
    public PageResponse<FlowModelResponse> toResponse(PageResponse<ModelDTO> modelDTOPage) {
        if (Objects.isNull(modelDTOPage)) {
            return PageTool.empty();
        }
        return PageTool.createPageVo(modelDTOPage.getCurrent(), modelDTOPage.getSize(), modelDTOPage.getTotal(), toResponse(modelDTOPage.getRecords()));
    }
}
