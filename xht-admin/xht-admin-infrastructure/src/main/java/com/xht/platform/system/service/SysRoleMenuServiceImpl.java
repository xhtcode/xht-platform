package com.xht.platform.system.service;

import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.CollectionUtils;
import com.xht.platform.system.converter.SysMenuConverter;
import com.xht.platform.system.dao.SysMenuDao;
import com.xht.platform.system.dao.SysRoleDao;
import com.xht.platform.system.dao.SysRoleMenuDao;
import com.xht.platform.system.dao.SysRoleTypeMenuDao;
import com.xht.platform.system.domain.form.SysRoleMenuBindForm;
import com.xht.platform.system.domain.response.RoleSelectedMenuResponse;
import com.xht.platform.system.entity.SysMenuEntity;
import com.xht.platform.system.entity.SysRoleEntity;
import com.xht.platform.system.entity.SysRoleMenuEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final SysRoleTypeMenuDao sysRoleTypeMenuDao;

    private final SysMenuDao sysMenuDao;

    private final SysMenuConverter sysMenuConverter;

    /**
     * 角色绑定菜单（提交的菜单必须是角色所属角色类型的菜单子集）
     *
     * @param bindRequest 角色菜单绑定请求
     */
    @Override
    public void roleMenuBind(SysRoleMenuBindForm bindRequest) {
        Long roleId = bindRequest.getRoleId();
        SysRoleEntity role = sysRoleDao.findOptionalById(roleId).orElse(null);
        AssertUtils.isTrue(Objects.nonNull(role), BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        List<Long> menuIds = bindRequest.getMenuIds();
        List<SysRoleMenuEntity> roleMenuEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuIds)) {
            // 角色类型为分配菜单的范围依据，未指定类型则不允许分配
            Long roleTypeId = role.getRoleTypeId();
            AssertUtils.isTrue(Objects.nonNull(roleTypeId), BusinessErrorCode.DATA_NOT_EXIST, "角色未绑定角色类型，无法分配菜单");
            List<Long> typeMenuIds = sysRoleTypeMenuDao.findMenuIdByTypeId(roleTypeId);
            AssertUtils.isTrue(typeMenuIds.containsAll(menuIds), BusinessErrorCode.DATA_NOT_EXIST, "菜单不在角色类型的可分配范围内");
            Boolean menuExists = sysMenuDao.existsMenuIds(menuIds);
            AssertUtils.isTrue(menuExists, BusinessErrorCode.DATA_NOT_EXIST, "菜单不存在");
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
     * 根据角色ID获取菜单ID（可分配菜单树按角色所属角色类型反查，已勾选菜单不受类型范围变化影响）
     *
     * @param roleId 角色ID
     * @return {@link RoleSelectedMenuResponse}
     */
    @Override
    public RoleSelectedMenuResponse selectMenuIdByRoleId(String roleId) {
        AssertUtils.hasText(roleId, "角色ID不能为空");
        SysRoleEntity role = sysRoleDao.findOptionalById(roleId).orElse(null);
        AssertUtils.isTrue(Objects.nonNull(role), BusinessErrorCode.DATA_NOT_EXIST, "角色不存在");
        RoleSelectedMenuResponse response = new RoleSelectedMenuResponse();
        List<Long> checkedMenuIds = sysRoleMenuDao.findMenuIdByRoleId(roleId);
        response.setCheckedMenuIds(checkedMenuIds);
        // 可分配菜单 = 角色所属类型绑定的菜单 + 其父级目录（补齐树链路）
        List<Long> typeMenuIds = sysRoleTypeMenuDao.findMenuIdByTypeId(role.getRoleTypeId());
        List<SysMenuEntity> menuList = buildCandidateMenus(typeMenuIds);
        response.setMenuTotal(menuList.size());
        response.setMenuList(sysMenuConverter.toTree(menuList));
        return response;
    }

    /**
     * 构建候选菜单集合：类型绑定菜单 + 全部父级目录
     *
     * @param typeMenuIds 类型绑定的菜单ID集合
     * @return 候选菜单列表
     */
    private List<SysMenuEntity> buildCandidateMenus(List<Long> typeMenuIds) {
        if (CollectionUtils.isEmpty(typeMenuIds)) {
            return new ArrayList<>();
        }
        List<SysMenuEntity> allMenus = sysMenuDao.getMenuTreeSystemTool(true);
        Map<Long, SysMenuEntity> menuMap = new HashMap<>(allMenus.size());
        allMenus.forEach(item -> menuMap.put(item.getId(), item));
        // 父级目录补齐，避免树断裂
        Set<Long> candidateIds = new HashSet<>(typeMenuIds);
        for (Long menuId : typeMenuIds) {
            Long parentId = menuMap.containsKey(menuId) ? menuMap.get(menuId).getParentId() : null;
            while (Objects.nonNull(parentId) && menuMap.containsKey(parentId) && candidateIds.add(parentId)) {
                parentId = menuMap.get(parentId).getParentId();
            }
        }
        return allMenus.stream().filter(item -> candidateIds.contains(item.getId())).collect(Collectors.toList());
    }

}
