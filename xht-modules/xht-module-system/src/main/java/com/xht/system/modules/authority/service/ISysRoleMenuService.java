package com.xht.system.modules.authority.service;

import com.xht.system.modules.authority.domain.request.SysRoleMenuBindForm;

import java.util.List;

/**
 * 角色菜单Service接口
 *
 * @author xht
 **/
public interface ISysRoleMenuService {
    /**
     * 角色绑定菜单
     *
     * @param bindRequest 角色菜单绑定请求
     */
    void roleMenuBind(SysRoleMenuBindForm bindRequest);

    /**
     * 根据角色ID获取菜单ID
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> selectMenuIdByRoleId(String roleId);
}
