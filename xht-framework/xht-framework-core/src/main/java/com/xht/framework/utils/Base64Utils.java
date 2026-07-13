package com.xht.framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 编解码工具类
 * 提供普通Base64与URL安全型Base64的字符串、字节数组互转
 *
 * @author xht
 **/
@Slf4j
public final class Base64Utils {

    /**
     * 将字节数组进行Base64编码，返回编码后的字符串
     *
     * @param bytes 待编码字节数组
     * @return Base64编码字符串
     */
    public static String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 普通字符串UTF-8编码后再进行Base64加密
     *
     * @param str 原始明文字符串
     * @return Base64编码字符串，入参为null时返回null
     */
    public static String encodeStr(String str) {
        if (str == null) return null;
        return encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对Base64字符串解码为字节数组
     *
     * @param base64Str Base64编码字符串
     * @return 原始字节数组
     */
    public static byte[] decodeFromString(String base64Str) {
        return Base64.getDecoder().decode(base64Str);
    }

    /**
     * Base64字符串解码为UTF-8明文字符串
     *
     * @param base64Str Base64编码字符串
     * @return 原始明文字符串，入参为null时返回null
     */
    public static String decodeStr(String base64Str) {
        if (base64Str == null) return null;
        byte[] arr = decodeFromString(base64Str);
        return new String(arr, StandardCharsets.UTF_8);
    }

    /**
     * URL安全模式Base64编码（剔除+、/、=等特殊字符，适用于URL参数传递）
     *
     * @param bytes 待编码字节数组
     * @return URL安全的Base64字符串
     */
    public static String encodeToUrlSafeString(byte[] bytes) {
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    /**
     * 解码URL安全模式的Base64字符串为字节数组
     *
     * @param str URL安全的Base64字符串
     * @return 原始字节数组
     */
    public static byte[] decodeFromUrlSafeString(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

}