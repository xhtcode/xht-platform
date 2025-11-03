package com.xht.auth.captcha.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.captcha.handler.captcha.ArithmeticCaptcha;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.domain.response.CaptchaResponse;
import com.xht.framework.sms.exception.SmsException;
import com.xht.framework.sms.utils.SmsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.xht.framework.security.constant.SecurityConstant.REDIS_CAPTCHA_CODE_KEY_PREFIX;

/**
 * 验证码服务实现类
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl implements ICaptchaService {

    /**
     * 验证码请求间隔限制（秒）
     */
    private static final long CAPTCHA_INTERVAL = 60;
    /**
     * 验证码过期时间
     */
    private static final long CAPTCHA_EXPIRE_TIME = 60 * 2;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     */
    @Override
    public void getPhoneCaptcha(String phone) {
        // 1. 验证手机号格式
        if (!SmsUtils.validMobilePhone(phone)) {
            throw new SmsException("无效的手机号格式");
        }
        // 2. 判断手机号是否存在于系统中
        if (!isPhoneExists(phone)) {
            log.warn("手机号不存在于系统中: {}", phone);
            throw new BusinessException("该手机号未注册");  // 假设存在自定义业务异常
        }
        // 3. 生成Redis键名（区分验证码内容和发送时间）
        String captchaKey = "captcha:phone:" + phone;
        String sendTimeKey = "captcha:phone:sendTime:" + phone;

        // 4. 检查短时间内是否已发送过验证码
        Object lastSendTimeObj = redisTemplate.opsForValue().get(sendTimeKey);
        if (lastSendTimeObj != null) {
            long lastSendTime = (long) lastSendTimeObj;
            long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime - lastSendTime < CAPTCHA_INTERVAL) {
                long remaining = CAPTCHA_INTERVAL - (currentTime - lastSendTime);
                log.warn("手机号{}请求验证码过于频繁，剩余{}秒", phone, remaining);
                throw new BusinessException("验证码发送过于频繁，请" + remaining + "秒后再试");
            }
        }
        // 5. 生成6位数字验证码
        String captcha = "123456";
        log.info("为手机号{}生成验证码: {}", phone, captcha);  // 实际生产环境建议去掉明文日志
        // 6. 发送验证码（此处仅为示例，实际需调用短信服务商API）
        boolean sendSuccess = sendSms(phone, captcha);
        if (!sendSuccess) {
            throw new BusinessException("验证码发送失败，请稍后重试");
        }
        // 7. 存储验证码和发送时间到Redis
        redisTemplate.opsForValue().set(captchaKey, captcha, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(sendTimeKey, System.currentTimeMillis() / 1000, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
    }


    /**
     * 检查手机号是否存在于系统中
     */
    private boolean isPhoneExists(String phone) {
        return true;
    }


    /**
     * 发送短信验证码（实际实现需对接短信服务商）
     */
    private boolean sendSms(String phone, String captcha) {
        try {
            // 示例：调用短信API发送验证码
            log.info("向手机号{}发送短信验证码: {}", phone, captcha);
            return true;
        } catch (Exception e) {
            log.error("发送短信失败", e);
            return false;
        }
    }

    /***
     * 生成图片验证码
     * @param captchaKey 验证码key
     * @return 验证码图片
     */
    @Override
    public CaptchaResponse generateCaptcha(String captchaKey) {
        removeCaptcha( String.format("%s%s", REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaKey));
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
                throw new CaptchaException("请输入验证码.");
            }
            if (StringUtils.isEmpty(requestKey)) {
                throw new CaptchaException("验证码校验失败");
            }
            Long expire = redisTemplate.getExpire(requestKey);
            if (expire <= 0) {
                throw new CaptchaException("验证码已过期.");
            }
            String captchaCode = (String) redisTemplate.opsForValue().get(requestKey);
            if (!StringUtils.equalsIgnoreCase(captchaCode, requestCaptcha)) {
                throw new CaptchaException("验证码错误.");
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

}
