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
import com.xht.system.modules.authority.domain.request.SysRoleFormRequest;
import com.xht.system.modules.authority.domain.request.SysRoleQueryRequest;
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
     * @param formRequest 角色信息
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateFormRequest(SysRoleFormRequest formRequest) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(condition(formRequest.getRoleCode()), SysRoleEntity::getRoleCode, formRequest.getRoleCode());
        updateWrapper.set(condition(formRequest.getRoleName()), SysRoleEntity::getRoleName, formRequest.getRoleName());
        updateWrapper.set(condition(formRequest.getDataScope()), SysRoleEntity::getDataScope, formRequest.getDataScope());
        updateWrapper.set(condition(formRequest.getRoleStatus()), SysRoleEntity::getRoleStatus, formRequest.getRoleStatus());
        updateWrapper.set(condition(formRequest.getRoleSort()), SysRoleEntity::getRoleSort, formRequest.getRoleSort());
        updateWrapper.set(condition(formRequest.getRemark()), SysRoleEntity::getRemark, formRequest.getRemark());
        updateWrapper.eq(SysRoleEntity::getId, formRequest.getId());
        return update(updateWrapper);
    }

    /**
     * 更新角色状态
     *
     * @param id     角色ID
     * @param status 角色状态
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateStatus(Long id, RoleStatusEnums status) {
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRoleEntity::getRoleStatus, status);
        updateWrapper.eq(SysRoleEntity::getId, id);
        return update(updateWrapper);
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
     * @param page         分页信息
     * @param queryRequest 角色查询请求参数
     * @return 角色分页信息
     */
    @Override
    public Page<SysRoleEntity> queryPageRequest(Page<SysRoleEntity> page, SysRoleQueryRequest queryRequest) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        // @formatter:off
        queryWrapper.and(
                        condition(queryRequest.getKeyWord()), wrapper -> wrapper.or()
                                .like(SysRoleEntity::getRoleCode, queryRequest.getKeyWord())
                                .or()
                                .like(SysRoleEntity::getRoleName, queryRequest.getKeyWord())
                )
                .like(condition(queryRequest.getRoleCode()), SysRoleEntity::getRoleCode, queryRequest.getRoleCode())
                .like(condition(queryRequest.getRoleName()), SysRoleEntity::getRoleName, queryRequest.getRoleName())
                .eq(Objects.nonNull(queryRequest.getRoleStatus()), SysRoleEntity::getRoleStatus, queryRequest.getRoleStatus())
        ;
        // @formatter:on
        return page(page, queryWrapper);
    }

    /**
     * 根据角色状态查询角色列表
     *
     * @param roleStatusEnums 角色状态
     * @return 角色列表信息
     */
    @Override
    public List<SysRoleEntity> queryRolesByStatus(RoleStatusEnums roleStatusEnums) {
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
