package com.xht.workflow.definition.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.query.FlowFormPageQuery;
import com.xht.workflow.definition.domain.response.FlowFormResponse;

/**
 * 描述： 流程表单服务类
 *
 * @author xht
 **/
public interface IFlowFormService {

    /**
     * 创建流程表单
     *
     * @param form 流程表单信息
     */
    void create(FlowFormForm form);

    /**
     * 删除流程表单
     *
     * @param id 流程表单ID
     */
    void removeById(Long id);

    /**
     * 修改流程表单
     *
     * @param id   流程表单ID
     * @param form 流程表单信息
     */
    void updateById(Long id, FlowFormForm form);

    /**
     * 获取流程表单详情
     *
     * @param id 流程表单ID
     * @return 流程表单详情
     */
    FlowFormResponse findById(Long id);

    /**
     * 分页查询流程表单
     *
     * @param query 流程表单查询参数
     * @return 流程表单分页信息
     */
    PageResponse<FlowFormResponse> findPageList(FlowFormPageQuery query);

}
