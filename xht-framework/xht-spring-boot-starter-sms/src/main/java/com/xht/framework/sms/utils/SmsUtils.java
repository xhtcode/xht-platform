package com.xht.framework.sms.utils;

import cn.hutool.core.util.ReUtil;
import com.xht.framework.core.constant.PatternConstant;
import com.xht.framework.core.exception.UtilException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 描述 ：短信验证码工具类
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("all")
public final class SmsUtils {

    private SmsUtils() {
        throw new UtilException("禁止实例化工具类");
    }

    /**
     * 生成6位随机手机号验证码
     *
     * @return 手机号
     */
    public static String generatePhoneCode() {
        // 计算随机数范围：例如6位的范围是 100000 ~ 999999
        long min = (long) Math.pow(10, 6 - 1);
        long max = (long) Math.pow(10, 6) - 1;
        // 使用ThreadLocalRandom生成随机数（线程安全，比Random性能好）
        long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);
        // 格式化为指定长度的字符串（防止极端情况位数不足）
        return String.format("%0" + 6 + "d", randomNum);
    }


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
