package com.xht.workflow.definition.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.definition.dao.FlowFormDao;
import com.xht.workflow.definition.dao.mapper.FlowFormMapper;
import com.xht.workflow.definition.domain.form.FlowFormForm;
import com.xht.workflow.definition.domain.query.FlowFormPageQuery;
import com.xht.workflow.definition.entity.FlowFormEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程扩展-流程表单
 *
 * @author xht
 */
@Slf4j
@Repository
public class FlowFormDaoImpl extends MapperRepositoryImpl<FlowFormMapper, FlowFormEntity> implements FlowFormDao {

    @Override
    protected SFunction<FlowFormEntity, ?> getFieldId() {
        return FlowFormEntity::getId;
    }

    /**
     * 更新表单信息
     *
     * @param id   ID
     * @param form 表单参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(Long id, FlowFormForm form) {
        LambdaUpdateWrapper<FlowFormEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getFormName()), FlowFormEntity::getFormName, form.getFormName())
                .set(condition(form.getFormContent()), FlowFormEntity::getFormContent, form.getFormContent())
                .set(condition(form.getRemark()), FlowFormEntity::getRemark, form.getRemark())
                .eq(FlowFormEntity::getId, id);
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 分页查询流程表单
     *
     * @param page  分页信息
     * @param query 查询参数
     * @return 分页数据
     */
    @Override
    public Page<FlowFormEntity> findPageList(Page<FlowFormEntity> page, FlowFormPageQuery query) {
        LambdaQueryWrapper<FlowFormEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.isQuick()) {
            //@formatter:off
            queryWrapper.and(
                    condition(query.getKeyWord()), wrapper -> wrapper.or()
                            .like(FlowFormEntity::getFormName, query.getKeyWord())
                            .or()
                            .like(FlowFormEntity::getRemark, query.getKeyWord())
            );
            //@formatter:on
        } else {
            queryWrapper.like(condition(query.getFormName()), FlowFormEntity::getFormName, query.getFormName());
        }
        return page(page, queryWrapper);
    }

}
