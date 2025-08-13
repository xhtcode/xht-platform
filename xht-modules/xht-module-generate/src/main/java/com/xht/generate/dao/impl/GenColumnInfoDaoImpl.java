package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenColumnInfoDao;
import com.xht.generate.dao.mapper.GenColumnInfoMapper;
import com.xht.generate.domain.entity.GenColumnInfoEntity;
import com.xht.generate.domain.request.GenColumnInfoFormRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 字段信息管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenColumnInfoDaoImpl extends MapperRepositoryImpl<GenColumnInfoMapper, GenColumnInfoEntity> implements GenColumnInfoDao {

    /**
     * 根据表ID删除字段信息
     *
     * @param tableId 表ID
     */
    @Override
    public void deleteByTableId(String tableId) {
        LambdaQueryWrapper<GenColumnInfoEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(GenColumnInfoEntity::getTableId, tableId);
        remove(queryWrapper);
    }

    /**
     * 修改字段信息
     *
     * @param column 字段信息
     */
    @Override
    public void updateFormRequest(GenColumnInfoFormRequest column) {
        LambdaUpdateWrapper<GenColumnInfoEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.eq(GenColumnInfoEntity::getId, column.getId())
                .set(GenColumnInfoEntity::getCodeName, column.getCodeName())
                .set(GenColumnInfoEntity::getCodeComment, column.getCodeComment())
                .set(GenColumnInfoEntity::getExtConfig, column.getExtConfig());
        update(updateWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenColumnInfoEntity, ?> getFieldId() {
        return GenColumnInfoEntity::getId;
    }


}
