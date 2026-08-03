package com.xht.workflow.definition.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.entity.FlowListenerExecutionEntity;
import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;

/**
 * 流程扩展-执行监听器 Dao
 *
 * @author xht
 **/
public interface FlowListenerExecutionDao extends MapperRepository<FlowListenerExecutionEntity> {

    /**
     * 根据主键`id`更新流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    void updateFormRequest(FlowListenerExecutionBasicForm form);

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param page  分页信息
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    Page<FlowListenerExecutionEntity> findPageList(Page<FlowListenerExecutionEntity> page, FlowListenerExecutionQuery query);

}
