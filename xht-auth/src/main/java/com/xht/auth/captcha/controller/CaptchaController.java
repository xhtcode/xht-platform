package com.xht.auth.captcha.controller;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.domain.response.CaptchaResponse;
import com.xht.framework.core.domain.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xht
 **/
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final ICaptchaService captchaService;

    /**
     * 登录验证码接口
     *
     * @param request 请求对象
     * @return 验证码图片
     */
    @GetMapping("/login/captcha")
    public R<CaptchaResponse> getCaptcha(HttpServletRequest request) {
        return R.ok(captchaService.generateCaptcha(request));
    }
}