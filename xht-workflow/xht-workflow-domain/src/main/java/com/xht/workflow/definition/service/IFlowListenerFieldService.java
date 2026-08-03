package com.xht.workflow.definition.service;

import com.xht.workflow.definition.domain.form.FlowListenerFieldForm;

/**
 * 描述： 流程扩展-监听器（字段管理）Service接口
 *
 * @author xht
 **/
public interface IFlowListenerFieldService {

    /**
     * 创建流程扩展-监听器
     *
     * @param form 流程扩展-监听器表单请求参数
     * @param listenerType 监听器类型
     */
    void create(FlowListenerFieldForm form, ListenerType listenerType);

    /**
     * 根据主键`id`删除流程扩展-监听器
     *
     * @param id 流程扩展-监听器主键
     */
    void remove(Long id);

    /**
     * 根据主键`id`更新流程扩展-监听器
     *
     * @param form 流程扩展-监听器表单请求参数
     * @param listenerType 监听器类型
     */
    void updateById(FlowListenerFieldForm form, ListenerType listenerType);

    /**
     * 根据监听器类型查询流程扩展-监听器
     *
     * @author  流程扩展-监听器信息
     */
    enum ListenerType {
        Execution,
        TASK
    }
}
