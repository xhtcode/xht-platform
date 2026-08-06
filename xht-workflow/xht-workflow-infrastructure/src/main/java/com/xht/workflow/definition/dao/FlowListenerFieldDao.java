package com.xht.workflow.definition.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.workflow.definition.domain.form.FlowListenerFieldForm;
import com.xht.workflow.definition.entity.FlowListenerFieldEntity;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;

import java.util.List;

/**
 * 描述： 流程扩展-监听器（字段管理）Dao
 *
 * @author xht
 **/
public interface FlowListenerFieldDao extends MapperRepository<FlowListenerFieldEntity> {

    /**
     * 根据主键`id`更新流程扩展-监听器
     *
     * @param form 流程扩展-监听器表单请求参数
     */
    void updateFormRequest(FlowListenerFieldForm form);

    /**
     * 根据监听器主键`listenerId`查询流程扩展-监听器
     *
     * @param listenerId 监听器主键`listenerId`
     * @param listenerFieldStatusEnum 监听器字段状态枚举
     * @return 流程扩展-监听器
     */
    List<FlowListenerFieldEntity> findByListenerId(Long listenerId, ListenerFieldStatusEnum listenerFieldStatusEnum);

}
