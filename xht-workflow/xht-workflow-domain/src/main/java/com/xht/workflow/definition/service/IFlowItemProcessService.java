package com.xht.workflow.definition.service;

import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemProcessResponse;

import java.util.List;

/**
 * 描述： 流程定义服务类
 *
 * @author xht
 **/
public interface IFlowItemProcessService {

    /**
     * 创建流程定义
     *
     * @param form 流程定义信息
     */
    void create(FlowItemProcessForm form);

    /**
     * 删除流程定义
     *
     * @param id 流程定义ID
     */
    void removeById(Long id);

    /**
     * 修改流程定义
     *
     * @param id   流程定义ID
     * @param form 流程定义信息
     */
    void updateById(Long id, FlowItemProcessForm form);

    /**
     * 获取流程定义详情
     *
     * @param id 流程定义ID
     * @return 流程定义详情
     */
    FlowItemProcessResponse findById(Long id);

    /**
     * 获取流程定义列表
     *
     * @param query 流程定义查询参数
     * @return 流程定义列表
     */
    List<FlowItemProcessResponse> findList(FlowItemProcessPageQuery query);

}
