package com.xht.workflow.flowable.deploy.engine;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.enums.SuspendedStatus;
import com.xht.workflow.flowable.common.BpmnSupplier;
import com.xht.workflow.flowable.deploy.DeployManager;
import com.xht.workflow.flowable.deploy.common.DeployPageQueryBO;
import com.xht.workflow.flowable.deploy.common.ProcessDefinitionDTO;
import com.xht.workflow.flowable.deploy.converter.FlowableDeployConverter;
import com.xht.workflow.flowable.utils.FlowableQueryUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.ProcessDefinitionQueryProperty;
import org.flowable.engine.repository.ProcessDefinitionQuery;
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
public class DeployManagerImpl implements DeployManager {

    private final RepositoryService repositoryService;

    private final FlowableDeployConverter flowableDeployConverter;


    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     * @return 分页流程定义列表
     */
    public PageResponse<ProcessDefinitionDTO> findPage(DeployPageQueryBO query) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().latestVersion();
        String processDefinitionKey = query.getProcessDefinitionKey();
        if (StringUtils.hasText(processDefinitionKey)) {
            processDefinitionQuery.processDefinitionKeyLike(FlowableQueryUtils.appendLikePrefix(processDefinitionKey));
        }
        String processDefinitionName = query.getProcessDefinitionName();
        if (StringUtils.hasText(processDefinitionName)) {
            processDefinitionQuery.processDefinitionNameLike(FlowableQueryUtils.appendLikePrefix(processDefinitionName));
        }
        String processDefinitionCategory = query.getProcessDefinitionCategory();
        if (StringUtils.hasText(processDefinitionCategory)) {
            processDefinitionQuery.processDefinitionCategory(processDefinitionCategory);
        }
        SuspendedStatus suspended = query.getSuspended();
        if (Objects.equals(suspended, SuspendedStatus.ACTIVE)) {
            processDefinitionQuery.active();
        }
        if (Objects.equals(suspended, SuspendedStatus.SUSPENDED)) {
            processDefinitionQuery.suspended();
        }
        FlowableQueryUtils.fillOrder(processDefinitionQuery, query, (BpmnSupplier<QueryProperty, String>) name -> switch (name) {
            case "processDefinitionName" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME;
            case "processDefinitionKey" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY;
            case "processDefinitionCategory" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY;
            case "processDefinitionVersion" -> ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION;
            default -> ProcessDefinitionQueryProperty.DEPLOYMENT_ID;
        });
        return FlowableQueryUtils.findPage(processDefinitionQuery, query, flowableDeployConverter::convert);
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param query         查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionDTO> historyPage(String processDefKey, DeployPageQueryBO query) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefKey)
                .orderByProcessDefinitionVersion()
                .desc();
        return FlowableQueryUtils.findPage(processDefinitionQuery, query, flowableDeployConverter::convert);
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
