package com.xht.framework.security.core.device.provider;

import com.xht.framework.utils.IpUtils;
import com.xht.framework.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.StringJoiner;

/**
 * 设备码工具类
 *
 * @author xht
 * @param salt
业务盐
 */
@Slf4j
public record DeviceCodeProvider(String salt) {

    private static final String SHA256 = "SHA-256";

    /**
     * 生成带盐设备码
     * @param request HttpServletRequest 请求对象
     * @return 设备码
     */
    public String generateDeviceCode(HttpServletRequest request) {
        String feature = getDeviceFeature(request);
        String raw = feature + "|" + salt;
        return sha256(raw);
    }

    /**
     * 校验传入的deviceCode是否合法（防篡改核心方法）
     * @param request HttpServletRequest 请求对象
     * @param clientDeviceCode 客户端传入的设备码
     * @return 是否合法
     */
    public boolean verifyDeviceCode(HttpServletRequest request, String clientDeviceCode) {
        if (clientDeviceCode == null || clientDeviceCode.length() != 64) {
            return false;
        }
        String realCode = generateDeviceCode(request);
        return realCode.equals(clientDeviceCode);
    }

    /**
     * 拼接设备特征
     * @param request HttpServletRequest 请求对象
     * @return 设备特征
     */
    private String getDeviceFeature(HttpServletRequest request) {
        StringJoiner joiner = new StringJoiner("|");
        joiner.add(ServletUtil.getHeader(request, "OAID"));
        joiner.add(ServletUtil.getHeader(request, "IDFA"));
        joiner.add(ServletUtil.getHeader(request, "User-Agent"));
        joiner.add(IpUtils.getClientIP(request));
        joiner.add(ServletUtil.getHeader(request, "X-Device-Screen"));
        return joiner.toString();
    }

    /**
     * SHA256摘要
     * @param content 待摘要内容
     * @return 摘要结果
     */
    private String sha256(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance(SHA256);
            byte[] arr = md.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : arr) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) sb.append("0");
                sb.append(hex);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}