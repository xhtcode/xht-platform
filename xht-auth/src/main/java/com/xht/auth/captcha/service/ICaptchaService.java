package com.xht.auth.captcha.service;

import com.xht.auth.domain.response.CaptchaResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 验证码
 *
 * @author xht
 **/
public interface ICaptchaService {

    /***
     * 生成图片验证码
     * @param request 请求对象
     * @return 验证码图片
     */
    CaptchaResponse generateCaptcha(HttpServletRequest request);
}
