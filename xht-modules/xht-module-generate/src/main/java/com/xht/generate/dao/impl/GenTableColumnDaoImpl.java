package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTableColumnDao;
import com.xht.generate.dao.mapper.GenTableColumnMapper;
import com.xht.generate.domain.entity.GenTableColumnEntity;
import com.xht.generate.domain.form.GenColumnInfoFormRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTableColumnDaoImpl extends MapperRepositoryImpl<GenTableColumnMapper, GenTableColumnEntity> implements GenTableColumnDao {

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    @Override
    public void deleteByTableId(Long tableId) {
        LambdaQueryWrapper<GenTableColumnEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(GenTableColumnEntity::getTableId, tableId);
        remove(queryWrapper);
    }

    /**
     * 修改字段信息
     *
     * @param column 字段信息
     */
    @Override
    public void updateFormRequest(GenColumnInfoFormRequest column) {
        LambdaUpdateWrapper<GenTableColumnEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.eq(GenTableColumnEntity::getId, column.getId())
                .set(GenTableColumnEntity::getCodeName, column.getCodeName())
                .set(GenTableColumnEntity::getCodeComment, column.getCodeComment());
        update(updateWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTableColumnEntity, ?> getFieldId() {
        return GenTableColumnEntity::getId;
    }


}
