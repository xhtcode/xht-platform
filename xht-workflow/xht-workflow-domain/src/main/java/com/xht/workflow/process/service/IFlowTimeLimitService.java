package com.xht.workflow.process.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.process.domain.query.FlowTimeLimitPageQuery;
import com.xht.workflow.process.domain.response.FlowTimeLimitResponse;

/**
 * 描述： 流程时限服务类
 *
 * @author xht
 **/
public interface IFlowTimeLimitService {

    /**
     * 获取流程时限详情
     *
     * @param id 流程时限ID
     * @return 流程时限详情
     */
    FlowTimeLimitResponse findById(Long id);

    /**
     * 分页查询流程时限
     *
     * @param query 流程时限查询参数
     * @return 流程时限分页信息
     */
    PageResponse<FlowTimeLimitResponse> findPageList(FlowTimeLimitPageQuery query);

}
