package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.IFlowItemDefDao;
import com.xht.workflow.definition.dao.mapper.FlowItemDefMapper;
import com.xht.workflow.definition.domain.form.FlowItemDefForm;
import com.xht.workflow.definition.domain.query.FlowItemDefPageQuery;
import com.xht.workflow.definition.entity.FlowItemDefEntity;
import com.xht.workflow.definition.enums.FlowDefinitionStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.xht.workflow.common.constant.CategoryConstant.DEFAULT_CATEGORY_ID;

/**
 * 流程扩展-流程定义
 *
 * @author xht
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowItemDefDaoImpl extends MapperRepositoryImpl<FlowItemDefMapper, FlowItemDefEntity> implements IFlowItemDefDao {

    @Override
    protected SFunction<FlowItemDefEntity, ?> getFieldId() {
        return FlowItemDefEntity::getId;
    }

    /**
     * 更新
     *
     * @param id            ID
     * @param form          表单参数
     * @param categoryLevel 定义级别
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRequest(Long id, FlowItemDefForm form, Integer categoryLevel) {
        LambdaUpdateWrapper<FlowItemDefEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getParentId()), FlowItemDefEntity::getParentId, form.getParentId())
                .set(FlowItemDefEntity::getItemLevel, categoryLevel)
                .set(condition(form.getItemCode()), FlowItemDefEntity::getItemCode, form.getItemCode())
                .set(condition(form.getItemName()), FlowItemDefEntity::getItemName, form.getItemName())
                .set(condition(form.getItemType()), FlowItemDefEntity::getItemType, form.getItemType())
                .set(condition(form.getItemDesc()), FlowItemDefEntity::getItemDesc, form.getItemDesc())
                .set(condition(form.getItemStatus()), FlowItemDefEntity::getItemStatus, form.getItemStatus())
                .set(condition(form.getItemSort()), FlowItemDefEntity::getItemSort, form.getItemSort())
                .eq(FlowItemDefEntity::getId, id);
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
        LambdaQueryWrapper<FlowItemDefEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(FlowItemDefEntity::getItemCode, categoryCode)
                .ne(Objects.nonNull(id), FlowItemDefEntity::getId, id);
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
    public List<FlowItemDefEntity> findList(FlowItemDefPageQuery query) {
        LambdaQueryWrapper<FlowItemDefEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(condition(query.getItemCode()), FlowItemDefEntity::getItemCode, query.getItemCode());
        queryWrapper.like(condition(query.getItemName()), FlowItemDefEntity::getItemName, query.getItemName());
        queryWrapper.eq(FlowItemDefEntity::getParentId, Objects.requireNonNullElse(query.getParentId(), DEFAULT_CATEGORY_ID));
        queryWrapper.eq(condition(query.getItemStatus()), FlowItemDefEntity::getItemStatus, query.getItemStatus());
        queryWrapper.eq(condition(query.getItemType()), FlowItemDefEntity::getItemType, query.getItemType());
        return list(queryWrapper);
    }

    /**
     * 根据父级ID查询流程类别
     *
     * @param parentId           父级ID
     * @param flowDefinitionStatusEnum 定义状态
     * @return 流程类别列表
     */
    @Override
    public List<FlowItemDefEntity> findByParentId(Long parentId, FlowDefinitionStatusEnum flowDefinitionStatusEnum) {
        LambdaQueryWrapper<FlowItemDefEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowItemDefEntity::getParentId, parentId);
        queryWrapper.eq(FlowItemDefEntity::getItemStatus, flowDefinitionStatusEnum);
        return list(queryWrapper);
    }

}




