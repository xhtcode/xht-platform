package com.xht.auth.captcha.controller;

import cn.hutool.extra.servlet.JakartaServletUtil;
import com.xht.auth.captcha.domain.response.CaptchaResponse;
import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.log.event.LoginLogApplicationEvent;
import com.xht.framework.log.event.LoginRequestInfo;
import com.xht.framework.security.annotation.IgnoreAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.xht.framework.security.constant.SecurityConstant.REQUEST_CAPTCHA_CODE_KEY;

/**
 * @author xht
 **/
@Tag(name = "验证码接口", description = "验证码相关接口")
@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final ICaptchaService captchaService;


    /**
     * 获取SSO登录验证码
     * <p>
     * 生成并返回SSO登录所需的验证码图片信息。支持传入已有的验证码key，
     * 若未提供则系统会自动生成新的验证码。
     * </p>
     *
     * @param captchaKey 验证码的唯一标识key，用于关联已生成的验证码，可为null
     * @return R<CaptchaResponse> 包含验证码信息的响应对象，包括验证码图片的Base64编码和验证码key
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/sso/login/captcha")
    @Operation(summary = "获取sso登录验证码", description = "生成并获取sso登录验证码图片")
    public R<CaptchaResponse> ssoCaptcha(@RequestParam(value = REQUEST_CAPTCHA_CODE_KEY, required = false) String captchaKey) {
        return R.ok().build(captchaService.generateCaptcha(captchaKey, CaptchaBusinessTypeEnums.SSO));
    }

    /**
     * 生成SSO手机号验证码
     * <p>
     * 向指定手机号发送短信验证码，用于SSO登录时的手机验证。
     * 验证码会通过短信服务发送到目标手机号。
     * </p>
     *
     * @param phone 需要接收验证码的手机号码，不能为空
     * @return R<Void> 操作结果响应，成功时不包含具体数据内容
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/sso/login/smsCode")
    @Operation(summary = "生成sso手机号验证码", description = "生成sso手机号验证码")
    public R<Void> ssoPhoneCaptcha(@RequestParam(value = "phone") String phone) {
        captchaService.getPhoneCaptcha(phone, CaptchaBusinessTypeEnums.SSO);
        return R.ok().build();
    }


    /**
     * 获取OAuth2登录验证码
     * <p>
     * 生成并返回OAuth2登录所需的验证码图片信息。支持传入已有的验证码key，
     * 若未提供则系统会自动生成新的验证码。
     * </p>
     *
     * @param captchaKey 验证码的唯一标识key，用于关联已生成的验证码，可为null
     * @return R<CaptchaResponse> 包含验证码信息的响应对象，包括验证码图片的Base64编码和验证码key
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/oauth2/login/captcha")
    @Operation(summary = "获取oauth2登录验证码", description = "生成并获取oauth2登录验证码图片")
    public R<CaptchaResponse> oauth2Captcha(@RequestParam(value = REQUEST_CAPTCHA_CODE_KEY, required = false) String captchaKey) {
        return R.ok().build(captchaService.generateCaptcha(captchaKey, CaptchaBusinessTypeEnums.OAUTH2));
    }

    /**
     * 生成OAuth2手机号验证码
     * <p>
     * 向指定手机号发送短信验证码，用于OAuth2登录时的手机验证。
     * 验证码会通过短信服务发送到目标手机号。
     * </p>
     *
     * @param phone 需要接收验证码的手机号码，不能为空
     * @return R<Void> 操作结果响应，成功时不包含具体数据内容
     */
    @IgnoreAuth(aop = false)
    @PostMapping("/oauth2/login/smsCode")
    @Operation(summary = "生成oauth2手机号验证码", description = "生成oauth2手机号验证码")
    public R<Void> oauth2PhoneCaptcha(@RequestParam(value = "phone") String phone) {
        captchaService.getPhoneCaptcha(phone, CaptchaBusinessTypeEnums.OAUTH2);
        return R.ok().build();
    }

}