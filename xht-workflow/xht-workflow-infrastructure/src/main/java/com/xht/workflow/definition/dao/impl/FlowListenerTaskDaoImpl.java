package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowListenerTaskDao;
import com.xht.workflow.definition.dao.mapper.FlowListenerTaskMapper;
import com.xht.workflow.definition.domain.form.FlowListenerTaskBasicForm;
import com.xht.workflow.definition.domain.query.FlowListenerTaskQuery;
import com.xht.workflow.definition.entity.FlowListenerTaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程扩展-任务监听器 Dao实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowListenerTaskDaoImpl extends MapperRepositoryImpl<FlowListenerTaskMapper, FlowListenerTaskEntity> implements FlowListenerTaskDao {

    /**
     * 根据主键`id`更新流程扩展-任务监听器
     *
     * @param form 流程扩展-任务监听器表单请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(FlowListenerTaskBasicForm form) {
        LambdaUpdateWrapper<FlowListenerTaskEntity> updateWrapper = new LambdaUpdateWrapper<>();
        FlowListenerDaoUtils.fillLambdaUpdateWrapper(updateWrapper, form);
        update(updateWrapper);
    }

    /**
     * 分页查询流程扩展-任务监听器
     *
     * @param page  分页信息
     * @param query 流程扩展-任务监听器查询请求参数
     * @return 流程扩展-任务监听器分页信息
     */
    @Override
    public Page<FlowListenerTaskEntity> findPageList(Page<FlowListenerTaskEntity> page, FlowListenerTaskQuery query) {
        LambdaQueryWrapper<FlowListenerTaskEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(condition(query.getEventType()), FlowListenerTaskEntity::getEventType, query.getEventType());
        queryWrapper.like(condition(query.getListenerType()), FlowListenerTaskEntity::getListenerType, query.getListenerType());
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<FlowListenerTaskEntity, ?> getFieldId() {
        return FlowListenerTaskEntity::getId;
    }

}
