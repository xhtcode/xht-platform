package com.xht.workflow.flowable.definition.engine;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.flowable.common.BpmnSupplier;
import com.xht.workflow.flowable.definition.ProcessDefinitionManager;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionDTO;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionPageQueryBO;
import com.xht.workflow.flowable.definition.converter.FlowableProcessDefinitionConverter;
import com.xht.workflow.flowable.utils.FlowableQueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.ProcessDefinitionQueryProperty;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDefinitionManagerImpl implements ProcessDefinitionManager {

    private final RepositoryService repositoryService;

    private final FlowableProcessDefinitionConverter flowableDeployConverter;


    /**
     * 根据流程定义id查询流程定义
     *
     * @param processDefId 流程定义id
     * @return 流程定义
     */
    @Override
    public ProcessDefinitionDTO findByProcessDefId(String processDefId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
        return flowableDeployConverter.convert(processDefinition);
    }

    /**
     * 分页查询流程定义
     *
     * @param processDefinitionPageQueryBO 查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionDTO> findPage(ProcessDefinitionPageQueryBO processDefinitionPageQueryBO) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion();
        String processDefinitionKey = processDefinitionPageQueryBO.getProcessDefinitionKey();
        if (StringUtils.hasText(processDefinitionKey)) {
            processDefinitionQuery.processDefinitionKeyLike(FlowableQueryUtils.appendLikePrefix(processDefinitionKey));
        }
        String processDefinitionName = processDefinitionPageQueryBO.getProcessDefinitionName();
        if (StringUtils.hasText(processDefinitionName)) {
            processDefinitionQuery.processDefinitionNameLike(FlowableQueryUtils.appendLikePrefix(processDefinitionName));
        }
        String processDefinitionCategory = processDefinitionPageQueryBO.getProcessDefinitionCategory();
        if (StringUtils.hasText(processDefinitionCategory)) {
            processDefinitionQuery.processDefinitionCategory(processDefinitionCategory);
        }
        SuspendedStatus suspended = processDefinitionPageQueryBO.getSuspended();
        if (Objects.equals(suspended, SuspendedStatus.ACTIVE)) {
            processDefinitionQuery.active();
        }
        if (Objects.equals(suspended, SuspendedStatus.SUSPENDED)) {
            processDefinitionQuery.suspended();
        }
        return getProcessDefinitionDTOPageResponse(processDefinitionPageQueryBO, processDefinitionQuery);
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param processDefinitionPageQueryBO         查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionDTO> historyPage(String processDefKey, ProcessDefinitionPageQueryBO processDefinitionPageQueryBO) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefKey)
                .desc();
        return getProcessDefinitionDTOPageResponse(processDefinitionPageQueryBO, processDefinitionQuery);
    }

    @NonNull
    private PageResponse<ProcessDefinitionDTO> getProcessDefinitionDTOPageResponse(ProcessDefinitionPageQueryBO processDefinitionPageQueryBO, ProcessDefinitionQuery processDefinitionQuery) {
        FlowableQueryUtils.fillOrder(processDefinitionQuery, processDefinitionPageQueryBO, (BpmnSupplier<QueryProperty, String>) name -> switch (name) {
            case "processDefinitionName" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME;
            case "processDefinitionKey" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY;
            case "processDefinitionCategory" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY;
            case "processDefinitionVersion" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION;
            default -> null;
        });
        return FlowableQueryUtils.findPage(processDefinitionQuery, processDefinitionPageQueryBO, flowableDeployConverter::convert);
    }

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeployId(String deployId) {
        Assert.hasText(deployId, "部署id不能为空");
        repositoryService.deleteDeployment(deployId);
    }

}
