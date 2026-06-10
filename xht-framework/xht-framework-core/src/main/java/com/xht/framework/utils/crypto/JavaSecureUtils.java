package com.xht.framework.utils.crypto;


import com.xht.framework.utils.crypto.enums.DigestAlgorithm;
import com.xht.framework.utils.crypto.exception.CryptoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

/**
 * 描述：安全相关工具类
 *
 * @author xht
 **/
public final class JavaSecureUtils {

    /**
     * 创建{@link MessageDigest}，使用JDK默认的Provider<br>
     *
     * @param algorithm 算法枚举
     * @return {@link MessageDigest}
     */
    public static MessageDigest createJdkMessageDigest(final DigestAlgorithm algorithm) {
        if (algorithm == null) {
            throw new IllegalArgumentException("null algorithm name");
        }
        return createJdkMessageDigest(algorithm, null);
    }

    /**
     * 创建{@link MessageDigest}，使用指定的Provider
     *
     * @param algorithm 算法枚举
     * @param provider  算法提供者
     * @return {@link MessageDigest}
     */
    public static MessageDigest createJdkMessageDigest(final DigestAlgorithm algorithm, Provider provider) {
        try {
            if (null == provider) {
                return MessageDigest.getInstance(algorithm.getValue());
            }
            return MessageDigest.getInstance(algorithm.getValue(), provider);
        } catch (final NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }
}
