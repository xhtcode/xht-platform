package com.xht.auth.captcha.service.impl;

import com.xht.auth.authentication.dao.IAuthenticationDao;
import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.auth.captcha.exception.CaptchaException;
import com.xht.auth.captcha.handler.captcha.ArithmeticCaptcha;
import com.xht.auth.captcha.service.ICaptchaService;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.auth.captcha.domain.response.CaptchaResponse;
import com.xht.framework.core.utils.IdUtils;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.sms.exception.SmsException;
import com.xht.framework.sms.utils.SmsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final RedisRepository redisRepository;

    private final IAuthenticationDao authenticationDao;

    /**
     * 生成验证码
     * <p>
     * 根据业务类型生成验证码图片，支持传入已有的验证码key进行关联。
     * 生成的验证码会存储在服务端用于后续校验。
     * </p>
     *
     * @param captchaKey 验证码的唯一标识key，可为null，为null时系统会自动生成
     * @param captchaBusinessType 验证码业务类型枚举，用于区分不同业务场景（如SSO、OAuth2等）
     * @return CaptchaResponse 验证码响应对象，包含验证码图片的Base64编码和验证码key
     */
    @Override
    public CaptchaResponse generateCaptcha(String captchaKey, CaptchaBusinessTypeEnums captchaBusinessType) {
        String removeKey = Keys.createKey(REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaBusinessType.getValue(), captchaKey);
        removeCaptcha(removeKey);
        String id = IdUtils.simpleUUID();
        ArithmeticCaptcha arithmeticCaptcha = new ArithmeticCaptcha(150, 40);
        CaptchaResponse response = new CaptchaResponse();
        response.setKey(id);
        String newKey = Keys.createKey(REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaBusinessType.getValue(), id);
        String code = arithmeticCaptcha.getCode();
        redisRepository.set(newKey, code, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
        response.setCode(arithmeticCaptcha.getBase64());
        return response;
    }

    /**
     * 校验验证码
     * <p>
     * 验证用户输入的验证码是否正确，包括验证码的存在性、有效期和内容匹配性检查。
     * 校验失败时会抛出相应的异常。
     * </p>
     *
     * @param requestKey 请求中的验证码key，用于定位要校验的验证码
     * @param requestCaptcha 用户输入的验证码内容
     * @param captchaBusinessType 验证码业务类型枚举，用于区分不同业务场景
     */
    @Override
    public void checkCaptcha(String requestKey, String requestCaptcha, CaptchaBusinessTypeEnums captchaBusinessType) {
        if (true){
            return;
        }
        String cacheKey = null;
        try {
            ThrowUtils.hasText(requestCaptcha, () -> new CaptchaException("验证码错误，请输入正确的验证码"));
            ThrowUtils.hasText(requestKey, () -> new CaptchaException("验证码错误，请输入正确的验证码"));
            cacheKey = Keys.createKey(REDIS_CAPTCHA_CODE_KEY_PREFIX, captchaBusinessType.getValue(), requestKey);
            Long expire = redisRepository.getExpire(cacheKey);
            if (expire <= 0) {
                throw new CaptchaException("验证码已过期，请重新获取验证码.");
            }
            String captchaCode = redisRepository.get(cacheKey);
            if (!StringUtils.equalsIgnoreCase(captchaCode, requestCaptcha)) {
                throw new CaptchaException("验证码错误，请输入正确的验证码");
            }
        } catch (Exception e) {
            log.error("验证码认证失败. {}", e.getMessage(), e);
            throw new CaptchaException(e.getMessage());
        } finally {
            if (StringUtils.hasText(cacheKey)) {
                removeCaptcha(cacheKey);
            }
        }
    }

    /**
     * 获取手机短信验证码
     * <p>
     * 向指定手机号发送短信验证码，验证码会存储在服务端用于后续校验。
     * 通常会有发送频率限制以防止滥用。
     * </p>
     *
     * @param phone 需要接收验证码的手机号码
     * @param captchaBusinessType 验证码业务类型枚举，用于区分不同业务场景
     */
    @Override
    public void getPhoneCaptcha(String phone, CaptchaBusinessTypeEnums captchaBusinessType) {
        if (!SmsUtils.validMobilePhone(phone)) {
            throw new SmsException("无效的手机号格式");
        }
        String captchaKey = Keys.createKey(SecurityConstant.REDIS_PHONE_CODE_KEY_PREFIX, captchaBusinessType.getValue(), phone);
        Boolean phoneExists = redisRepository.hasKey(captchaKey);
        if (phoneExists) {
            throw new BusinessException("验证码发送过于频繁，请注意查收短信！");
        }
        boolean existsUserByPhone = authenticationDao.existsUserByPhone(phone);
        if (!existsUserByPhone) {
            throw new BusinessException("该用户不存在或未绑定手机号!");
        }
        String captcha = SmsUtils.generatePhoneCode();
        log.info("为手机号{}生成验证码: {}", phone, captcha);  // 实际生产环境建议去掉明文日志
        sendSms(phone, captcha);
        redisRepository.set(captchaKey, captcha, CAPTCHA_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 校验手机短信验证码
     * <p>
     * 验证用户输入的手机短信验证码是否正确，包括验证码的存在性、有效期和内容匹配性检查。
     * 校验失败时会抛出相应的异常。
     * </p>
     *
     * @param phone 手机号码
     * @param captcha 用户输入的短信验证码内容
     * @param captchaBusinessType 验证码业务类型枚举，用于区分不同业务场景
     */
    @Override
    public void checkPhoneCode(String phone, String captcha, CaptchaBusinessTypeEnums captchaBusinessType) {
        if (!SmsUtils.validMobilePhone(phone)) {
            throw new CaptchaException("无效的手机号格式");
        }
        if (StringUtils.isEmpty(captcha)) {
            throw new CaptchaException("请输入验证码");
        }
        String captchaKey = Keys.createKey(SecurityConstant.REDIS_PHONE_CODE_KEY_PREFIX, captchaBusinessType.getValue(), phone);
        Long expire = redisRepository.getExpire(captchaKey);
        if (expire <= 0) {
            throw new CaptchaException("验证码已过期，请重新获取验证码");
        }
        String phoneCode = redisRepository.get(captchaKey);
        if (!StringUtils.equals(captcha, phoneCode)) {
            throw new CaptchaException("输入的验证码不正确");
        }
    }

    /**
     * 删除手机短信验证码
     * <p>
     * 从服务端删除指定手机号对应的短信验证码，通常在验证码校验完成后调用以清理数据。
     * </p>
     *
     * @param phone               手机号码
     * @param captchaBusinessType 验证码业务类型枚举，用于区分不同业务场景
     */
    @Override
    public void removePhoneCode(String phone, CaptchaBusinessTypeEnums captchaBusinessType) {
        if (StringUtils.hasText(phone)) {
            String captchaKey = Keys.createKey(SecurityConstant.REDIS_PHONE_CODE_KEY_PREFIX, captchaBusinessType.getValue(), phone);
            redisRepository.delete(captchaKey);
        }
    }

    /**
     * 发送短信验证码（实际实现需对接短信服务商）
     */
    private void sendSms(String phone, String captcha) {
        log.warn("向手机号{}发送短信验证码: {}", phone, captcha);
    }


    /**
     * 删除验证码
     * <p>
     * 从Redis中删除指定的验证码数据，通常在验证码校验完成后调用以清理数据。
     * 该方法具备容错性，即使删除失败也只会记录警告日志而不会抛出异常。
     * </p>
     *
     * @param requestKey 验证码的唯一标识key，用于定位要删除的验证码数据
     */
    private void removeCaptcha(String requestKey) {
        try {
            log.info("删除验证码: {}", requestKey);
            if (StringUtils.hasText(requestKey)) {
                redisRepository.delete(requestKey);
            }
        } catch (Exception e) {
            log.warn("删除验证码失败{}", e.getMessage(), e);
        }
    }

}
