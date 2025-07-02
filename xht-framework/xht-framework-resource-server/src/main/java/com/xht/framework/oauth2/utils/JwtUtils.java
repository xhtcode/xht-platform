package com.xht.framework.oauth2.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * jwt 工具类
 *
 * @author xht
 **/
public final class JwtUtils {

    public static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
