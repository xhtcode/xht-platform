package com.xht.auth.captcha.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.auth.domain.response.CaptchaResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    private final StringRedisTemplate stringRedisTemplate;

    /***
     * 生成图片验证码
     * @param request 请求对象
     * @return 验证码图片
     */
    @Override
    public CaptchaResponse generateCaptcha(HttpServletRequest request) {
        CaptchaResponse response = new CaptchaResponse();
        String id = request.getRequestedSessionId();
        response.setId(id);
        // 使用hutool-captcha生成图形验证码
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
        // 存入session中
        stringRedisTemplate.opsForValue().set("captcha:"+id, captcha.getCode(), 120, TimeUnit.SECONDS);
        response.setCaptcha(captcha.getImageBase64Data());
        return response;
    }
}
