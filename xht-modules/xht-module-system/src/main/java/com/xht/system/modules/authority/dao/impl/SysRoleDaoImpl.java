package com.xht.system.modules.authority.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.system.modules.authority.common.enums.RoleStatusEnums;
import com.xht.system.modules.authority.dao.SysRoleDao;
import com.xht.system.modules.authority.dao.mapper.SysRoleMapper;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.form.SysRoleBasicForm;
import com.xht.system.modules.authority.domain.query.SysRoleBasicQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 系统角色管理
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysRoleDaoImpl extends MapperRepositoryImpl<SysRoleMapper, SysRoleEntity> implements SysRoleDao {

    /**
     * 更新角色信息
     *
     * @param form 角色信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFormRequest(SysRoleBasicForm form) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(condition(form.getRoleCode()), SysRoleEntity::getRoleCode, form.getRoleCode());
        updateWrapper.set(condition(form.getRoleName()), SysRoleEntity::getRoleName, form.getRoleName());
        updateWrapper.set(condition(form.getDataScope()), SysRoleEntity::getDataScope, form.getDataScope());
        updateWrapper.set(condition(form.getRoleStatus()), SysRoleEntity::getRoleStatus, form.getRoleStatus());
        updateWrapper.set(condition(form.getRoleSort()), SysRoleEntity::getRoleSort, form.getRoleSort());
        updateWrapper.set(condition(form.getRemark()), SysRoleEntity::getRemark, form.getRemark());
        updateWrapper.eq(SysRoleEntity::getId, form.getId());
        update(updateWrapper);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, RoleStatusEnums status) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRoleEntity::getRoleStatus, status);
        updateWrapper.eq(SysRoleEntity::getId, id);
        update(updateWrapper);
    }

    /**
     * 角色编码是否存在
     *
     * @param roleId   角色ID
     * @param roleCode 角色编码
     * @return 是否存在
     */
    @Override
    public Boolean existsRoleCode(Long roleId, String roleCode) {
        LambdaQueryWrapper<SysRoleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleEntity::getRoleCode, roleCode)
                .ne(Objects.nonNull(roleId), SysRoleEntity::getId, roleId);
        return SqlHelper.retBool(count(lambdaQueryWrapper));
    }

    /**
     * 分页查询角色
     *
     * @param page  分页信息
     * @param query 角色查询请求参数
     * @return 角色分页信息
     */
    @Override
    public Page<SysRoleEntity> findPageList(Page<SysRoleEntity> page, SysRoleBasicQuery query) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(query.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysRoleEntity::getRoleCode, query.getKeyWord())
                                .or()
                                .like(SysRoleEntity::getRoleName, query.getKeyWord())
                )
                .like(condition(query.getRoleCode()), SysRoleEntity::getRoleCode, query.getRoleCode())
                .like(condition(query.getRoleName()), SysRoleEntity::getRoleName, query.getRoleName())
                .eq(Objects.nonNull(query.getRoleStatus()), SysRoleEntity::getRoleStatus, query.getRoleStatus())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 根据角色状态查询角色列表
     *
     * @return 角色列表信息
     */
    @Override
    public List<SysRoleEntity> queryRolesByStatus() {
        // @formatter:off
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysRoleEntity::getId, SysRoleEntity::getRoleCode, SysRoleEntity::getRoleName)
                .eq(SysRoleEntity::getRoleStatus, RoleStatusEnums.NORMAL)
                .orderByDesc(SysRoleEntity::getRoleSort);
        // @formatter:on
        return list(queryWrapper);
    }

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleIds 角色ID列表
     * @return true：存在，false：不存在
     */
    @Override
    public boolean existsByRoleId(List<Long> roleIds) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRoleEntity::getId, roleIds);
        return count(queryWrapper) == roleIds.size();
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysRoleEntity, ?> getFieldId() {
        return SysRoleEntity::getId;
    }
}
