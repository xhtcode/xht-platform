package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowListenerExecutionDao;
import com.xht.workflow.definition.dao.mapper.FlowListenerExecutionMapper;
import com.xht.workflow.definition.domain.form.FlowListenerExecutionBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerExecutionQuery;
import com.xht.workflow.definition.entity.FlowListenerExecutionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程扩展-执行监听器 Dao实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowListenerExecutionDaoImpl extends MapperRepositoryImpl<FlowListenerExecutionMapper, FlowListenerExecutionEntity> implements FlowListenerExecutionDao {

    /**
     * 根据主键`id`更新流程扩展-执行监听器
     *
     * @param form 流程扩展-执行监听器表单请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(FlowListenerExecutionBasicForm form) {
        LambdaUpdateWrapper<FlowListenerExecutionEntity> updateWrapper = new LambdaUpdateWrapper<>();
        FlowListenerDaoUtils.fillLambdaUpdateWrapper(updateWrapper, form);
        update(updateWrapper);
    }

    /**
     * 分页查询流程扩展-执行监听器
     *
     * @param page  分页信息
     * @param query 流程扩展-执行监听器查询请求参数
     * @return 流程扩展-执行监听器分页信息
     */
    @Override
    public Page<FlowListenerExecutionEntity> findPageList(Page<FlowListenerExecutionEntity> page, FlowListenerExecutionQuery query) {
        LambdaQueryWrapper<FlowListenerExecutionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(condition(query.getEventType()), FlowListenerExecutionEntity::getEventType, query.getEventType());
        queryWrapper.like(condition(query.getListenerType()), FlowListenerExecutionEntity::getListenerType, query.getListenerType());
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<FlowListenerExecutionEntity, ?> getFieldId() {
        return FlowListenerExecutionEntity::getId;
    }

}
