package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowListenerFieldDao;
import com.xht.workflow.definition.dao.mapper.FlowListenerFieldMapper;
import com.xht.workflow.definition.domain.form.FlowListenerFieldForm;
import com.xht.workflow.definition.entity.FlowListenerFieldEntity;
import com.xht.workflow.definition.enums.ListenerFieldStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： 流程扩展-监听器（字段管理）DaoImpl
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowListenerFieldDaoImpl extends MapperRepositoryImpl<FlowListenerFieldMapper, FlowListenerFieldEntity> implements FlowListenerFieldDao {

    /**
     * 根据主键`id`更新流程扩展-监听器
     *
     * @param form 流程扩展-监听器表单请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(FlowListenerFieldForm form) {
        LambdaUpdateWrapper<FlowListenerFieldEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FlowListenerFieldEntity::getId, form.getId());
        updateWrapper.set(FlowListenerFieldEntity::getListenerId, form.getListenerId());
        updateWrapper.set(FlowListenerFieldEntity::getFieldName, form.getFieldName());
        updateWrapper.set(FlowListenerFieldEntity::getFieldType, form.getFieldType());
        updateWrapper.set(FlowListenerFieldEntity::getFieldValue, form.getFieldValue());
        updateWrapper.set(FlowListenerFieldEntity::getFieldOrder, form.getFieldOrder());
        updateWrapper.set(FlowListenerFieldEntity::getFieldStatus, form.getFieldStatus());
        update(updateWrapper);
    }

    /**
     * 根据监听器主键`listenerId`查询流程扩展-监听器
     *
     * @param listenerId              监听器主键`listenerId`
     * @param listenerFieldStatusEnum 监听器字段状态枚举
     * @return 流程扩展-监听器
     */
    @Override
    public List<FlowListenerFieldEntity> findByListenerId(Long listenerId, ListenerFieldStatusEnum listenerFieldStatusEnum) {
        LambdaUpdateWrapper<FlowListenerFieldEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(FlowListenerFieldEntity::getListenerId, listenerId);
        updateWrapper.eq(condition(listenerFieldStatusEnum), FlowListenerFieldEntity::getFieldStatus, listenerFieldStatusEnum);
        return list(updateWrapper);
    }


    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<FlowListenerFieldEntity, ?> getFieldId() {
        return FlowListenerFieldEntity::getId;
    }

}
