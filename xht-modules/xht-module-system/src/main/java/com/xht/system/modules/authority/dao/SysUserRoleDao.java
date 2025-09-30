package com.xht.system.modules.authority.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.entity.SysUserRoleEntity;

import java.util.List;

/**
 * 用户角色
 *
 * @author xht
 **/
public interface SysUserRoleDao extends MapperRepository<SysUserRoleEntity> {

    /**
     * 保存用户角色
     *
     * @param userId              用户ID
     * @param sysUserRoleEntities 角色列表
     */
    void saveUserRole(Long userId, List<SysUserRoleEntity> sysUserRoleEntities);

    /**
     * 根据用户ID查询角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getRoleId(String userId);

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> findRoleListByUserId(Long userId);
}
