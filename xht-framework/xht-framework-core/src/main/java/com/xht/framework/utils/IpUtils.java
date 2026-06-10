package com.xht.framework.utils;

import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.UtilException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * IP工具类
 *
 * @author xht
 */
public final class IpUtils {
    /**
     * 多个IP时，取第一个非unknown的IP
     */
    private final static String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    /**
     * 0:0:0:0:0:0:0:1
     */
    private final static String LOCALHOST = "0:0:0:0:0:0:0:1";

    private IpUtils() {
        throw new UtilException("This is a utility class and cannot be instantiated");
    }


    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request) {
        if (request == null) {
            return StringConstant.UNKNOWN;
        }
        String ip;
        for (String item : headers) {
            ip = request.getHeader(item);
            if (isUnknown(ip)) {
                return getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }


    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        if (StringUtils.equals(LOCALHOST, ip)) {
            return StringConstant.LOCALHOST;
        }
        // 多级反向代理检测
        if (ip != null && ip.indexOf(StringConstant.COMMA) > 0) {
            final String[] ips = ip.trim().split(StringConstant.COMMA);
            for (String subIp : ips) {
                if (isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return StringUtils.substring(ip, 0, 255);
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    private static boolean isUnknown(String checkString) {
        return !StringUtils.isEmpty(checkString) && !StringConstant.UNKNOWN.equalsIgnoreCase(checkString);
    }


}