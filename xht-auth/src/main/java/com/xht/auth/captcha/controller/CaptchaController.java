package com.xht.auth.captcha.controller;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.security.domain.response.CaptchaResponse;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.InnerAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.xht.framework.security.constant.SecurityConstant.REQUEST_CAPTCHA_CODE_KEY;

/**
 * @author xht
 **/
@RestController
@RequiredArgsConstructor
@Tag(name = "验证码接口", description = "验证码相关接口")
public class CaptchaController {

    private final ICaptchaService captchaService;

    /**
     * 登录验证码接口
     *
     * @param captchaKey 验证码唯一标识
     * @return 验证码图片
     */
    @InnerAuth
    @GetMapping("/login/captcha")
    @Operation(summary = "获取登录验证码", description = "生成并获取登录验证码图片")
    public R<CaptchaResponse> getCaptcha(@RequestParam(value = REQUEST_CAPTCHA_CODE_KEY, required = false) String captchaKey) {
        return R.ok(captchaService.generateCaptcha(captchaKey));
    }
}