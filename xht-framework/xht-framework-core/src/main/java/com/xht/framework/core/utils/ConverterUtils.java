package com.xht.framework.core.utils;

import com.xht.framework.core.exception.UtilException;

/**
 * 转换工具类
 *
 * @author xht
 **/
public final class ConverterUtils {

    private ConverterUtils() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 将字符串转换为Long类型数值
     *
     * @param number 待转换的字符串数值
     * @return 转换后的Long类型数值，如果转换失败则返回默认值0L
     */
    public static Long strToLong(String number) {
        return strToLong(number, null);
    }


    /**
     * 将字符串转换为长整型数值
     *
     * @param number       待转换的字符串，如果为空或空白字符则返回默认值
     * @param defaultValue 转换失败或字符串为空时返回的默认值
     * @return 转换后的长整型数值，如果转换失败则返回默认值
     */
    public static Long strToLong(String number, Long defaultValue) {
        if (StringUtils.isEmpty(number)) {
            return defaultValue;
        }
        return Long.parseLong(number);
    }


}
