package com.xht.auth.captcha.service;

import com.xht.framework.security.domain.response.CaptchaResponse;

/**
 * 验证码
 *
 * @author xht
 **/
public interface ICaptchaService {

    /***
     * 生成图片验证码
     * @param captchaKey 验证码key
     * @return 验证码图片
     */
    CaptchaResponse generateCaptcha(String captchaKey);

    /**
     * 删除验证码
     *
     * @param id 验证码id
     */
    void removeCaptcha(String id);
}
