package com.xht.system.modules.authority.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.system.modules.authority.domain.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色菜单关系Dao
 *
 * @author xht
 **/
public interface SysRoleMenuDao extends MapperRepository<SysRoleMenuEntity> {

    /***
     * 角色菜单绑定
     * @param roleId 角色ID
     * @param roleMenuEntities 角色菜单关系集合
     * @return true/false
     */
    Boolean roleMenuBind(Long roleId, List<SysRoleMenuEntity> roleMenuEntities);

    /**
     * 根据角色ID获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> getRoleId(String roleId);

    /**
     * 用户id获取菜单集合
     * @param userId 用户id
     * @return 菜单集合
     */
    List<String> findPermissionCodeByUserId(Long userId);
}
