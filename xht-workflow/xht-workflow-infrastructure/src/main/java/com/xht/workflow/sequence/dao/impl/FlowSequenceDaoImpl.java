package com.xht.workflow.sequence.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.workflow.sequence.dao.FlowSequenceDao;
import com.xht.workflow.sequence.dao.mapper.FlowSequenceMapper;
import com.xht.workflow.sequence.domain.form.FlowSequenceForm;
import com.xht.workflow.sequence.domain.query.FlowSequencePageQuery;
import com.xht.workflow.sequence.entity.FlowSequenceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * 描述：流程序列管理
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FlowSequenceDaoImpl extends MapperRepositoryImpl<FlowSequenceMapper, FlowSequenceEntity> implements FlowSequenceDao {

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<FlowSequenceEntity, ?> getFieldId() {
        return FlowSequenceEntity::getId;
    }

    /**
     * 根据序列id修改 序列
     *
     * @param id              序列ID
     * @param oldCurrentValue 旧值
     * @param newCurrentValue 新值
     * @return 影响行数
     */
    @Override
    public boolean updateSequence(Long id, int oldCurrentValue, int newCurrentValue) {
        LambdaUpdateWrapper<FlowSequenceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(FlowSequenceEntity::getCurrentValue, newCurrentValue);
        updateWrapper.eq(FlowSequenceEntity::getCurrentValue, oldCurrentValue);
        updateWrapper.eq(FlowSequenceEntity::getId, id);
        return update(updateWrapper);
    }

    /**
     * 更新流程序列
     *
     * @param form 流程序列信息
     */
    @Override
    public void updateRequest(FlowSequenceForm form) {
        LambdaUpdateWrapper<FlowSequenceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        //@formatter:off
        updateWrapper
                .set(condition(form.getSequenceCode()), FlowSequenceEntity::getSequenceCode, form.getSequenceCode())
                .set(condition(form.getSequenceName()), FlowSequenceEntity::getSequenceName, form.getSequenceName())
                .set(condition(form.getSequenceFormat()), FlowSequenceEntity::getSequenceFormat, form.getSequenceFormat())
                .set(condition(form.getMinValue()), FlowSequenceEntity::getMinValue, form.getMinValue())
                .set(condition(form.getMaxValue()), FlowSequenceEntity::getMaxValue, form.getMaxValue())
                .set(condition(form.getCurrentValue()), FlowSequenceEntity::getCurrentValue, form.getCurrentValue())
                .set(condition(form.getSteppingValue()), FlowSequenceEntity::getSteppingValue, form.getSteppingValue())
                .set(condition(form.getIsCycle()), FlowSequenceEntity::getIsCycle, form.getIsCycle())
                .set(condition(form.getResetFlag()), FlowSequenceEntity::getResetFlag, form.getResetFlag())
                .eq(FlowSequenceEntity::getId, form.getId());
        //@formatter:on
        update(updateWrapper);
    }

    /**
     * 校验流程序列编码是否重复
     *
     * @param id           流程序列ID
     * @param sequenceCode 流程序列编码
     * @return true-存在，false-不存在
     */
    @Override
    public Boolean checkSequenceCode(Long id, String sequenceCode) {
        //@formatter:off
        LambdaQueryWrapper<FlowSequenceEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(FlowSequenceEntity::getSequenceCode, sequenceCode)
                .ne(Objects.nonNull(id), FlowSequenceEntity::getId, id);
        //@formatter:on
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 根据序列code查询序列信息
     *
     * @param sequenceCode 序列编码
     * @return 序列信息
     */
    @Override
    public FlowSequenceEntity findBySequenceCode(String sequenceCode) {
        LambdaQueryWrapper<FlowSequenceEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowSequenceEntity::getSequenceCode, sequenceCode);
        queryWrapper.last("FOR UPDATE");
        return getOne(queryWrapper);
    }

    /**
     * 分页查询流程序列列表
     *
     * @param page  分页参数
     * @param query 查询参数
     * @return 流程序列列表
     */
    @Override
    public Page<FlowSequenceEntity> findPageList(Page<FlowSequenceEntity> page, FlowSequencePageQuery query) {
        LambdaQueryWrapper<FlowSequenceEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(condition(query.getKeyWord()), wrapper -> wrapper.like(FlowSequenceEntity::getSequenceCode, query.getKeyWord()).or().like(FlowSequenceEntity::getSequenceName, query.getKeyWord()));
        queryWrapper.like(condition(query.getSequenceCode()), FlowSequenceEntity::getSequenceCode, query.getSequenceCode());
        queryWrapper.like(condition(query.getSequenceName()), FlowSequenceEntity::getSequenceName, query.getSequenceName());
        queryWrapper.eq(condition(query.getIsCycle()), FlowSequenceEntity::getIsCycle, query.getIsCycle());
        queryWrapper.eq(condition(query.getResetFlag()), FlowSequenceEntity::getResetFlag, query.getResetFlag());
        return page(page, queryWrapper);
    }


}

