package com.xht.framework.mybatis.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 基础管理器
 *
 * @author xht
 **/
public abstract class BasicDao<M extends BaseMapperX<T>, T>
        extends CrudRepository<M, T>
        implements IService<T> {

    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public Optional<T> getOneOpt(SFunction<T, ?> field, Object value) {
        return getOneOpt(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectList(SFunction<T, ?> field, Object value) {
        return list(new LambdaQueryWrapper<T>().eq(field, value));
    }


    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    public List<T> selectListIn(SFunction<T, ?> field, Collection<?> value) {
        return list(new LambdaQueryWrapper<T>().in(field, value));
    }


    /**
     * 判断是否存在
     *
     * @param field 字段
     * @param value 字段值
     * @return true：存在，false：不存在
     */
    public Boolean exists(SFunction<T, ?> field, Object value) {
        return dataExists(selectCount(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCount(SFunction<T, ?> field, Object value) {
        return count(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    public long selectCountIn(SFunction<T, ?> field, Collection<?> value) {
        return count(new LambdaQueryWrapper<T>().in(field, value));
    }

    /**
     * 保存数据（事务）
     *
     * @param entity 实体
     * @return true：成功，false：失败
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTransactional(T entity) {
        return save(entity);
    }

    /**
     * 数据是否存在
     *
     * @param count 数据数量
     * @return true：存在，false：不存在
     */
    protected Boolean dataExists(Long count) {
        return null != count && count > 0L;
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIdTransactional(Serializable id) {
        return removeById(id);
    }
}
