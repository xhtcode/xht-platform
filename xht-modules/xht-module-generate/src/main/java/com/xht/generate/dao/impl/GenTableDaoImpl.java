package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableDao;
import com.xht.generate.dao.mapper.GenTableMapper;
import com.xht.generate.domain.entity.GenTableEntity;
import com.xht.generate.domain.form.GenTableInfoFormRequest;
import com.xht.generate.domain.query.GenTableInfoQueryRequest;
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
     * @param formRequest 菜单信息
     * @return 是否成功
     */
    @Override
    public Boolean updateFormRequest(GenTableInfoFormRequest formRequest) {
        LambdaUpdateWrapper<GenTableEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(formRequest.getGroupId()), GenTableEntity::getGroupId, formRequest.getGroupId());
        updateWrapper.set(condition(formRequest.getDataSourceId()), GenTableEntity::getDataSourceId, formRequest.getDataSourceId());
        updateWrapper.set(condition(formRequest.getTableComment()), GenTableEntity::getTableComment, formRequest.getTableComment());
        updateWrapper.set(condition(formRequest.getCodeName()), GenTableEntity::getCodeName, formRequest.getCodeName());
        updateWrapper.set(condition(formRequest.getCodeComment()), GenTableEntity::getCodeComment, formRequest.getCodeComment());
        updateWrapper.eq(GenTableEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenTableEntity> queryPageRequest(Page<GenTableEntity> page, GenTableInfoQueryRequest queryRequest) {
        LambdaQueryWrapper<GenTableEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(condition(queryRequest.getGroupId()), GenTableEntity::getGroupId, queryRequest.getGroupId());
        queryWrapper.eq(condition(queryRequest.getDataSourceId()), GenTableEntity::getDataSourceId, queryRequest.getDataSourceId());
        queryWrapper.like(condition(queryRequest.getTableName()), GenTableEntity::getTableName, queryRequest.getTableName());
        queryWrapper.like(condition(queryRequest.getTableComment()), GenTableEntity::getTableComment, queryRequest.getTableComment());
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
        LambdaQueryWrapper<GenTableEntity> queryWrapper = lambdaQueryWrapper();
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
