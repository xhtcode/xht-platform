package com.xht.generate.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.generate.dao.GenTypeMappingDao;
import com.xht.generate.dao.mapper.GenTypeMappingMapper;
import com.xht.generate.domain.entity.GenTypeMappingEntity;
import com.xht.generate.domain.form.GenTypeMappingForm;
import com.xht.generate.domain.query.GenTypeMappingQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字段映射管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class GenTypeMappingDaoImpl extends MapperRepositoryImpl<GenTypeMappingMapper, GenTypeMappingEntity> implements GenTypeMappingDao {

    /**
     * 更新菜单信息
     *
     * @param form 菜单信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(GenTypeMappingForm form) {
        LambdaUpdateWrapper<GenTypeMappingEntity> updateWrapper = lambdaUpdateWrapper();
        updateWrapper.set(condition(form.getDbType()), GenTypeMappingEntity::getDbType, form.getDbType());
        updateWrapper.set(condition(form.getDbDataType()), GenTypeMappingEntity::getDbDataType, form.getDbDataType());
        updateWrapper.set(condition(form.getTargetLanguage()), GenTypeMappingEntity::getTargetLanguage, form.getTargetLanguage());
        updateWrapper.set(condition(form.getTargetDataType()), GenTypeMappingEntity::getTargetDataType, form.getTargetDataType());
        updateWrapper.set(condition(form.getImportPackage()), GenTypeMappingEntity::getImportPackage, form.getImportPackage());
        updateWrapper.eq(GenTypeMappingEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 分页查询菜单
     *
     * @param page         分页信息
     * @param query 菜单查询请求参数
     * @return 菜单分页信息
     */
    @Override
    public Page<GenTypeMappingEntity> queryPageRequest(Page<GenTypeMappingEntity> page, GenTypeMappingQuery query) {
        LambdaQueryWrapper<GenTypeMappingEntity> queryWrapper = lambdaQueryWrapper();
        queryWrapper.eq(condition(query.getDbType()), GenTypeMappingEntity::getDbType, query.getDbType());
        queryWrapper.eq(condition(query.getTargetLanguage()), GenTypeMappingEntity::getTargetLanguage, query.getTargetLanguage());
        return page(page, queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<GenTypeMappingEntity, ?> getFieldId() {
        return GenTypeMappingEntity::getId;
    }
}
