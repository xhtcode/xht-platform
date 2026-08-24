package com.xht.workflow.definition.service;

import com.xht.workflow.definition.domain.form.FlowDefinitionForm;
import com.xht.workflow.definition.domain.query.FlowDefinitionPageQuery;
import com.xht.workflow.definition.domain.response.FlowDefinitionResponse;

import java.util.List;

/**
 * 描述： 流程定义服务类
 *
 * @author xht
 **/
public interface IFlowDefinitionService {

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    void create(FlowDefinitionForm form);

    /**
     * 删除流程定义
     *
     * @param id) 流程定义ID
     */
    void removeById(Long id);

    /**
     * 修改流程定义
     *
     * @param id 流程定义ID
     * @param form 流程定义信息
     */
    void updateById(Long id, FlowDefinitionForm form);

    /**
     * 获取流程定义详情
     *
     * @param id 流程定义ID
     * @return 流程定义详情
     */
    FlowDefinitionResponse findById(Long id);

    /**
     * 获取流程定义列表
     *
     * @param flowDefinitionPageQuery 流程定义查询参数
     * @return 流程定义列表
     */
    List<FlowDefinitionResponse> findList(FlowDefinitionPageQuery flowDefinitionPageQuery);

}
