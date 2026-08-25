package com.xht.workflow.deploy.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.deploy.domain.query.DeployPageQuery;
import com.xht.workflow.deploy.domain.response.ProcessDefinitionResponse;

/**
 * 描述： 流程部署服务
 *
 * @author xht
 **/
public interface IFlowDeployService {

    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionResponse> findPage(DeployPageQuery query);

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param query         查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionResponse> historyPage(String processDefKey, WorkFlowPageQuery query);

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    void deleteByDeployId(String deployId);

}
