package com.xht.workflow.sequence.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.query.FlowSequencePageQuery;
import com.xht.workflow.sequence.domain.response.FlowSequenceResponse;

import java.util.List;

/**
 * 描述：流程序列管理表单
 *
 * @author xht
 **/
public interface IFlowSequenceService {

    /**
     * 创建流程序列
     *
     * @param form 流程序列信息
     */
    void create(FlowSequenceForm form);

    /**
     * 删除流程序列
     *
     * @param ids 流程序列ID集合
     */
    void removeById(List<Long> ids);

    /**
     * 修改流程序列
     *
     * @param form 流程序列信息
     */
    void updateById(FlowSequenceForm form);

    /**
     * 获取流程序列详情
     *
     * @param id 流程序列ID
     * @return 流程序列详情
     */
    FlowSequenceResponse findById(Long id);

    /**
     * 分页查询流程序列
     *
     * @param query 流程定义查询参数
     * @return 分页结果
     */
    PageResponse<FlowSequenceResponse> findPageList(FlowSequencePageQuery query);

}
