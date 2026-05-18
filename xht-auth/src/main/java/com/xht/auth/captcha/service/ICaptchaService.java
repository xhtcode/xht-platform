package com.xht.auth.captcha.service;

import com.xht.auth.captcha.enums.CaptchaBusinessTypeEnums;
import com.xht.framework.core.security.response.CaptchaResponse;

/**
 * 验证码
 *
 * @author xht
 **/
public interface ICaptchaService {

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
        CaptchaResponse generateCaptcha(String captchaKey, CaptchaBusinessTypeEnums captchaBusinessType);

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
        void checkCaptcha(String requestKey, String requestCaptcha, CaptchaBusinessTypeEnums captchaBusinessType);

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
        void getPhoneCaptcha(String phone, CaptchaBusinessTypeEnums captchaBusinessType);

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
        void checkPhoneCode(String phone, String captcha, CaptchaBusinessTypeEnums captchaBusinessType);

}
