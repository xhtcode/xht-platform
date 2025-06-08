package com.xht.framework.mybatis.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import java.io.Serializable;

/**
 * 描述 ：Wrapper构建
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public interface AbstractWrapperBuilder<T> extends Serializable {

    /**
     * 构建 {@link QueryWrapper }
     *
     * @return {@link QueryWrapper }
     */
    default QueryWrapper<T> buildQuery() {
        return new QueryWrapper<>();
    }

    /**
     * 构建 {@link LambdaQueryWrapper }
     *
     * @return {@link LambdaQueryWrapper }
     */
    default LambdaQueryWrapper<T> buildLambdaQuery() {
        return new LambdaQueryWrapper<>();
    }

    /**
     * 构建 {@link LambdaQueryWrapper }
     *
     * @param entity 实体类
     * @return {@link LambdaQueryWrapper }
     */
    LambdaQueryWrapper<T> buildLambdaQuery(T entity);


    /**
     * 构建 {@link LambdaUpdateWrapper }
     *
     * @param entity 实体类
     * @return {@link LambdaUpdateWrapper }
     */
    LambdaUpdateWrapper<T> buildLambdaUpdate(T entity);

}
