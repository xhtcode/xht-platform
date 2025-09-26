package com.xht.auth.captcha.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xht.auth.captcha.handler.captcha.ArithmeticCaptcha;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.cache.service.RedisService;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.domain.response.CaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.xht.framework.security.constant.SecurityConstant.REDIS_CAPTCHA_CODE_KEY_PREFIX;

/**
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    /**
     * 验证码过期时间
     */
    private static final long CAPTCHA_EXPIRE_TIME = 60 * 2;

    private final RedisService redisService;

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
        String id = IdUtil.objectId();
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(150, 40);
        CaptchaResponse response = new CaptchaResponse();
        response.setKey(id);
        String key = String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, id);
        String code = arithmeticCaptcha.getCode();
        redisService.set(key, code, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        response.setCode(arithmeticCaptcha.getBase64());
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
            String key = String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, id);
            redisService.delete(key);
        } catch (Exception e) {
            log.warn("删除验证码失败", e);
        }
    }
}
