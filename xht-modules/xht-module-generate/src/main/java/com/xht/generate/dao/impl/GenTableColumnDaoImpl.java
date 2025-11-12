package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableColumnDao;
import com.xht.generate.dao.mapper.GenTableColumnMapper;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.form.GenColumnInfoForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableColumnDaoImpl extends MapperRepositoryImpl<GenTableColumnMapper, GenTableColumnEntity> implements GenTableColumnDao {

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    @Override
    public void deleteByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(GenTableColumnEntity::getTableId, tableId);
        remove(queryWrapper);
    }

    /**
     * 修改字段信息
     *
     * @param column 字段信息
     */
    @Override
    public void updateFormRequest(GenColumnInfoForm column) {
        LambdaUpdateWrapper<GenTableColumnEntity> updateWrapper = lambdaUpdateWrapper();
        // @formatter:off
        updateWrapper
                .set(condition(column.getDbRequired()), GenTableColumnEntity::getDbRequired, column.getDbRequired())
                .set(condition(column.getDbComment()), GenTableColumnEntity::getDbComment, column.getDbComment())
                .set(condition(column.getDbLength()), GenTableColumnEntity::getDbLength, column.getDbLength())
                .set(condition(column.getCodeName()), GenTableColumnEntity::getCodeName, column.getCodeName())
                .set(condition(column.getCodeComment()), GenTableColumnEntity::getCodeComment, column.getCodeComment())
                .set(condition(column.getFromInsert()), GenTableColumnEntity::getFromInsert, column.getFromInsert())
                .set(condition(column.getFromUpdate()), GenTableColumnEntity::getFromUpdate, column.getFromUpdate())
                .set(condition(column.getFromLength()), GenTableColumnEntity::getFromLength, column.getFromLength())
                .set(condition(column.getFromFill()), GenTableColumnEntity::getFromFill, column.getFromFill())
                .set(condition(column.getFromComponent()), GenTableColumnEntity::getFromComponent, column.getFromComponent())
                .set(condition(column.getListShow()), GenTableColumnEntity::getListShow, column.getListShow())
                .set(condition(column.getListComment()), GenTableColumnEntity::getListComment, column.getListComment())
                .set(condition(column.getListDisabled()), GenTableColumnEntity::getListDisabled, column.getListDisabled())
                .set(condition(column.getListHidden()), GenTableColumnEntity::getListHidden, column.getListHidden())
                .set(condition(column.getCodeJava()), GenTableColumnEntity::getCodeJava, column.getCodeJava())
                .set(condition(column.getCodeJavaPackage()), GenTableColumnEntity::getCodeJavaPackage, column.getCodeJavaPackage())
                .set(condition(column.getCodeTs()), GenTableColumnEntity::getCodeTs, column.getCodeTs())
                .set(condition(column.getSortOrder()), GenTableColumnEntity::getSortOrder, column.getSortOrder())
        ;
        // @formatter:on
        updateWrapper.eq(GenTableColumnEntity::getId, column.getId());
        update(updateWrapper);
    }

    /**
     * 根据表ID查询字段信息
     *
     * @param tableId 表ID
     * @return 字段信息
     */
    @Override
    public List<GenTableColumnEntity> findByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(GenTableColumnEntity::getTableId, tableId)
                .orderByAsc(GenTableColumnEntity::getSortOrder);
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTableColumnEntity, ?> getFieldId() {
        return GenTableColumnEntity::getId;
    }


}
