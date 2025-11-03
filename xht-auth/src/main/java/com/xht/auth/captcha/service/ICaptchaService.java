package com.xht.auth.captcha.service;

import com.xht.framework.security.domain.response.CaptchaResponse;

/**
 * 验证码
 *
 * @author xht
 **/
public interface ICaptchaService {

    /**
     * 获取手机验证码
     * @param phone 手机号
     */
    void getPhoneCaptcha(String phone);

    /***
     * 生成图片验证码
     * @param captchaKey 验证码key
     * @return 验证码图片
     */
    CaptchaResponse generateCaptcha(String captchaKey);

    /**
     * 校验验证码
     *
     * @param requestKey 验证码key
     * @param requestCaptcha    验证码
     */
    void checkCaptcha(String requestKey, String requestCaptcha);
}
