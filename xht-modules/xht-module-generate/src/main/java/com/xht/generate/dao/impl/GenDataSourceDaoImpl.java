package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.dao.mapper.GenDataSourceMapper;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.form.GenDataSourceForm;
import com.xht.generate.domain.query.GenDataSourceQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据源管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenDataSourceDaoImpl extends MapperRepositoryImpl<GenDataSourceMapper, GenDataSourceEntity> implements GenDataSourceDao {

    /**
     * 更新数据源信息
     *
     * @param form 数据源信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(GenDataSourceForm form) {
        LambdaUpdateWrapper<GenDataSourceEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(form.getName()), GenDataSourceEntity::getName, form.getName());
        updateWrapper.set(condition(form.getDbType()), GenDataSourceEntity::getDbType, form.getDbType());
        updateWrapper.set(condition(form.getUrl()), GenDataSourceEntity::getUrl, form.getUrl());
        updateWrapper.eq(GenDataSourceEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 分页查询数据源
     *
     * @param query 数据源查询请求参数
     * @return 数据源分页信息
     */
    @Override
    public List<GenDataSourceEntity> findList(GenDataSourceQuery query) {
        LambdaQueryWrapper<GenDataSourceEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.like(condition(query.getName()), GenDataSourceEntity::getName, query.getName());
        queryWrapper.eq(condition(query.getDbType()), GenDataSourceEntity::getDbType, query.getDbType());
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenDataSourceEntity, ?> getFieldId() {
        return GenDataSourceEntity::getId;
    }
}
