package com.xht.modules.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.generate.dao.GenTableDao;
import com.xht.modules.generate.dao.mapper.GenTableMapper;
import com.xht.modules.generate.entity.GenTableEntity;
import com.xht.modules.generate.domain.form.GenTableInfoForm;
import com.xht.modules.generate.domain.query.GenTableInfoQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 表信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableDaoImpl extends MapperRepositoryImpl<GenTableMapper, GenTableEntity> implements GenTableDao {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    @Override
    public void updateFormRequest(GenTableInfoForm form) {
        LambdaUpdateWrapper<GenTableEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(condition(form.getGroupId()), GenTableEntity::getGroupId, form.getGroupId());
        updateWrapper.set(condition(form.getDataSourceId()), GenTableEntity::getDataSourceId, form.getDataSourceId());
        updateWrapper.set(condition(form.getDataBaseType()), GenTableEntity::getDataBaseType, form.getDataBaseType());
        updateWrapper.set(condition(form.getEngineName()), GenTableEntity::getEngineName, form.getEngineName());
        updateWrapper.set(condition(form.getTableName()), GenTableEntity::getTableName, form.getTableName());
        updateWrapper.set(condition(form.getTableComment()), GenTableEntity::getTableComment, form.getTableComment());
        updateWrapper.set(condition(form.getModuleName()), GenTableEntity::getModuleName, form.getModuleName());
        updateWrapper.set(condition(form.getServiceName()), GenTableEntity::getServiceName, form.getServiceName());
        updateWrapper.set(condition(form.getCodeName()), GenTableEntity::getCodeName, form.getCodeName());
        updateWrapper.set(condition(form.getCodeComment()), GenTableEntity::getCodeComment, form.getCodeComment());
        updateWrapper.set(condition(form.getBackEndAuthor()), GenTableEntity::getBackEndAuthor, form.getBackEndAuthor());
        updateWrapper.set(condition(form.getFrontEndAuthor()), GenTableEntity::getFrontEndAuthor, form.getFrontEndAuthor());
        updateWrapper.set(condition(form.getUrlPrefix()), GenTableEntity::getUrlPrefix, form.getUrlPrefix());
        updateWrapper.set(condition(form.getPermissionPrefix()), GenTableEntity::getPermissionPrefix, form.getPermissionPrefix());
        updateWrapper.set(condition(form.getParentMenuId()), GenTableEntity::getParentMenuId, form.getParentMenuId());
        updateWrapper.set(condition(form.getPageStyle()), GenTableEntity::getPageStyle, form.getPageStyle());
        updateWrapper.set(condition(form.getPageStyleWidth()), GenTableEntity::getPageStyleWidth, form.getPageStyleWidth());
        updateWrapper.set(condition(form.getFromNumber()), GenTableEntity::getFromNumber, form.getFromNumber());
        updateWrapper.eq(GenTableEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenTableEntity> findPageList(Page<GenTableEntity> page, GenTableInfoQuery query) {
        LambdaQueryWrapper<GenTableEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(condition(query.getGroupId()), GenTableEntity::getGroupId, query.getGroupId());
        queryWrapper.eq(condition(query.getDataSourceId()), GenTableEntity::getDataSourceId, query.getDataSourceId());
        queryWrapper.like(condition(query.getTableName()), GenTableEntity::getTableName, query.getTableName());
        return page(page, queryWrapper);
    }

    /**
     * 根据数据源id查询表名
     *
     * @param dataSourceId 数据源id
     * @return 表名
     */
    @Override
    public List<String> findTableNameByDbId(Long dataSourceId) {
        LambdaQueryWrapper<GenTableEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(GenTableEntity::getTableName);
        queryWrapper.eq(GenTableEntity::getDataSourceId, dataSourceId);
        List<GenTableEntity> list = list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(GenTableEntity::getTableName).toList();
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTableEntity, ?> getFieldId() {
        return GenTableEntity::getId;
    }
}
