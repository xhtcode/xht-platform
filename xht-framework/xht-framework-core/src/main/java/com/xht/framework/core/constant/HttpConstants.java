package com.xht.framework.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http 常用常量
 *
 * @author xht
 **/
public final class HttpConstants {

    /**
     * 字符集
     */
    @Getter
    @AllArgsConstructor
    public enum Character {

        UTF8("UTF-8", "UTF-8"),
        GBK("GBK", "GBK");

        private final String code;
        private final String info;

    }
}
