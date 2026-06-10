package com.xht.framework.security.domain;

import cn.hutool.core.map.MapUtil;
import com.xht.framework.exception.utils.ThrowUtils;
import com.xht.framework.security.constant.SecurityConstant;
import lombok.AccessLevel;
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
     * 验证码
     */
    private final String captcha;

    /**
     * 验证码key
     */
    @Getter(AccessLevel.PRIVATE)
    private final String captchaKey;

    /**
     * 手机号
     */
    private final String phone;

    /**
     * 手机号验证码
     */
    private final String phoneCode;

    private RequestUserBO(String userName, String passWord, String captcha, String captchaKey, String grantType) {
        this.userName = userName;
        this.passWord = passWord;
        this.captcha = captcha;
        this.captchaKey = captchaKey;
        this.phone = null;
        this.phoneCode = null;
        this.grantType = grantType;
    }

    private RequestUserBO(String phone, String phoneCode, String grantType) {
        this.userName = null;
        this.passWord = null;
        this.captcha = null;
        this.captchaKey = null;
        this.phone = phone;
        this.phoneCode = phoneCode;
        this.grantType = grantType;
    }

    /**
     * 根据请求信息构建 用户
     *
     * @param additionalParameters 扩展信息 数据来源{@code  com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter}
     * @return {@link RequestUserBO}
     */
    public static RequestUserBO builderPassword(Map<String, ?> additionalParameters) throws AuthenticationException {
        String username = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_USERNAME);
        String password = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PASSWORD);
        String captchaCode = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE);
        String captchaKey = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE_KEY);
        String grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        return new RequestUserBO(username, password, captchaCode, captchaKey, grantType);
    }

    /**
     * 根据请求信息构建 手机号
     *
     * @param additionalParameters 扩展信息 数据来源{@code  com.xht.cloud.framework.security.authorization.granttype.AbstractAuthenticationConverter}
     * @return {@link RequestUserBO}
     */
    public static RequestUserBO builderPhone(Map<String, ?> additionalParameters) throws AuthenticationException {
        String phone = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PHONE);
        String phoneCode = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PHONE_CODE);
        String grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        return new RequestUserBO(phone, phoneCode, grantType);
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
