package com.xht.framework.security.domain;

import cn.hutool.core.map.MapUtil;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.security.constant.SecurityConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

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
     * 验证码key
     */
    @Getter(AccessLevel.PRIVATE)
    private final String captchaKey;


    /**
     * 根据请求信息构建 用户
     *
     * @param additionalParameters 扩展信息 数据来源{@code  com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter}
     * @return {@link RequestUserBO}
     */
    public static RequestUserBO builderUser(Map<String, ?> additionalParameters) throws AuthenticationException {
        String username = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_USERNAME);
        String password = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PASSWORD);
        String grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        String loginType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_LOGIN_TYPE);
        String captchaCode = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE);
        String captchaKey = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE_KEY);
        return new RequestUserBO(username, password, grantType, LoginTypeEnums.of(loginType), captchaCode, captchaKey);
    }


    /**
     * 校验当前登录类型是否为空
     */
    public void checkLoginType() {
        ThrowUtils.notNull(this.loginType, "loginType must not be null");
    }

    /**
     * 检查用户名是否为空
     * 如果用户名为空，则抛出异常
     */
    public void checkUserName() {
        ThrowUtils.hasText(this.userName, "用户名不能为空");
    }

    /**
     * 检查密码是否为空
     * 如果密码为空，则抛出异常
     */
    public void checkPassWord() {
        ThrowUtils.hasText(this.passWord, "密码不能为空");
    }

    /**
     * 生成验证码在Redis中的键值
     * 将验证码前缀与验证码key拼接生成完整的Redis键
     *
     * @return 验证码在Redis中的完整键值
     */
    public String generateCaptchaKey() {
        return String.format("%s%s", SecurityConstant.REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaKey);
    }

}
