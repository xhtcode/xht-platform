package com.xht.framework.security.domain;

import cn.hutool.core.map.MapUtil;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.utils.ThrowUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ： 请求的用户信息 构建器
 *
 * @author xht
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestUserBuilder {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * oauth2 授权类型
     */
    private String grantType;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String captchaKey;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 手机号验证码
     */
    private String phoneCode;

    /**
     * 自定义授权类型
     */
    private String customGrantType;

    /**
     * 原数据
     */
    private final Map<String, Object> additionalParameters = new HashMap<>();

    /**
     * 创建请求的用户信息构建器
     *
     * @return 请求的用户信息构建器
     */
    public static RequestUserBuilder builder() {
        return new RequestUserBuilder();
    }

    /**
     * 按密码授权类型请求附加参数填充构建器
     *
     * @param additionalParameters 请求附加参数
     * @return 请求的用户信息构建器
     */
    public RequestUserBuilder passwordGrant(Map<String, ?> additionalParameters) {
        ThrowUtils.notEmpty(additionalParameters, "请求附加参数不能为空");
        this.userName = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_USERNAME);
        this.passWord = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PASSWORD);
        this.captcha = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE);
        this.captchaKey = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CAPTCHA_CODE_KEY);
        this.grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        this.customGrantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CUSTOM_GRANT_TYPE);
        this.additionalParameters.putAll(additionalParameters);
        return this;
    }

    /**
     * 按手机号授权类型请求附加参数填充构建器
     *
     * @param additionalParameters 请求附加参数
     * @return 请求的用户信息构建器
     */
    public RequestUserBuilder phoneGrant(Map<String, ?> additionalParameters) {
        ThrowUtils.notEmpty(additionalParameters, "请求附加参数不能为空");
        this.phone = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PHONE);
        this.phoneCode = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_PHONE_CODE);
        this.grantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_OAUTH2_GRANT_TYPE);
        this.customGrantType = MapUtil.getStr(additionalParameters, SecurityConstant.REQUEST_CUSTOM_GRANT_TYPE);
        this.additionalParameters.putAll(additionalParameters);
        return this;
    }

    /**
     * 构建请求的用户信息
     *
     * @return 请求的用户信息
     */
    public RequestUserBO build() {
        ThrowUtils.hasText(grantType, "授权类型不能为空");
        RequestUserBO requestUserBO = new RequestUserBO();
        requestUserBO.setUserName(userName);
        requestUserBO.setPassWord(passWord);
        requestUserBO.setGrantType(grantType);
        requestUserBO.setCaptcha(captcha);
        requestUserBO.setCaptchaKey(captchaKey);
        requestUserBO.setPhone(phone);
        requestUserBO.setPhoneCode(phoneCode);
        requestUserBO.setCustomGrantType(customGrantType);
        requestUserBO.setAdditionalParameters(additionalParameters);
        return requestUserBO;
    }
}
