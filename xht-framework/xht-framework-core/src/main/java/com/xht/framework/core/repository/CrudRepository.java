package com.xht.framework.core.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 增删改查的Repository接口
 *
 * @author xht
 **/
@SuppressWarnings("all")
public interface CrudRepository<T> extends IRepository {

    /**
     * 保存单个实体
     *
     * @param entity 实体
     * @return 是否保存成功 true:成功 false:失败
     */
    boolean save(T entity);

    /**
     * 批量保存实体列表
     *
     * @param entityList 实体列表
     * @return 是否保存成功 true:成功 false:失败
     */
    boolean saveAll(Collection<T> entityList);

    /**
     * 根据ID删除实体
     *
     * @param id 实体ID
     * @return 是否删除成功 true:成功 false:失败
     */
    boolean removeById(Serializable id);


    /**
     * 根据ID列表批量删除实体
     *
     * @param idList ID列表
     * @return 是否删除成功 true:成功 false:失败
     */
    boolean removeAllById(Collection<? extends Serializable> idList);

    /**
     * 根据实体列表删除所有实体
     *
     * @param entityList 实体列表
     * @return 是否删除成功 true:成功 false:失败
     */
    boolean removeAll(Collection<T> entityList);

    /**
     * 根据ID更新实体
     *
     * @param entity 实体
     * @return 是否更新成功 true:成功 false:失败
     */
    boolean updateById(T entity);

    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return 实体
     */
    T findById(Serializable id);

    /**
     * 根据ID查找实体并返回Optional
     *
     * @param id 实体ID
     * @return 实体Optional
     */
    default Optional<T> findOptionalById(Serializable id) {
        return Optional.of(findById(id));
    }

    /**
     * 检查是否存在指定ID的实体
     *
     * @param id 实体ID
     * @return 是否存在 true:存在 false:不存在
     */
    boolean existsById(Serializable id);
    //

    /**
     * 根据ID列表查找实体列表
     *
     * @param idList 实体ID列表
     * @return 实体列表
     */
    List<T> findAllById(Collection<? extends Serializable> idList);

    /**
     * 查找所有实体
     *
     * @return 实体列表
     */
    List<T> findAll();

    /**
     * 计算实体总数
     *
     * @return 实体总数
     */
    long count();

}
