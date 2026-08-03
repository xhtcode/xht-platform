package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.IFlowDefinitionDao;
import com.xht.workflow.definition.dao.mapper.FlowDefinitionMapper;
import com.xht.workflow.definition.domain.form.FlowDefinitionForm;
import com.xht.workflow.definition.domain.query.FlowDefinitionPageQuery;
import com.xht.workflow.definition.entity.FlowDefinitionEntity;
import com.xht.workflow.definition.enums.DefinitionStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.xht.workflow.common.constant.CategoryConstant.DEFAULT_CATEGORY_ID;

/**
 * 流程定义
 *
 * @author xht
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowDefinitionDaoImpl extends MapperRepositoryImpl<FlowDefinitionMapper, FlowDefinitionEntity> implements IFlowDefinitionDao {

    @Override
    protected SFunction<FlowDefinitionEntity, ?> getFieldId() {
        return FlowDefinitionEntity::getId;
    }


    /**
     * 更新
     *
     * @param form          表单参数
     * @param categoryLevel 定义级别
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRequest(FlowDefinitionForm form, Integer categoryLevel) {
        LambdaUpdateWrapper<FlowDefinitionEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getParentId()), FlowDefinitionEntity::getParentId, form.getParentId())
                .set(FlowDefinitionEntity::getDefinitionLevel, categoryLevel)
                .set(condition(form.getDefinitionCode()), FlowDefinitionEntity::getDefinitionCode, form.getDefinitionCode())
                .set(condition(form.getDefinitionName()), FlowDefinitionEntity::getDefinitionName, form.getDefinitionName())
                .set(condition(form.getDefinitionType()), FlowDefinitionEntity::getDefinitionType, form.getDefinitionType())
                .set(condition(form.getDefinitionDesc()), FlowDefinitionEntity::getDefinitionDesc, form.getDefinitionDesc())
                .set(condition(form.getDefinitionStatus()), FlowDefinitionEntity::getDefinitionStatus, form.getDefinitionStatus())
                .set(condition(form.getDefinitionSort()), FlowDefinitionEntity::getDefinitionSort, form.getDefinitionSort())
                .eq(FlowDefinitionEntity::getId, form.getId());
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 校验定义编号
     *
     * @param id           ID
     * @param categoryCode 定义编号
     * @return 校验结果
     */
    @Override
    public Boolean checkCategoryCode(Long id, String categoryCode) {
        //@formatter:off
        LambdaQueryWrapper<FlowDefinitionEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(FlowDefinitionEntity::getDefinitionCode, categoryCode)
                .ne(Objects.nonNull(id), FlowDefinitionEntity::getId, id);
        //@formatter:on
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 获取流程定义列表
     *
     * @param query 流程定义查询参数
     * @return 流程定义列表
     */
    @Override
    public List<FlowDefinitionEntity> findList(FlowDefinitionPageQuery query) {
        LambdaQueryWrapper<FlowDefinitionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(condition(query.getDefinitionCode()), FlowDefinitionEntity::getDefinitionCode, query.getDefinitionCode());
        queryWrapper.like(condition(query.getDefinitionName()), FlowDefinitionEntity::getDefinitionName, query.getDefinitionName());
        queryWrapper.eq(FlowDefinitionEntity::getParentId, Objects.requireNonNullElse(query.getParentId(), DEFAULT_CATEGORY_ID));
        queryWrapper.eq(condition(query.getDefinitionStatus()), FlowDefinitionEntity::getDefinitionStatus, query.getDefinitionStatus());
        queryWrapper.eq(condition(query.getDefinitionType()), FlowDefinitionEntity::getDefinitionType, query.getDefinitionType());
        return list(queryWrapper);
    }

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId           父级ID
     * @param definitionStatusEnum 定义状态
     * @return 流程类别列表
     */
    @Override
    public List<FlowDefinitionEntity> findByParentId(Long parentId, DefinitionStatusEnum definitionStatusEnum) {
        LambdaQueryWrapper<FlowDefinitionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowDefinitionEntity::getParentId, parentId);
        queryWrapper.eq(FlowDefinitionEntity::getDefinitionStatus, definitionStatusEnum);
        return list(queryWrapper);
    }

}




