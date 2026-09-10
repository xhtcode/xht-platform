package com.xht.platform.system.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.system.entity.SysRoleMenuEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 角色菜单关系Dao
 *
 * @author xht
 **/
public interface SysRoleMenuDao extends MapperRepository<SysRoleMenuEntity> {

    /**
     * 角色菜单绑定
     *
     * @param roleId           角色ID
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
     * 根据角色ID集合获取菜单ID集合
     *
     * @param roleIds 角色ID集合
     * @return 菜单ID集合
     */
    Set<Long> findMenuIdByRoleIds(Collection<Long> roleIds);

}
