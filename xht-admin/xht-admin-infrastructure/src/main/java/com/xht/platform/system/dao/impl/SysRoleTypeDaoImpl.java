package com.xht.platform.system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.system.dao.SysRoleTypeDao;
import com.xht.platform.system.dao.mapper.SysRoleTypeMapper;
import com.xht.platform.system.domain.form.SysRoleTypeForm;
import com.xht.platform.system.domain.query.SysRoleTypeQuery;
import com.xht.platform.system.entity.SysRoleTypeEntity;
import com.xht.platform.system.enums.RoleStatusEnum;
import com.xht.framework.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 角色类型管理 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysRoleTypeDaoImpl extends MapperRepositoryImpl<SysRoleTypeMapper, SysRoleTypeEntity> implements SysRoleTypeDao {

    /**
     * 更新角色类型信息
     *
     * @param id   角色类型ID
     * @param form 角色类型信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(Long id, SysRoleTypeForm form) {
        LambdaUpdateWrapper<SysRoleTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(condition(form.getTypeCode()), SysRoleTypeEntity::getTypeCode, form.getTypeCode());
        updateWrapper.set(condition(form.getTypeName()), SysRoleTypeEntity::getTypeName, form.getTypeName());
        updateWrapper.set(condition(form.getTypeStatus()), SysRoleTypeEntity::getTypeStatus, form.getTypeStatus());
        updateWrapper.set(condition(form.getTypeSort()), SysRoleTypeEntity::getTypeSort, form.getTypeSort());
        updateWrapper.set(condition(form.getRemark()), SysRoleTypeEntity::getRemark, form.getRemark());
        updateWrapper.eq(SysRoleTypeEntity::getId, id);
        update(updateWrapper);
    }

    /**
     * 更新角色类型状态
     *
     * @param id     角色类型ID
     * @param status 角色类型状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, RoleStatusEnum status) {
        LambdaUpdateWrapper<SysRoleTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRoleTypeEntity::getTypeStatus, status);
        updateWrapper.eq(SysRoleTypeEntity::getId, id);
        update(updateWrapper);
    }

    /**
     * 角色类型编码是否存在
     *
     * @param id       角色类型ID
     * @param typeCode 类型编码
     * @return 是否存在
     */
    @Override
    public Boolean existsTypeCode(Long id, String typeCode) {
        LambdaQueryWrapper<SysRoleTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleTypeEntity::getTypeCode, typeCode)
                .ne(Objects.nonNull(id), SysRoleTypeEntity::getId, id);
        return SqlHelper.retBool(count(queryWrapper));
    }

    /**
     * 分页查询角色类型
     *
     * @param page  分页信息
     * @param query 角色类型查询请求参数
     * @return 角色类型分页信息
     */
    @Override
    public Page<SysRoleTypeEntity> findPageList(Page<SysRoleTypeEntity> page, SysRoleTypeQuery query) {
        LambdaQueryWrapper<SysRoleTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.isQuick()) {
            // @formatter:off
            queryWrapper.and(condition(query.getKeyWord()), wrapper -> wrapper.or()
                    .like(SysRoleTypeEntity::getTypeCode, query.getKeyWord())
                    .or()
                    .like(SysRoleTypeEntity::getTypeName, query.getKeyWord())
            );
            // @formatter:on
        } else {
            queryWrapper.like(condition(query.getTypeCode()), SysRoleTypeEntity::getTypeCode, query.getTypeCode());
            queryWrapper.like(condition(query.getTypeName()), SysRoleTypeEntity::getTypeName, query.getTypeName());
            queryWrapper.eq(Objects.nonNull(query.getTypeStatus()), SysRoleTypeEntity::getTypeStatus, query.getTypeStatus());
        }
        return page(page, queryWrapper);
    }

    /**
     * 根据角色类型状态查询角色类型列表
     *
     * @param typeStatus 角色类型状态
     * @return 角色类型列表信息
     */
    @Override
    public List<SysRoleTypeEntity> findListByStatus(RoleStatusEnum typeStatus) {
        // @formatter:off
        LambdaQueryWrapper<SysRoleTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysRoleTypeEntity::getId, SysRoleTypeEntity::getTypeCode, SysRoleTypeEntity::getTypeName)
                .eq(SysRoleTypeEntity::getTypeStatus, typeStatus)
                .orderByDesc(SysRoleTypeEntity::getTypeSort);
        // @formatter:on
        return list(queryWrapper);
    }

    /**
     * 根据角色类型ID集合查询角色类型列表
     *
     * @param ids 角色类型ID集合
     * @return 角色类型列表信息
     */
    @Override
    public List<SysRoleTypeEntity> findListByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysRoleTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleTypeEntity::getId, ids);
        return list(queryWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysRoleTypeEntity, ?> getFieldId() {
        return SysRoleTypeEntity::getId;
    }
}
