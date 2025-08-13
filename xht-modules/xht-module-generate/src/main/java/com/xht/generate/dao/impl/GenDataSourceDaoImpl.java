package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenDataSourceDao;
import com.xht.generate.dao.mapper.GenDataSourceMapper;
import com.xht.generate.domain.entity.GenDataSourceEntity;
import com.xht.generate.domain.request.GenDataSourceFormRequest;
import com.xht.generate.domain.request.GenDataSourceQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据源管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenDataSourceDaoImpl extends MapperRepositoryImpl<GenDataSourceMapper, GenDataSourceEntity> implements GenDataSourceDao {

    /**
     * 更新菜单信息
     *
     * @param formRequest 菜单信息
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
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param queryRequest 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenDataSourceEntity> queryPageRequest(Page<GenDataSourceEntity> page, GenDataSourceQueryRequest queryRequest) {
        LambdaQueryWrapper<GenDataSourceEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.like(condition(queryRequest.getName()), GenDataSourceEntity::getName, queryRequest.getName());
        queryWrapper.eq(condition(queryRequest.getDbType()), GenDataSourceEntity::getDbType, queryRequest.getDbType());
        return page(page, queryWrapper);
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
