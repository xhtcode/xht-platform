package com.xht.framework.mybatis.repository;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.core.repository.CrudRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Mapper 仓库接口，提供基于 MyBatis-Plus 的通用数据访问能力。
 * 扩展自 CrudRepository，提供更丰富的数据库操作方法。
 *
 * @param <T> 实体类型
 * @author xht
 **/
@SuppressWarnings("unused")
public interface MapperRepository<T> extends CrudRepository<T> {

    /**
     * 保存数据，并在事务中执行操作。
     *
     * @param entity 待保存的实体对象
     * @return true：保存成功，false：保存失败
     */
    boolean saveTransactional(T entity);

    /**
     * 批量保存或更新实体列表，并在事务中执行操作。
     *
     * @param entityList 实体对象集合
     * @return true：操作成功，false：操作失败
     */
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 根据主键 ID 删除数据，并在事务中执行操作。
     *
     * @param id 主键 ID
     */
    void removeByIdTransactional(Serializable id);

    /**
     * 根据指定字段和值查询单个实体对象，返回 Optional 封装结果。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值
     * @return Optional 包装的实体对象，若不存在则返回空 Optional
     */
    Optional<T> findOneOptional(SFunction<T, ?> field, Object value);

    /**
     * 根据指定字段和值查询多个实体对象。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值
     * @return 实体对象列表
     */
    List<T> findList(SFunction<T, ?> field, Object value);

    /**
     * 根据指定字段和值集合查询多个实体对象。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值集合
     * @return 实体对象列表
     */
    List<T> findList(SFunction<T, ?> field, Collection<?> value);

    /**
     * 判断是否存在满足指定字段等于给定值的记录。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值
     * @return true：存在匹配记录，false：不存在匹配记录
     */
    Boolean exists(SFunction<T, ?> field, Object value);

    /**
     * 判断是否存在满足指定字段值在给定集合中的记录。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值集合
     * @return true：存在匹配记录，false：不存在匹配记录
     */
    Boolean existsIn(SFunction<T, ?> field, Collection<?> value);

    /**
     * 统计满足指定字段等于给定值的记录数量。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值
     * @return 记录数量
     */
    long count(SFunction<T, ?> field, Object value);

    /**
     * 统计满足指定字段值在给定集合中的记录数量。
     *
     * @param field 查询字段（使用 Lambda 表达式）
     * @param value 查询值集合
     * @return 记录数量
     */
    long count(SFunction<T, ?> field, Collection<?> value);

}
