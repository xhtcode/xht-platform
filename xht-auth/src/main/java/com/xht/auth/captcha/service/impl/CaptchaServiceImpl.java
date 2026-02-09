package com.xht.auth.captcha.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.captcha.handler.captcha.ArithmeticCaptcha;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.domain.response.CaptchaResponse;
import com.xht.framework.sms.exception.SmsException;
import com.xht.framework.sms.utils.SmsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.xht.framework.security.constant.SecurityConstant.CAPTCHA_EXPIRE_TIME;
import static com.xht.framework.security.constant.SecurityConstant.REDIS_CAPTCHA_CODE_KEY_PREFIX;

/**
 * 验证码服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 生成图片验证码
     * @param captchaKey 验证码key
     * @return 验证码图片
     */
    @Override
    public CaptchaResponse generateCaptcha(String captchaKey) {
        removeCaptcha(String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaKey));
        String id = IdUtil.objectId();
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(150, 40);
        CaptchaResponse response = new CaptchaResponse();
        response.setKey(id);
        String key = String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, id);
        String code = arithmeticCaptcha.getCode();
        redisTemplate.opsForValue().set(key, code, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        response.setCode(arithmeticCaptcha.getBase64());
        return response;
    }

    /**
     * 校验验证码
     *
     * @param requestKey     验证码key
     * @param requestCaptcha 验证码
     */
    @Override
    public void checkCaptcha(String requestKey, String requestCaptcha) {
        try {
            if (StringUtils.isEmpty(requestCaptcha)) {
                throw new CaptchaException("请输入验证码");
            }
            if (StringUtils.isEmpty(requestKey)) {
                throw new CaptchaException("请输入验证码");
            }
            Long expire = redisTemplate.getExpire(requestKey);
            if (expire <= 0) {
                throw new CaptchaException("验证码已过期，请重新获取验证码.");
            }
            String captchaCode = (String) redisTemplate.opsForValue().get(requestKey);
            if (!StringUtils.equalsIgnoreCase(captchaCode, requestCaptcha)) {
                throw new CaptchaException("输入的验证码不正确");
            }
        } catch (Exception e) {
            log.error("验证码认证失败. {}", e.getMessage(), e);
            throw new CaptchaException(e.getMessage());
        } finally {
            removeCaptcha(requestKey);
        }
    }


    /**
     * 删除验证码
     *
     * @param requestKey 验证码requestKey
     */
    public void removeCaptcha(String requestKey) {
        try {
            if (StringUtils.hasText(requestKey)) {
                redisTemplate.delete(requestKey);
            }
        } catch (Exception e) {
            log.warn("删除验证码失败", e);
        }
    }

    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     */
    @Override
    public void getPhoneCaptcha(String phone) {
        if (!SmsUtils.validMobilePhone(phone)) {
            throw new SmsException("无效的手机号格式");
        }
        String captchaKey = Keys.createKey(SecurityConstant.REDIS_PHONE_CODE_KEY_PREFIX, phone);
        Boolean phoneExists = redisTemplate.hasKey(captchaKey);
        if (phoneExists) {
            throw new BusinessException("验证码发送过于频繁，请请注意查收短信！");
        }
        String captcha = SmsUtils.generatePhoneCode();
        log.info("为手机号{}生成验证码: {}", phone, captcha);  // 实际生产环境建议去掉明文日志
        sendSms(phone, captcha);
        redisTemplate.opsForValue().set(captchaKey, captcha, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 校验手机号 验证码
     *
     * @param phone   手机号
     * @param captcha 验证码
     */
    @Override
    public void checkPhoneCode(String phone, String captcha) {
        if (!SmsUtils.validMobilePhone(phone)) {
            throw new CaptchaException("无效的手机号格式");
        }
        if (StringUtils.isEmpty(captcha)) {
            throw new CaptchaException("请输入验证码");
        }
        String captchaKey = Keys.createKey(SecurityConstant.REDIS_PHONE_CODE_KEY_PREFIX, phone);
        Long expire = redisTemplate.getExpire(captchaKey);
        if (expire <= 0) {
            throw new CaptchaException("验证码已过期，请重新获取验证码");
        }
        String phoneCode = (String) redisTemplate.opsForValue().get(captchaKey);
        if (!StringUtils.equals(captcha, phoneCode)) {
            throw new CaptchaException("输入的验证码不正确");
        }
    }

    /**
     * 发送短信验证码（实际实现需对接短信服务商）
     */
    private void sendSms(String phone, String captcha) {
        log.info("向手机号{}发送短信验证码: {}", phone, captcha);
    }

}
