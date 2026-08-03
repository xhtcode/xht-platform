package com.xht.workflow.definition.service;

import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.workflow.definition.domain.response.FlowListenerTaskResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;
import com.xht.workflow.definition.domain.vo.FlowListenerTaskVO;

/**
 * 描述： 流程定义服务接口
 *
 * @author xht
 **/
public interface IFlowBpmnService {

    /**
     * 根据主键`listenerId`查询流程扩展-执行监听器
     *
     * @param listenerId 流程扩展-执行监听器主键
     * @return 流程扩展-执行监听器信息
     */
    FlowListenerExecutionVO findListenerExecutionById(Long listenerId);

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    PageResponse<FlowListenerExecutionResponse> findListenerExecutionPage(FlowListenerExecutionQuery query);

    /**
     * 根据主键`listenerId`查询流程扩展-任务监听器
     *
     * @param listenerId 流程扩展-任务监听器主键
     * @return 流程扩展-任务监听器信息
     */
    FlowListenerTaskVO findListenerTaskById(Long listenerId);

    /**
     * 分页查询流程扩展-任务监听器
     *
     * @param query 流程扩展-任务监听器查询请求参数
     * @return 流程扩展-任务监听器分页信息
     */
    PageResponse<FlowListenerTaskResponse> findListenerTaskPage(FlowListenerTaskQuery query);

}
