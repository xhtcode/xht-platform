package com.xht.system.modules.authority.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.entity.SysRoleMenuEntity;
import com.xht.system.modules.authority.domain.request.RoleMenuBindRequest;
import com.xht.system.modules.authority.dao.SysMenuDao;
import com.xht.system.modules.authority.dao.SysRoleDao;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.service.ISysRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色菜单Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {


    private final SysRoleMenuDao sysRoleMenuManager;

    private final SysRoleDao sysRoleManager;

    private final SysMenuDao sysMenuManager;

    /**
     * 角色绑定菜单
     *
     * @param bindRequest 角色菜单绑定请求
     * @return 成功、失败
     */
    @Override
    public Boolean roleMenuBind(RoleMenuBindRequest bindRequest) {
        Long roleId = bindRequest.getRoleId();
        Boolean roleExists = sysRoleManager.exists(SysRoleEntity::getId, roleId);
        ThrowUtils.throwIf(!roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        List<Long> menuIds = bindRequest.getMenuIds();
        List<SysRoleMenuEntity> roleMenuEntities = new ArrayList<>();
        if (CollectionUtils.isEmpty(menuIds)) {
            Boolean menuExists = sysMenuManager.existsMenuIds(menuIds);
            ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
            for (Long item : menuIds) {
                SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
                sysRoleMenuEntity.setRoleId(roleId);
                sysRoleMenuEntity.setMenuId(item);
                roleMenuEntities.add(sysRoleMenuEntity);
            }
        }
        return sysRoleMenuManager.roleMenuBind(roleId, roleMenuEntities);
    }

    /**
     * 根据角色ID获取菜单ID
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @Override
    public List<Long> selectMenuIdByRoleId(String roleId) {
        ThrowUtils.hasText(roleId, "角色ID不能为空");
        return sysRoleMenuManager.getRoleId(roleId);
    }
}
