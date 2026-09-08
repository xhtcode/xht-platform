package com.xht.workflow.process.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.process.domain.query.FlowBusinessPageQuery;
import com.xht.workflow.process.domain.response.FlowBusinessResponse;

/**
 * 描述： 流程业务服务类
 *
 * @author xht
 **/
public interface IFlowBusinessService {

    /**
     * 获取流程业务详情
     *
     * @param id 流程业务ID
     * @return 流程业务详情
     */
    FlowBusinessResponse findById(Long id);

    /**
     * 分页查询流程业务
     *
     * @param query 流程业务查询参数
     * @return 流程业务分页信息
     */
    PageResponse<FlowBusinessResponse> findPageList(FlowBusinessPageQuery query);

}
