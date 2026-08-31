package com.xht.workflow.definition.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.domain.response.FlowItemProcessResponse;

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
     * 分页查询流程定义
     *
     * @param query 流程定义查询参数
     * @return 流程定义分页信息
     */
    PageResponse<FlowItemProcessResponse> findPageList(FlowItemProcessPageQuery query);

}
