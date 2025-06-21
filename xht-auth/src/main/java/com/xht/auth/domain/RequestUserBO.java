package com.xht.auth.domain;

import cn.hutool.core.map.MapUtil;
import com.xht.auth.constant.SecurityConstant;
import com.xht.auth.constant.enums.LoginTypeEnums;
import com.xht.framework.core.exception.utils.ThrowUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 请求的用户信息
 *
 * @author xht
 **/
@Slf4j
@Getter
@SuppressWarnings("all")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUserBO implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private final String userName;

    /**
     * 密码
     */
    private final String passWord;

    /**
     * oauth2 授权类型
     */
    private final String grantType;

    /**
     * 登录类型
     */
    private final LoginTypeEnums loginType;

    /**
     * 验证码
     */
    private final String captcha;


    /**
     * 根据请求信息构建 用户
     *
     * @param additionalParameters 扩展信息 数据来源{@code  com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter}
     * @return {@link RequestUserBO}
     */
    public static RequestUserBO builderUser(Map<String, Object> additionalParameters) throws AuthenticationException {
        String username = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_USERNAME);
        String password = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PASSWORD);
        String grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        String loginType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_LOGIN_TYPE);
        String captchaCode = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE);
        return new RequestUserBO(username, password, grantType, LoginTypeEnums.of(loginType),captchaCode);
    }

    /**
     * 获取用户授权类型
     *
     * @return {@link AuthorizationGrantType} 授权类型
     */
    public AuthorizationGrantType getAuthorizationGrantType() {
        ThrowUtils.hasText(this.grantType, "grantType must not be empty");
        return new AuthorizationGrantType(grantType);
    }


    /**
     * 校验当前登录类型是否为空
     */
    public void checkLoginType() {
        ThrowUtils.notNull(this.loginType, "loginType must not be null");
    }

}
