package com.xht.framework.mybatis.repository.impl;

import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.framework.mybatis.repository.DataScopeMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 描述：数据权限的Repository
 *
 * @author xht
 **/
@Component
public class DataScopeMapperRepositoryImpl<M extends BaseMapperX<T>, T> implements DataScopeMapperRepository<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public boolean save(T entity) {
        return false;
    }

    @Override
    public boolean saveAll(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeAllById(Collection<? extends Serializable> idList) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean updateById(T entity) {
        return false;
    }

    @Override
    public T findById(Serializable id) {
        return null;
    }

    @Override
    public boolean existsById(Serializable id) {
        return false;
    }

    @Override
    public boolean existsByIds(Collection<? extends Serializable> ids) {
        return false;
    }

    @Override
    public List<T> findAllById(Collection<? extends Serializable> idList) {
        return List.of();
    }

    @Override
    public List<T> findAll() {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    protected String getSqlSegment() {
        return null;
    }

}
