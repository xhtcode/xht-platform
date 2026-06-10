package com.xht.framework.utils.crypto.enums;

import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 消息摘要算法枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DigestAlgorithm implements XhtEnum<String> {
    /**
     * MD5
     */
    MD5("MD5"),
    /**
     * SHA-1
     */
    SHA1("SHA-1"),
    /**
     * SHA-256
     */
    SHA256("SHA-256"),
    /**
     * SHA-384
     */
    SHA384("SHA-384"),
    /**
     * SHA-512
     */
    SHA512("SHA-512");

    private final String value;

}
