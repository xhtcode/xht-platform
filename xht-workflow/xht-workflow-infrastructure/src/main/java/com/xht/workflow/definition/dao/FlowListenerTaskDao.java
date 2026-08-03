package com.xht.workflow.definition.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.entity.FlowListenerTaskEntity;
import com.xht.workflow.definition.domain.form.FlowListenerTaskBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;

/**
 * 流程扩展-任务监听器 Dao
 *
 * @author xht
 **/
public interface FlowListenerTaskDao extends MapperRepository<FlowListenerTaskEntity> {

    /**
     * 根据主键`id`更新流程扩展-任务监听器
     *
     * @param form 流程扩展-任务监听器表单请求参数
     */
    void updateFormRequest(FlowListenerTaskBasicForm form);

    /**
     * 分页查询流程扩展-任务监听器
     *
     * @param page  分页信息
     * @param query 流程扩展-任务监听器查询请求参数
     * @return 流程扩展-任务监听器分页信息
     */
    Page<FlowListenerTaskEntity> findPageList(Page<FlowListenerTaskEntity> page, FlowListenerTaskQuery query);

}
