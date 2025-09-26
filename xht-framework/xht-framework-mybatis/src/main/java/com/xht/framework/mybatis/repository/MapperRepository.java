package com.xht.framework.mybatis.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.core.repository.CrudRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Mapper 仓库
 *
 * @author xht
 **/
public interface MapperRepository<T> extends CrudRepository<T> {

    /**
     * 默认批次提交数量
     */
    int DEFAULT_BATCH_SIZE = Constants.DEFAULT_BATCH_SIZE;

    /**
     * 保存数据（事务）
     *
     * @param entity 实体
     * @return true：成功，false：失败
     */
    boolean saveTransactional(T entity);

    /**
     * 批量保存或修改数据（事务）
     *
     * @param entityList 实体列表
     * @return true：成功，false：失败
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);
    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    boolean removeByIdTransactional(Serializable id);

    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    Optional<T> findOneOptional(SFunction<T, ?> field, Object value);

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    List<T> findList(SFunction<T, ?> field, Object value);


    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    List<T> findList(SFunction<T, ?> field, Collection<?> value);


    /**
     * 判断是否存在
     *
     * @param field 字段
     * @param value 字段值
     * @return true：存在，false：不存在
     */
    Boolean exists(SFunction<T, ?> field, Object value);

    /**
     * 判断是否存在
     *
     * @param field 字段
     * @param value 字段值
     * @return true：存在，false：不存在
     */
    Boolean existsIn(SFunction<T, ?> field, Collection<?> value);

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    long count(SFunction<T, ?> field, Object value);

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    long count(SFunction<T, ?> field, Collection<?> value);

    /**
     * 获取 LambdaUpdateWrapper
     *
     * @return LambdaUpdateWrapper
     */
    default LambdaUpdateWrapper<T> lambdaUpdateWrapper() {
        return new LambdaUpdateWrapper<>();
    }

    /**
     * 获取 LambdaQueryWrapper
     *
     * @return LambdaQueryWrapper
     */
    default LambdaQueryWrapper<T> lambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
