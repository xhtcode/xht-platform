package com.xht.workflow.flowable.definition;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionDTO;
import com.xht.workflow.flowable.definition.common.ProcessDefinitionPageQueryBO;

/**
 * 描述： 部署管理器
 *
 * @author xht
 **/
public interface ProcessDefinitionManager {

    /**
     * 根据流程定义id查询流程定义
     *
     * @param processDefId 流程定义id
     * @return 流程定义
     */
    ProcessDefinitionDTO findByProcessDefId(String processDefId);

    /**
     * 分页查询流程定义
     *
     * @param query 查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionDTO> findPage(ProcessDefinitionPageQueryBO query);

    /**
     * 分页查询历史流程定义
     *
     * @param processDefKey 流程定义key
     * @param query         查询条件
     * @return 分页流程定义列表
     */
    PageResponse<ProcessDefinitionDTO> historyPage(String processDefKey, ProcessDefinitionPageQueryBO query);

    /**
     * 根据流程部署id 删除流程
     *
     * @param deployId 流程部署id
     */
    void deleteByDeployId(String deployId);

}
