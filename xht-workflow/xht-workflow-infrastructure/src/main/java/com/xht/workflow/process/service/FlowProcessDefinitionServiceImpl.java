package com.xht.workflow.process.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.SortTool;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.flowable.common.bo.BpmnOrder;
import com.xht.workflow.flowable.definition.ProcessDefinitionManager;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionPageQueryBO;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionQueryBuilder;
import com.xht.workflow.process.converter.FlowProcessDefinitionConverter;
import com.xht.workflow.process.domain.query.ProcessDefinitionPageQuery;
import com.xht.workflow.process.domain.response.ProcessDefinitionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 描述： 流程部署服务实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FlowProcessDefinitionServiceImpl implements IFlowProcessDefinitionService {

    private final ProcessDefinitionManager processDefinitionManager;

    private final FlowProcessDefinitionConverter flowProcessDefinitionConverter;

    /**
     * 分页查询流程定义
     *
     * @param processDefinitionPageQuery 查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionResponse> findPage(ProcessDefinitionPageQuery processDefinitionPageQuery) {
        // @formatter:off
        ProcessDefinitionPageQueryBO pageQueryBO = ProcessDefinitionQueryBuilder.builder()
                .processDefinitionKey(processDefinitionPageQuery.getProcessDefinitionKey())
                .processDefinitionName(processDefinitionPageQuery.getProcessDefinitionName())
                .processDefinitionCategory(processDefinitionPageQuery.getProcessDefinitionCategory())
                .suspended(processDefinitionPageQuery.getSuspended())
                .current(processDefinitionPageQuery.getCurrent())
                .size(processDefinitionPageQuery.getSize())
                .asc(SortTool.getAscSort(processDefinitionPageQuery))
                .desc(SortTool.getDescSort(processDefinitionPageQuery))
                .defaultOrder("processDefinitionVersion", BpmnOrder.BpmnOrderType.DESC)
                .build();
        // @formatter:on
        return flowProcessDefinitionConverter.toResponse(processDefinitionManager.findPage(pageQueryBO));
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param workFlowPageQuery         查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionResponse> historyPage(String processDefKey, WorkFlowPageQuery workFlowPageQuery) {
        // @formatter:off
        ProcessDefinitionPageQueryBO pageQueryBO = ProcessDefinitionQueryBuilder.builder()
                .current(workFlowPageQuery.getCurrent())
                .size(workFlowPageQuery.getSize())
                .asc(SortTool.getAscSort(workFlowPageQuery))
                .desc(SortTool.getDescSort(workFlowPageQuery))
                .defaultOrder("processDefinitionVersion", BpmnOrder.BpmnOrderType.DESC)
                .build();
        // @formatter:on
        return flowProcessDefinitionConverter.toResponse(processDefinitionManager.historyPage(processDefKey, pageQueryBO));
    }

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeployId(String deployId) {
        Assert.notNull(deployId, "部署ID不能为空");
        processDefinitionManager.deleteByDeployId(deployId);
    }
}
