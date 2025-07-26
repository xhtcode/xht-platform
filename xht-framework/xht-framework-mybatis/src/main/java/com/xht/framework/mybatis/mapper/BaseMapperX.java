package com.xht.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 描述 ：BaseMapper 扩展
 *
 * @author xht
 * @see BaseMapper
 **/
public interface BaseMapperX<T> extends BaseMapper<T> {


    /**
     * 查询一个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default Optional<T> selectOne(SFunction<T, ?> field, Object value) {
        return Optional.ofNullable(selectOne(new LambdaQueryWrapper<T>().eq(field, value)));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询多个
     *
     * @param field 字段name
     * @param value 字段value
     */
    default List<T> selectListIn(SFunction<T, ?> field, Collection<?> value) {
        return selectList(new LambdaQueryWrapper<T>().in(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询数量
     *
     * @param field 字段
     * @param value 字段值
     * @return 数量
     */
    default long selectCountIn(SFunction<T, ?> field, Collection<?> value) {
        return selectCount(new LambdaQueryWrapper<T>().in(field, value));
    }
}
