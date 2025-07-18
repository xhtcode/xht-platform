package com.xht.auth.captcha.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.security.domain.response.CaptchaResponse;
import com.xht.framework.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static com.xht.framework.security.constant.SecurityConstant.REDIS_CAPTCHA_CODE_KEY_PREFIX;

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
     * @param captchaKey 验证码key
     * @return 验证码图片
     */
    @Override
    public CaptchaResponse generateCaptcha(String captchaKey) {
        if (StringUtils.hasText(captchaKey)) {
            removeCaptcha(captchaKey);
        }
        CaptchaResponse response = new CaptchaResponse();
        String id = IdUtil.objectId();
        response.setKey(id);
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 40, 4, 2);
        MathGenerator mathGenerator = new MathGenerator(1);
        captcha.setGenerator(mathGenerator);
        captcha.setBackground(Color.LIGHT_GRAY);
        ExpressionParser parser = new SpelExpressionParser();
        String code = captcha.getCode();
        Expression exp = parser.parseExpression(StringUtils.replace(code, "=", ""));
        code = exp.getValue(String.class);
        stringRedisTemplate.opsForValue().set(String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, id), code, 120, TimeUnit.SECONDS);
        response.setCode(captcha.getImageBase64Data());
        return response;
    }

    /**
     * 删除验证码
     *
     * @param id 验证码id
     */
    @Override
    public void removeCaptcha(String id) {
        try {
            String format = String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, id);
            stringRedisTemplate.delete(format);
        } catch (Exception e) {
            log.warn("删除验证码失败", e);
        }
    }
}
