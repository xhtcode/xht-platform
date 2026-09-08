package com.xht.framework.security.domain;

import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.utils.ThrowUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 请求的用户信息
 *
 * @author xht
 **/
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestUserBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    @Getter(AccessLevel.PRIVATE)
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
    private Map<String, Object> additionalParameters;

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
