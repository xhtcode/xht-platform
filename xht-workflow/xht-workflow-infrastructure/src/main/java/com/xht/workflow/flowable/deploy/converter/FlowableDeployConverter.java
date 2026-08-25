package com.xht.workflow.flowable.deploy.converter;

import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.flowable.deploy.common.ProcessDefinitionDTO;
import com.xht.workflow.flowable.utils.FlowableDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.repository.EngineDeployment;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述： 流程定义转换器
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FlowableDeployConverter implements DeployConverter<ProcessDefinition> {

    private final RepositoryService repositoryService;

    /**
     * 将源对象转换为目标对象
     *
     * @param source 源对象，非null
     * @return 转换后的目标对象，非null
     */
    @Override
    public ProcessDefinitionDTO convert(ProcessDefinition source) {
        if (Objects.isNull(source)) {
            return null;
        }
        ProcessDefinitionDTO processDefinitionDTO = basicConvert(source);
        EngineDeployment deployment = repositoryService.createDeploymentQuery().deploymentId(processDefinitionDTO.getDeploymentId()).singleResult();
        processDefinitionDTO.setDeploymentName(deployment.getName());
        processDefinitionDTO.setDeploymentTime(FlowableDateUtils.toLocalDateTime(deployment.getDeploymentTime()));
        processDefinitionDTO.setDeploymentCategory(deployment.getCategory());
        processDefinitionDTO.setDeploymentKey(deployment.getKey());
        return processDefinitionDTO;
    }

    /**
     * 将源对象列表转换为目标对象列表
     *
     * @param sourceList 源对象列表，可为null或空
     * @return 转换后的目标对象列表，非null（空列表而非null）
     */
    @Override
    public List<ProcessDefinitionDTO> convert(List<ProcessDefinition> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProcessDefinitionDTO> processDefinitionDTOS = sourceList.stream().map(this::basicConvert).toList();
        List<String> deploymentIds = sourceList.stream().map(ProcessDefinition::getDeploymentId).toList();
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().deploymentIds(deploymentIds).list();
        // deploymentList 根据部署id 转成map
        Map<String, Deployment> deploymentMap = deploymentList.stream().collect(Collectors.toMap(Deployment::getId, d -> d));
        for (ProcessDefinitionDTO processDefinitionDTO : processDefinitionDTOS) {
            Deployment deployment = deploymentMap.get(processDefinitionDTO.getDeploymentId());
            if (Objects.nonNull(deployment)) {
                processDefinitionDTO.setDeploymentName(deployment.getName());
                processDefinitionDTO.setDeploymentTime(FlowableDateUtils.toLocalDateTime(deployment.getDeploymentTime()));
                processDefinitionDTO.setDeploymentCategory(deployment.getCategory());
                processDefinitionDTO.setDeploymentKey(deployment.getKey());
            }
        }
        return processDefinitionDTOS;
    }

    /**
     * 基本转换
     *
     * @param source 源对象，非null
     * @return 转换后的目标对象，非null
     */
    private ProcessDefinitionDTO basicConvert(ProcessDefinition source) {
        ProcessDefinitionDTO processDefinitionDTO = new ProcessDefinitionDTO();
        processDefinitionDTO.setProcessDefId(source.getId());
        processDefinitionDTO.setProcessDefCategory(source.getCategory());
        processDefinitionDTO.setProcessDefName(source.getName());
        processDefinitionDTO.setProcessDefKey(source.getKey());
        processDefinitionDTO.setProcessDefDescription(source.getDescription());
        processDefinitionDTO.setProcessDefVersion(source.getVersion());
        processDefinitionDTO.setDeploymentId(source.getDeploymentId());
        processDefinitionDTO.setSuspendedStatus(source.isSuspended() ? SuspendedStatus.ACTIVE : SuspendedStatus.SUSPENDED);
        processDefinitionDTO.setTenantId(source.getTenantId());
        return processDefinitionDTO;
    }


}
