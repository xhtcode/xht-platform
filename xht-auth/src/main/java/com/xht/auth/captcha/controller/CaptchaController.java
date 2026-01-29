package com.xht.auth.captcha.controller;

import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.domain.response.CaptchaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.xht.framework.security.constant.SecurityConstant.REQUEST_CAPTCHA_CODE_KEY;

/**
 * @author xht
 **/
@IgnoreAuth(aop = false)
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
    @PostMapping("/login/captcha")
    @Operation(summary = "获取登录验证码", description = "生成并获取登录验证码图片")
    public R<CaptchaResponse> getCaptcha(@RequestParam(value = REQUEST_CAPTCHA_CODE_KEY, required = false) String captchaKey) {
        return R.ok().build(captchaService.generateCaptcha(captchaKey));
    }

    /**
     * 生成手机号验证码
     *
     * @param phone 手机号
     */
    @PostMapping("/login/smsCode")
    @Operation(summary = "生成手机号验证码", description = "生成手机号验证码")
    public R<Void> getPhoneCaptcha(@RequestParam(value = "phone") String phone) {
        captchaService.getPhoneCaptcha(phone);
        return R.ok().build();
    }
}