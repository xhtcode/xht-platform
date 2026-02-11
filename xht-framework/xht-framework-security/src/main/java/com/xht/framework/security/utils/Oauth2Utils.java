package com.xht.framework.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Base64;

/**
 * Oauth2Utils
 *
 * @author xht
 */
public final class Oauth2Utils {

    /**
     * Bearer token 前缀格式
     */
    private final static String BEARER_PREFIX = "Bearer %s";

    /**
     * Basic 认证前缀格式
     */
    private final static String BASIC_PREFIX = "Basic %s";

    /**
     * Base64 编码器
     */
    private final static Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    /**
     * 组装 Basic 认证头，使用 BCrypt 加密客户端密码
     *
     * @param clientId     客户端标识
     * @param clientSecret 客户端密钥
     * @param encoder      BCrypt密码编码器
     * @return 格式化的 Basic 认证头字符串
     */
    public static String assembleBasicBCryptAuthorization(String clientId, String clientSecret, BCryptPasswordEncoder encoder) {
        return assembleBasicAuthorization(clientId, encoder.encode(clientSecret));
    }

    /**
     * 组装 Basic 认证头
     *
     * @param clientId     客户端标识
     * @param clientSecret 客户端密钥
     * @return 格式化的 Basic 认证头字符串
     */
    public static String assembleBasicAuthorization(String clientId, String clientSecret) {
        String basic = clientId + ":" + clientSecret;
        return String.format(BASIC_PREFIX, BASE64_ENCODER.encodeToString(basic.getBytes()));
    }

    /**
     * 组装 Bearer 认证头
     *
     * @param token 认证token
     * @return 格式化的 Bearer 认证头字符串
     */
    public static String assembleBearerAuthorization(String token) {
        return String.format(BEARER_PREFIX, token);
    }

}
