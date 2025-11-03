package com.xht.framework.sms.utils;

import cn.hutool.core.util.ReUtil;
import com.xht.framework.core.constant.PatternConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述 ：短信验证码工具类
 *
 * @author xht
 **/
@Slf4j
public final class SmsUtils {


    /**
     * 验证手机号码格式是否正确
     *
     * @param phone 待验证的手机号码字符串
     * @return true表示手机号码格式正确，false表示格式错误
     */
    public static boolean validMobilePhone(String phone) {
        return ReUtil.isMatch(PatternConstant.MOBILE_PHONE, phone);
    }

}
