package com.xht.workflow.flowable.process.converter;

import com.xht.workflow.flowable.process.common.ProcessInstanceDTO;
import com.xht.workflow.flowable.utils.FlowableDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述： 流程实例转换器
 *
 * @author xht
 **/
@Slf4j
@Component
public class FlowableProcessInstanceConverter implements ProcessInstanceConverter<ProcessInstance> {

    /**
     * 将流程实例转换为流程实例DTO
     *
     * @param source 流程实例，非null
     * @return 流程实例DTO，非null
     */
    @Override
    public ProcessInstanceDTO convert(ProcessInstance source) {
        if (Objects.isNull(source)) {
            return null;
        }
        ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
        processInstanceDTO.setProcessInstanceId(source.getId());
        processInstanceDTO.setName(source.getName());
        processInstanceDTO.setProcessDefId(source.getProcessDefinitionId());
        processInstanceDTO.setProcessDefKey(source.getProcessDefinitionKey());
        processInstanceDTO.setProcessDefName(source.getProcessDefinitionName());
        processInstanceDTO.setProcessDefVersion(source.getProcessDefinitionVersion());
        processInstanceDTO.setDeploymentId(source.getDeploymentId());
        processInstanceDTO.setBusinessKey(source.getBusinessKey());
        processInstanceDTO.setStartTime(FlowableDateUtils.toLocalDateTime(source.getStartTime()));
        processInstanceDTO.setEnded(source.isEnded());
        processInstanceDTO.setStartUserId(source.getStartUserId());
        processInstanceDTO.setProcessVariables(source.getProcessVariables());
        return processInstanceDTO;
    }
}
