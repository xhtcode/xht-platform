package com.xht.modules.admin.system.service.impl;

import com.xht.framework.core.exception.code.BusinessErrorCode;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.modules.admin.system.converter.SysMenuConverter;
import com.xht.modules.admin.system.dao.SysMenuDao;
import com.xht.modules.admin.system.dao.SysRoleDao;
import com.xht.modules.admin.system.dao.SysRoleMenuDao;
import com.xht.modules.admin.system.entity.SysMenuEntity;
import com.xht.modules.admin.system.entity.SysRoleEntity;
import com.xht.modules.admin.system.entity.SysRoleMenuEntity;
import com.xht.api.system.domain.form.SysRoleMenuBindForm;
import com.xht.api.system.domain.response.RoleSelectedMenuResponse;
import com.xht.modules.admin.system.service.ISysRoleMenuService;
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


    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysRoleDao sysRoleDao;

    private final SysMenuDao sysMenuDao;

    private final SysMenuConverter sysMenuConverter;

    /**
     * 角色绑定菜单
     *
     * @param bindRequest 角色菜单绑定请求
     */
    @Override
    public void roleMenuBind(SysRoleMenuBindForm bindRequest) {
        Long roleId = bindRequest.getRoleId();
        Boolean roleExists = sysRoleDao.exists(SysRoleEntity::getId, roleId);
        ThrowUtils.throwIf(!roleExists, BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        List<Long> menuIds = bindRequest.getMenuIds();
        List<SysRoleMenuEntity> roleMenuEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuIds)) {
            Boolean menuExists = sysMenuDao.existsMenuIds(menuIds);
            ThrowUtils.throwIf(!menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
            for (Long item : menuIds) {
                SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
                sysRoleMenuEntity.setRoleId(roleId);
                sysRoleMenuEntity.setMenuId(item);
                roleMenuEntities.add(sysRoleMenuEntity);
            }
        }
        sysRoleMenuDao.roleMenuBind(roleId, roleMenuEntities);
    }

    /**
     * 根据角色ID获取菜单ID
     *
     * @param roleId 角色ID
     * @return {@link RoleSelectedMenuResponse}
     */
    @Override
    public RoleSelectedMenuResponse selectMenuIdByRoleId(String roleId) {
        ThrowUtils.hasText(roleId, "角色ID不能为空");
        RoleSelectedMenuResponse response = new RoleSelectedMenuResponse();
        List<Long> menuIdByRoleId = sysRoleMenuDao.findMenuIdByRoleId(roleId);
        List<SysMenuEntity> menuEntityList = sysMenuDao.getMenuTreeSystemTool(true);
        if (!CollectionUtils.isEmpty(menuIdByRoleId) && !CollectionUtils.isEmpty(menuEntityList)) {
            response.setCheckAll(menuIdByRoleId.size() == menuEntityList.size());
        } else {
            response.setCheckAll(false);
        }
        response.setCheckedKeys(menuIdByRoleId);
        response.setMenuList(sysMenuConverter.toTree(menuEntityList));
        return response;
    }
}
