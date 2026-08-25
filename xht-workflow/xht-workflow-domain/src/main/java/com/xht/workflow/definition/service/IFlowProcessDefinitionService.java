package com.xht.workflow.definition.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.common.domain.query.WorkFlowPageQuery;
import com.xht.workflow.definition.domain.query.ProcessDefinitionPageQuery;
import com.xht.workflow.definition.domain.response.ProcessDefinitionResponse;

/**
 * 描述： 流程部署服务
 *
 * @author xht
 **/
public interface IFlowProcessDefinitionService {

    /**
     * 分页查询流程定义
     *
     * @param processDefinitionPageQuery 查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionResponse> findPage(ProcessDefinitionPageQuery processDefinitionPageQuery);

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param workFlowPageQuery         查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionResponse> historyPage(String processDefKey, WorkFlowPageQuery workFlowPageQuery);

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    void deleteByDeployId(String deployId);

}
