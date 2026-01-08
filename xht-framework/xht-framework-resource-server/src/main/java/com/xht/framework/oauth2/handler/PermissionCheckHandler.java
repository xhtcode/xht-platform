package com.xht.framework.oauth2.handler;

import cn.hutool.core.util.ArrayUtil;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import java.util.Set;

/**
 * 描述 ： 权限校验
 *
 * @author xht
 **/
@Slf4j
public class PermissionCheckHandler {

    /**
     * 判断当前登录用户是否为管理员
     */
    public boolean isAdmin() {
        BasicUserDetails user = SecurityUtils.getUser();
        return UserTypeEnums.isAdmin(user.getUserType());
    }


    /**
     * 判断接口是否有任意xxx，xxx角色
     *
     * @param roleCodes 角色
     * @return {boolean}
     */
    public boolean hasRoleCode(String... roleCodes) {
        BasicUserDetails user = SecurityUtils.getUser();
        if (UserTypeEnums.isAdmin(user.getUserType())) {
            return true;
        }
        if (ArrayUtil.isEmpty(roleCodes)) {
            return false;
        }
        Set<String> roles = user.getRoleCodes();
        if (CollectionUtils.isEmpty(roles)) return false;
        return roles.stream().anyMatch(x -> PatternMatchUtils.simpleMatch(roleCodes, x));
    }

    /**
     * 判断接口是否有菜单按钮权限
     *
     * @param menuCodes 菜单按钮权限
     * @return {boolean}
     */
    public boolean hasMenuCode(String... menuCodes) {
        BasicUserDetails user = SecurityUtils.getUser();
        if (UserTypeEnums.isAdmin(user.getUserType())) {
            return true;
        }
        if (ArrayUtil.isEmpty(menuCodes)) {
            return false;
        }
        Set<String> roles = user.getMenuButtonCodes();
        if (CollectionUtils.isEmpty(roles)) return false;
        return roles.stream().anyMatch(x -> PatternMatchUtils.simpleMatch(menuCodes, x));
    }

}
