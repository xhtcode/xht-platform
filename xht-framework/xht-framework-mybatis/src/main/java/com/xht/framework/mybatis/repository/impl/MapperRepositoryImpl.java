package com.xht.framework.mybatis.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.mapper.BaseMapperX;
import com.xht.framework.mybatis.repository.MapperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 自定义 Mapper 接口实现类
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("unused")
public abstract class MapperRepositoryImpl<M extends BaseMapperX<T>, T> extends CrudRepository<M, T> implements MapperRepository<T> {

    protected static final int DEFAULT_BATCH_SIZE = 100;

    protected int getDefaultBatchSize() {
        return DEFAULT_BATCH_SIZE;
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    protected SFunction<T, ?> getFieldId() {
        throw new UnsupportedOperationException("getFieldId() method not implemented");
    }


    /**
     * 保存单个实体
     *
     * @param entity 实体
     * @return 是否保存成功 true:成功 false:失败
     */
    @Override
    public final boolean save(T entity) {
        return SqlHelper.retBool(baseMapper.insert(entity));
    }

    /**
     * 保存数据（事务）
     *
     * @param entity 实体
     * @return true：成功，false：失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTransactional(T entity) {
        return SqlHelper.retBool(baseMapper.insert(entity));
    }

    /**
     * 批量保存实体列表
     *
     * @param entityList 实体列表
     * @return 是否保存成功 true:成功 false:失败
     */
    @Override
    public final boolean saveAll(Collection<T> entityList) {
        return super.saveBatch(entityList, getDefaultBatchSize());
    }


    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }


    /**
     * 根据ID删除实体
     *
     * @param id 实体ID
     * @return 是否删除成功 true:成功 false:失败
     */
    @Override
    public final boolean deleteById(Serializable id) {
        return removeById(id);
    }

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIdTransactional(Serializable id) {
        return removeById(id);
    }

    /**
     * 根据ID列表批量删除实体
     *
     * @param idList ID列表
     * @return 是否删除成功 true:成功 false:失败
     */
    @Override
    public final boolean deleteAllById(Collection<? extends Serializable> idList) {
        return removeByIds(idList);
    }

    /**
     * 根据实体列表删除所有实体
     *
     * @param entityList 实体列表
     * @return 是否删除成功 true:成功 false:失败
     */
    @Override
    public final boolean deleteAll(Collection<T> entityList) {
        return removeByIds(entityList);
    }

    /**
     * 根据ID更新实体
     *
     * @param entity 实体
     * @return 是否更新成功 true:成功 false:失败
     */
    @Override
    public final boolean updateById(T entity) {
        return SqlHelper.retBool(baseMapper.updateById(entity));
    }

    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return 实体
     */
    @Override
    public final T findById(Serializable id) {
        return getById(id);
    }

    /**
     * 检查是否存在指定ID的实体
     *
     * @param id 实体ID
     * @return 是否存在 true:存在 false:不存在
     */
    @Override
    public final boolean existsById(Serializable id) {
        return SqlHelper.retBool(baseMapper.selectCount(getFieldId(), id));
    }

    /**
     * 根据ID列表查找实体列表
     *
     * @param idList 实体ID列表
     * @return 实体列表
     */
    @Override
    public final List<T> findAllById(Collection<? extends Serializable> idList) {
        return baseMapper.selectList(getFieldId(), idList);
    }

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    @Override
    public final List<T> findAll() {
        return list(Wrappers.emptyWrapper());
    }

    /**
     * 计算实体总数
     *
     * @return 实体总数
     */
    @Override
    public final long count() {
        return count(Wrappers.emptyWrapper());
    }


    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    @Override
    public final Optional<T> findOneOptional(SFunction<T, ?> field, Object value) {
        return getOneOpt(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    @Override
    public final List<T> findList(SFunction<T, ?> field, Object value) {
        return list(new LambdaQueryWrapper<T>().eq(field, value));
    }


    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    @Override
    public final List<T> findListIn(SFunction<T, ?> field, Collection<?> value) {
        return list(new LambdaQueryWrapper<T>().in(field, value));
    }


    /**
     * 判断是否存在
     *
     * @param field 字段
     * @param value 字段值
     * @return true：存在，false：不存在
     */
    @Override
    public final Boolean exists(SFunction<T, ?> field, Object value) {
        return SqlHelper.retBool(count(field, value));
    }

    /**
     * 判断是否存在
     *
     * @param field 字段
     * @param value 字段值
     * @return true：存在，false：不存在
     */
    @Override
    public final Boolean existsIn(SFunction<T, ?> field, Collection<?> value) {
        return count(field, value) == value.size();
    }
    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    @Override
    public final long count(SFunction<T, ?> field, Object value) {
        return count(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    @Override
    public final long count(SFunction<T, ?> field, Collection<?> value) {
        return count(new LambdaQueryWrapper<T>().in(field, value));
    }


}
