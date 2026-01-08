package com.xht.modules.system.service;

import com.xht.api.system.domain.form.SysRoleMenuBindForm;
import com.xht.api.system.domain.response.RoleSelectedMenuResponse;

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
     * @return {@link RoleSelectedMenuResponse}
     */
    RoleSelectedMenuResponse selectMenuIdByRoleId(String roleId);
}
