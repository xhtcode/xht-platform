package com.xht.workflow.definition.service;

import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.domain.response.FlowListenerExecutionResponse;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.workflow.definition.domain.vo.FlowListenerExecutionVO;

/**
 * 流程扩展-执行监听器 Service接口
 *
 * @author xht
 */
public interface IFlowListenerExecutionService {

    /**
     * 创建流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    void create(FlowListenerExecutionBasicForm form);

    /**
     * 根据主键`id`删除流程扩展-执行监听器
     *
     * @param id 流程扩展-执行监听器主键
     */
    void remove(Long id);

    /**
     * 根据主键`id`更新流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    void updateById(FlowListenerExecutionBasicForm form);

    /**
     * 根据主键`listenerId`查询流程扩展-执行监听器
     *
     * @param listenerId 流程扩展-执行监听器主键
     * @return 流程扩展-执行监听器信息
     */
    FlowListenerExecutionVO findByListenerId(Long listenerId);

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    PageResponse<FlowListenerExecutionResponse> findPageList(FlowListenerExecutionQuery query);

}
