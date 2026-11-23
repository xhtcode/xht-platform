package com.xht.platform.system.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.system.entity.SysRoleTypeMenuEntity;

import java.util.List;

/**
 * 角色类型菜单关系Dao
 *
 * @author xht
 **/
public interface SysRoleTypeMenuDao extends MapperRepository<SysRoleTypeMenuEntity> {

    /**
     * 角色类型菜单绑定（先删后插）
     *
     * @param roleTypeId            角色类型ID
     * @param roleTypeMenuEntities  角色类型菜单关系集合
     */
    void roleTypeMenuBind(Long roleTypeId, List<SysRoleTypeMenuEntity> roleTypeMenuEntities);

    /**
     * 根据角色类型ID获取菜单ID集合（仅正常状态且未删除的菜单）
     *
     * @param roleTypeId 角色类型ID
     * @return 菜单ID集合
     */
    List<Long> findMenuIdByTypeId(Long roleTypeId);

}
