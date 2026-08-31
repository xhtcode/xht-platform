package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowItemProcessDao;
import com.xht.workflow.definition.dao.mapper.FlowItemProcessMapper;
import com.xht.workflow.definition.domain.form.FlowItemProcessForm;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Slf4j
@Repository
public class FlowItemProcessDaoImpl extends MapperRepositoryImpl<FlowItemProcessMapper, FlowItemProcessEntity> implements FlowItemProcessDao {

    @Override
    protected SFunction<FlowItemProcessEntity, ?> getFieldId() {
        return FlowItemProcessEntity::getId;
    }

    /**
     * 更新流程定义信息
     *
     * @param id   ID
     * @param form 表单参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(Long id, FlowItemProcessForm form) {
        LambdaUpdateWrapper<FlowItemProcessEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getItemDefId()), FlowItemProcessEntity::getItemDefId, form.getItemDefId())
                .set(condition(form.getRouterKey()), FlowItemProcessEntity::getRouterKey, form.getRouterKey())
                .set(condition(form.getRouterName()), FlowItemProcessEntity::getRouterName, form.getRouterName())
                .set(condition(form.getProcStartType()), FlowItemProcessEntity::getProcStartType, form.getProcStartType())
                .set(condition(form.getProcDefId()), FlowItemProcessEntity::getProcDefId, form.getProcDefId())
                .set(condition(form.getProcDefKey()), FlowItemProcessEntity::getProcDefKey, form.getProcDefKey())
                .set(condition(form.getProcDefName()), FlowItemProcessEntity::getProcDefName, form.getProcDefName())
                .set(condition(form.getProcDefVersion()), FlowItemProcessEntity::getProcDefVersion, form.getProcDefVersion())
                .set(condition(form.getProcDefPriority()), FlowItemProcessEntity::getProcDefPriority, form.getProcDefPriority())
                .set(condition(form.getDeploymentId()), FlowItemProcessEntity::getDeploymentId, form.getDeploymentId())
                .set(condition(form.getDefaultStatus()), FlowItemProcessEntity::getDefaultStatus, form.getDefaultStatus())
                .set(condition(form.getEnableStatus()), FlowItemProcessEntity::getEnableStatus, form.getEnableStatus())
                .set(condition(form.getRemark()), FlowItemProcessEntity::getRemark, form.getRemark())
                .eq(FlowItemProcessEntity::getId, id);
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 分页查询流程定义
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    @Override
    public Page<FlowItemProcessEntity> findPageList(Page<FlowItemProcessEntity> page, FlowItemProcessPageQuery query) {
        LambdaQueryWrapper<FlowItemProcessEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.isQuick()) {
            //@formatter:off
            queryWrapper.and(
                    condition(query.getKeyWord()), wrapper -> wrapper.or()
                            .like(FlowItemProcessEntity::getRouterName, query.getKeyWord())
                            .or()
                            .like(FlowItemProcessEntity::getRouterKey, query.getKeyWord())
                            .or()
                            .like(FlowItemProcessEntity::getProcDefName, query.getKeyWord())
                            .or()
                            .like(FlowItemProcessEntity::getProcDefKey, query.getKeyWord())
            );
            //@formatter:on
        } else {
            queryWrapper.eq(condition(query.getItemDefId()), FlowItemProcessEntity::getItemDefId, query.getItemDefId());
            queryWrapper.like(condition(query.getRouterName()), FlowItemProcessEntity::getRouterName, query.getRouterName());
            queryWrapper.eq(condition(query.getProcDefKey()), FlowItemProcessEntity::getProcDefKey, query.getProcDefKey());
            queryWrapper.like(condition(query.getProcDefName()), FlowItemProcessEntity::getProcDefName, query.getProcDefName());
            queryWrapper.eq(condition(query.getEnableStatus()), FlowItemProcessEntity::getEnableStatus, query.getEnableStatus());
        }
        return page(page, queryWrapper);
    }

}
