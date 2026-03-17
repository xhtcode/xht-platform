package com.xht.framework.security.core;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 用户详情转换
 *
 * @author xht
 **/
public final class BasicUserDetailsConvert {

    /**
     * 列表类型转换
     */
    private final static TypeReference<Set<String>> LIST_TYPE_REFERENCE = new TypeReference<>() {
    };
    /**
     * 用户ID
     */
    private final static String USER_ID = "userId";
    /**
     * 用户类型
     */
    private final static String USER_TYPE = "userType";
    /**
     * 用户名
     */
    private final static String USER_NAME = "username";
    /**
     * 用户昵称
     */
    private final static String NICK_NAME = "nickName";
    /**
     * 账号状态
     */
    private final static String USER_STATUS = "userStatus";
    /**
     * 手机号
     */
    private final static String USER_PHONE = "userPhone";
    /**
     * 部门id
     */
    private final static String DEPT_ID = "deptId";
    /**
     * 部门名称
     */
    private final static String DEPT_NAME = "deptName";
    /**
     * 角色列表
     */
    private final static String ROLE_CODES = "roleCodes";
    /**
     * 权限列表
     */
    private final static String MENU_BUTTON_CODE = "menuButtonCodes";
    /**
     * 登录类型
     */
    private final static String LOGIN_TYPE = "loginType";

    private BasicUserDetailsConvert() {
        throw new UnsupportedOperationException("工具类不能实例化");
    }

    /**
     * 将目标对象反转转换为源对象
     *
     * @param target 目标对象，非null
     * @return 反转转换后的源对象，非null
     */
    public static BasicUserDetails reverse(Map<String, Object> target) {
        if (target == null) {
            return null;
        }
        BasicUserDetails basicUserDetails = new BasicUserDetails(
                MapUtil.getLong(target, USER_ID),
                UserTypeEnums.getByValue(MapUtil.getInt(target, USER_TYPE)),
                MapUtil.getStr(target, USER_NAME),
                MapUtil.getStr(target, NICK_NAME),
                null,
                Collections.emptySet());
        basicUserDetails.setUserStatus(UserStatusEnums.getByValue(MapUtil.getInt(target, USER_STATUS)));
        basicUserDetails.setUserPhone(MapUtil.getStr(target, USER_PHONE));
        basicUserDetails.setDeptId(MapUtil.getLong(target, DEPT_ID));
        basicUserDetails.setDeptName(MapUtil.getStr(target, DEPT_NAME));
        basicUserDetails.setRoleCodes(MapUtil.get(target, ROLE_CODES, LIST_TYPE_REFERENCE));
        basicUserDetails.setMenuButtonCodes(MapUtil.get(target, MENU_BUTTON_CODE, LIST_TYPE_REFERENCE));
        basicUserDetails.setLoginType(LoginTypeEnums.getByValue(MapUtil.getStr(target, LOGIN_TYPE)));
        return basicUserDetails;
    }

}
