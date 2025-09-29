package com.xht.system.modules.authority.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.tree.INode;
import com.xht.framework.core.utils.tree.TreeNode;
import com.xht.framework.core.utils.tree.TreeUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.utils.SecurityUtils;
import com.xht.system.modules.authority.common.enums.MenuCacheEnums;
import com.xht.system.modules.authority.common.enums.MenuHiddenEnums;
import com.xht.system.modules.authority.common.enums.MenuLinkEnums;
import com.xht.system.modules.authority.dao.SysRoleMenuDao;
import com.xht.system.modules.authority.dao.SysUserRoleDao;
import com.xht.system.modules.authority.domain.entity.SysRoleEntity;
import com.xht.system.modules.authority.domain.response.SysMenuResponse;
import com.xht.system.modules.authority.domain.vo.AuthorityUserVO;
import com.xht.system.modules.authority.domain.vo.MetaVo;
import com.xht.system.modules.authority.domain.vo.RouterVo;
import com.xht.system.modules.authority.service.IAuthorityService;
import com.xht.system.modules.dept.domain.response.SysPostResponse;
import com.xht.system.modules.user.dao.SysUserDao;
import com.xht.system.modules.user.dao.SysUserPostDao;
import com.xht.system.modules.user.domain.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 权限Service实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements IAuthorityService {

    private final SysUserDao sysUserDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final SysRoleMenuDao sysRoleMenuDao;

    private final SysUserPostDao sysUserPostDao;

    /**
     * 获取菜单元数据
     *
     * @param menu 菜单信息
     * @return 菜单元数据
     */
    private static MetaVo getMetaVo(SysMenuResponse menu) {
        MetaVo metaVo = new MetaVo();
        metaVo.setTitle(menu.getMenuName());
        metaVo.setIcon(menu.getMenuIcon());
        metaVo.setLinkStatus(Objects.requireNonNullElse(menu.getFrameFlag(), MenuLinkEnums.NO).getStatus());
        metaVo.setMenuType(menu.getMenuType().getValue());
        metaVo.setActiveMenuPath(menu.getActiveMenuPath());
        metaVo.setHiddenStatus(Objects.requireNonNullElse(menu.getMenuHidden(), MenuHiddenEnums.SHOW).getHidden());
        metaVo.setKeepAliveStatus(Objects.requireNonNullElse(menu.getMenuCache(), MenuCacheEnums.YES).getStatus());
        metaVo.setRoles(StrUtil.splitTrim(menu.getMenuAuthority(), ","));//perms
        metaVo.setRank(menu.getMenuSort());
        return metaVo;
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return 用户信息
     */
    @Override
    public AuthorityUserVO getUserProfileInfo() {
        BasicUserDetails user = SecurityUtils.getUser();
        AuthorityUserVO vo = new AuthorityUserVO();
        SysUserVO sysUserVO = sysUserDao.findInfoByUserId(user.getUserId());
        if (Objects.isNull(sysUserVO)) {
            return vo;
        }
        List<SysPostResponse> postResponses = sysUserPostDao.getPostByUserId(user.getUserId());
        sysUserVO.setPost(postResponses);
        sysUserVO.setPassWord(null);
        sysUserVO.setPassWordSalt(null);
        vo.setUserInfo(sysUserVO);
        List<SysRoleEntity> roles = sysUserRoleDao.findRoleListByUserId(user.getUserId());
        List<String> permissionCodes = sysRoleMenuDao.findPermissionCodeByUserId(user.getUserId());
        vo.setRoleCodes(roles.stream().map(SysRoleEntity::getRoleCode).collect(Collectors.toList()));
        vo.setPermissionCodes(permissionCodes);
        vo.setDataScope(0);
        return vo;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Override
    public List<INode<Long>> getRouters() {
        BasicUserDetails user = SecurityUtils.getUser();
        List<SysMenuResponse> menus = sysRoleMenuDao.findRouterByUserId(user.getUserId());
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<INode<Long>> result = new ArrayList<>(menus.size());
        for (SysMenuResponse menu : menus) {
            RouterVo routerVo = new RouterVo();
            routerVo.setPath(menu.getMenuPath());
            routerVo.setName(StringUtils.emptyToDefault(menu.getViewName(), menu.getId() + ""));
            routerVo.setComponent(menu.getViewPath());
            routerVo.setMeta(getMetaVo(menu));
            result.add(new TreeNode<>(menu.getId(), menu.getParentId(), menu.getMenuSort()).setExtra(BeanUtil.beanToMap(routerVo)));
        }
        return TreeUtils.buildList(result, Boolean.FALSE);
    }

}
