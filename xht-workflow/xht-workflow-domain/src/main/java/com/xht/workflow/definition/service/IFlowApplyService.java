package com.xht.workflow.definition.service;

import com.xht.workflow.definition.domain.response.FlowDefinitionResponse;

import java.util.List;

/**
 * 描述： 申请服务
 *
 * @author xht
 **/
public interface IFlowApplyService {

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId 父级ID
     * @return 流程类别列表
     */
    List<FlowDefinitionResponse> getFlowCategoryItems(Long parentId);

}
