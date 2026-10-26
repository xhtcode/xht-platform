package com.xht.framework.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * URL 编解码工具类
 * <p>
 * 注意区分：
 * 1. url参数编码（query参数，?a=b）：空格编码为 +，使用 {@link URLEncoder#encode(String, String)}
 * 2. url路径编码（/path/xxx）：空格编码为 %20，不能直接使用URLEncoder
 *
 * @author xht
 */
public abstract class URLCodecUtil {


    /**
     * URL Query 参数 UTF-8 编码
     * <p>适用场景：url问号后面的参数值，空格会编码成 +</p>
     *
     * @param value 原始字符串，可为 null
     * @return 编码后的字符串，入参null返回null
     */
    public static String encodeUtf8(String value) {
        if (value == null) {
            return null;
        }
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * URL Query 参数 UTF-8 解码
     *
     * @param value 编码后的字符串，可为 null
     * @return 解码后的字符串，入参null返回null
     */
    public static String decodeUtf8(String value) {
        if (value == null) {
            return null;
        }
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    // ====================== URL 路径部分编码（/path/xxx 部分，空格转为%20） ======================

    /**
     * URL 路径 UTF-8 编码
     * <p>适用场景：url路径段，空格编码为 %20，不使用 +</p>
     * <p>示例：/user/测试 name → /user/%E6%B5%8B%E8%AF%95%20name</p>
     *
     * @param value 原始路径片段，可为 null
     * @return 编码后的路径片段，入参null返回null
     */
    public static String encodePath(String value) {
        if (value == null) {
            return null;
        }
        // 先用URLEncoder编码，再把 + 替换成 %20
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }

    /**
     * URL 路径解码
     * 路径和query解码逻辑一致，URLDecoder可以自动识别 %20 和 +
     *
     * @param value 编码后的路径片段，可为 null
     * @return 解码后的字符串
     */
    public static String decodePath(String value) {
        return decodeUtf8(value);
    }

}
