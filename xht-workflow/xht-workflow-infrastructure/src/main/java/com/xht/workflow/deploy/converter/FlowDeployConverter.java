package com.xht.workflow.deploy.converter;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.workflow.deploy.domain.response.ProcessDefinitionResponse;
import com.xht.workflow.flowable.deploy.common.ProcessDefinitionDTO;
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
public class FlowDeployConverter {

    /**
     * 将流程定义DTO转换为流程定义响应对象
     *
     * @param processDefinitionDTO 流程定义DTO，可为null
     * @return 流程定义响应对象，若入参为null则返回null
     */
    public ProcessDefinitionResponse toResponse(ProcessDefinitionDTO processDefinitionDTO) {
        if (Objects.isNull(processDefinitionDTO)) {
            return null;
        }
        ProcessDefinitionResponse processDefinitionResponse = new ProcessDefinitionResponse();
        processDefinitionResponse.setProcessDefId(processDefinitionDTO.getProcessDefId());
        processDefinitionResponse.setProcessDefName(processDefinitionDTO.getProcessDefName());
        processDefinitionResponse.setProcessDefKey(processDefinitionDTO.getProcessDefKey());
        processDefinitionResponse.setProcessDefVersion(processDefinitionDTO.getProcessDefVersion());
        processDefinitionResponse.setSuspendedStatus(processDefinitionDTO.getSuspendedStatus());
        processDefinitionResponse.setTenantId(processDefinitionDTO.getTenantId());
        processDefinitionResponse.setDeploymentId(processDefinitionDTO.getDeploymentId());
        processDefinitionResponse.setDeploymentName(processDefinitionDTO.getDeploymentName());
        processDefinitionResponse.setDeploymentTime(processDefinitionDTO.getDeploymentTime());
        processDefinitionResponse.setDeploymentCategory(processDefinitionDTO.getDeploymentCategory());
        processDefinitionResponse.setDeploymentKey(processDefinitionDTO.getDeploymentKey());
        return processDefinitionResponse;
    }


    /**
     * 将流程定义DTO列表转换为流程定义响应对象列表
     *
     * @param definitionDTOS 流程定义DTO列表，可为null或空
     * @return 流程定义响应对象列表，非null（空列表而非null）
     */
    public List<ProcessDefinitionResponse> toResponse(List<ProcessDefinitionDTO> definitionDTOS) {
        if (CollectionUtils.isEmpty(definitionDTOS)) {
            return List.of();
        }
        return definitionDTOS.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 将分页的流程定义DTO转换为分页的流程定义响应对象
     *
     * @param definitionDTOS 分页的流程定义DTO，可为null
     * @return 分页的流程定义响应对象，非null
     */
    public PageResponse<ProcessDefinitionResponse> toResponse(PageResponse<ProcessDefinitionDTO> definitionDTOS) {
        if (Objects.isNull(definitionDTOS)) {
            return PageTool.empty();
        }
        return PageTool.createPageVo(definitionDTOS.getCurrent(), definitionDTOS.getSize(), definitionDTOS.getTotal(), toResponse(definitionDTOS.getRecords()));
    }
}
