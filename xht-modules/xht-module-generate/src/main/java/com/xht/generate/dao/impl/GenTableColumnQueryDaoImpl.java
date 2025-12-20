package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableColumnQueryDao;
import com.xht.generate.dao.mapper.GenTableColumnQueryMapper;
import com.xht.generate.domain.entity.GenTableColumnQueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableColumnQueryDaoImpl extends MapperRepositoryImpl<GenTableColumnQueryMapper, GenTableColumnQueryEntity> implements GenTableColumnQueryDao {

    /**
     * 根据表ID查询字段信息
     *
     * @param tableId 表ID
     * @return 字段信息
     */
    @Override
    public List<GenTableColumnQueryEntity> findByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnQueryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(GenTableColumnQueryEntity::getTableId, tableId)
                .orderByAsc(GenTableColumnQueryEntity::getSortOrder);
        return list(queryWrapper);
    }

    /**
     * 根据表ID查询字段信息
     *
     * @param tableIds 表ID
     * @return 字段信息
     */
    @Override
    public List<GenTableColumnQueryEntity> findByTableId(List<Long> tableIds) {
        LambdaQueryWrapper<GenTableColumnQueryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(GenTableColumnQueryEntity::getTableId, tableIds)
                .orderByAsc(GenTableColumnQueryEntity::getSortOrder);
        return list(queryWrapper);
    }

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    @Override
    public void deleteByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnQueryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GenTableColumnQueryEntity::getTableId, tableId);
        remove(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTableColumnQueryEntity, ?> getFieldId() {
        return GenTableColumnQueryEntity::getId;
    }

}
