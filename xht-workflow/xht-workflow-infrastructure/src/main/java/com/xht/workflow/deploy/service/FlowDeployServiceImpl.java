package com.xht.workflow.deploy.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.mybatis.utils.SortTool;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.deploy.converter.FlowDeployConverter;
import com.xht.workflow.deploy.domain.query.DeployPageQuery;
import com.xht.workflow.deploy.domain.response.ProcessDefinitionResponse;
import com.xht.workflow.flowable.deploy.DeployManager;
import com.xht.workflow.flowable.deploy.common.DeployPageQueryBO;
import com.xht.workflow.flowable.deploy.common.DeployPageQueryBuilder;
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
public class FlowDeployServiceImpl implements IFlowDeployService {

    private final DeployManager deployManager;

    private final FlowDeployConverter flowDeployConverter;

    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionResponse> findPage(DeployPageQuery query) {
        // @formatter:off
        DeployPageQueryBO pageQueryBO = DeployPageQueryBuilder.builder()
                .processDefinitionKey(query.getProcessDefinitionKey())
                .processDefinitionName(query.getProcessDefinitionName())
                .processDefinitionCategory(query.getProcessDefinitionCategory())
                .suspended(query.getSuspended())
                .current(query.getCurrent())
                .size(query.getSize())
                .asc(SortTool.getAscSort(query))
                .desc(SortTool.getDescSort(query))
                .build();
        // @formatter:on
        return flowDeployConverter.toResponse(deployManager.findPage(pageQueryBO));
    }

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param query         查询条件
     * @return 分页流程定义列表
     */
    @Override
    public PageResponse<ProcessDefinitionResponse> historyPage(String processDefKey, WorkFlowPageQuery query) {
        // @formatter:off
        DeployPageQueryBO pageQueryBO = DeployPageQueryBuilder.builder()
                .current(query.getCurrent())
                .size(query.getSize())
                .asc(SortTool.getAscSort(query))
                .desc(SortTool.getDescSort(query))
                .build();
        // @formatter:on
        return flowDeployConverter.toResponse(deployManager.historyPage(processDefKey, pageQueryBO));
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
        deployManager.deleteByDeployId(deployId);
    }
}
