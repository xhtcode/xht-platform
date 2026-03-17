package com.xht.framework.oauth2.handler;

import cn.hutool.core.util.ArrayUtil;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
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

    /**
     * 自定义hasIpAddress方法，适配@PreAuthorize
     * @param ipAddress IP/网段（如192.168.1.0/24、202.24.0.0/14）
     * @return 是否匹配
     */
    public boolean hasIpAddress(String ipAddress) {
        HttpServletRequest httpServletRequest = ServletUtil.getHttpServletRequest();
        if (ServletUtil.getHttpServletRequest() == null) {
            // 非Web环境/无请求上下文时，默认返回false（可根据业务调整）
            return false;
        }
        IpAddressMatcher matcher = new IpAddressMatcher(ipAddress);
        return matcher.matches(httpServletRequest);
    }

}
