package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.dao.mapper.GenDataSourceMapper;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.form.GenDataSourceFormRequest;
import com.xht.generate.domain.query.GenDataSourceQueryRequest;
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
     * @param formRequest 数据源信息
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(GenDataSourceFormRequest formRequest) {
        LambdaUpdateWrapper<GenDataSourceEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(formRequest.getName()), GenDataSourceEntity::getName, formRequest.getName());
        updateWrapper.set(condition(formRequest.getDbType()), GenDataSourceEntity::getDbType, formRequest.getDbType());
        updateWrapper.set(condition(formRequest.getUrl()), GenDataSourceEntity::getUrl, formRequest.getUrl());
        updateWrapper.eq(GenDataSourceEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 分页查询数据源
     *
     * @param queryRequest 数据源查询请求参数
     * @return 数据源分页信息
     */
    @Override
    public List<GenDataSourceEntity> selectList(GenDataSourceQueryRequest queryRequest) {
        LambdaQueryWrapper<GenDataSourceEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.like(condition(queryRequest.getName()), GenDataSourceEntity::getName, queryRequest.getName());
        queryWrapper.eq(condition(queryRequest.getDbType()), GenDataSourceEntity::getDbType, queryRequest.getDbType());
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
