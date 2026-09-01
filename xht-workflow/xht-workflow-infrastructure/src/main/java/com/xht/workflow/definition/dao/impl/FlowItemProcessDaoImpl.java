package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowItemProcessDao;
import com.xht.workflow.definition.dao.mapper.FlowItemProcessMapper;
import com.xht.workflow.definition.domain.query.FlowItemProcessPageQuery;
import com.xht.workflow.definition.entity.FlowItemProcessEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @param id     ID
     * @param entity 实体对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(Long id, FlowItemProcessEntity entity) {
        LambdaUpdateWrapper<FlowItemProcessEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(entity.getItemDefId()), FlowItemProcessEntity::getItemDefId, entity.getItemDefId())
                .set(condition(entity.getRouterKey()), FlowItemProcessEntity::getRouterKey, entity.getRouterKey())
                .set(condition(entity.getRouterName()), FlowItemProcessEntity::getRouterName, entity.getRouterName())
                .set(condition(entity.getProcStartType()), FlowItemProcessEntity::getProcStartType, entity.getProcStartType())
                .set(condition(entity.getProcDefId()), FlowItemProcessEntity::getProcDefId, entity.getProcDefId())
                .set(condition(entity.getProcDefKey()), FlowItemProcessEntity::getProcDefKey, entity.getProcDefKey())
                .set(condition(entity.getProcDefName()), FlowItemProcessEntity::getProcDefName, entity.getProcDefName())
                .set(condition(entity.getProcDefVersion()), FlowItemProcessEntity::getProcDefVersion, entity.getProcDefVersion())
                .set(condition(entity.getProcDefPriority()), FlowItemProcessEntity::getProcDefPriority, entity.getProcDefPriority())
                .set(condition(entity.getDeploymentId()), FlowItemProcessEntity::getDeploymentId, entity.getDeploymentId())
                .set(condition(entity.getDefaultStatus()), FlowItemProcessEntity::getDefaultStatus, entity.getDefaultStatus())
                .set(condition(entity.getEnableStatus()), FlowItemProcessEntity::getEnableStatus, entity.getEnableStatus())
                .set(condition(entity.getRemark()), FlowItemProcessEntity::getRemark, entity.getRemark())
                .eq(FlowItemProcessEntity::getId, id);
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 查询流程定义列表
     *
     * @param query 查询参数
     * @return 流程定义列表
     */
    @Override
    public List<FlowItemProcessEntity> findList(FlowItemProcessPageQuery query) {
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
        return list(queryWrapper);
    }

}
