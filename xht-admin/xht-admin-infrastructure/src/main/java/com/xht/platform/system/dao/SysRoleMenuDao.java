package com.xht.platform.system.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import  com.xht.platform.system.domain.response.SysMenuResponse;
import  com.xht.platform.system.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色菜单关系Dao
 *
 * @author xht
 **/
public interface SysRoleMenuDao extends MapperRepository<SysRoleMenuEntity> {

    /**
     * 角色菜单绑定
     * @param roleId 角色ID
     * @param roleMenuEntities 角色菜单关系集合
     */
    void roleMenuBind(Long roleId, List<SysRoleMenuEntity> roleMenuEntities);

    /**
     * 根据角色ID获取菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> findMenuIdByRoleId(String roleId);

    /**
     * 根据用户ID获取路由菜单集合
     *
     * @param userId 用户ID
     * @return 路由菜单集合
     */
    List<SysMenuResponse> findRouterByUserId(Long userId);

}
