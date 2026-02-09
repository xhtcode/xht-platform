package com.xht.framework.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 登录类型枚举类
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum LoginTypeEnums implements XhtEnum<String>, Serializable {

    /**
     * 客户端模式
     */
    CLIENT_CREDENTIALS("client_credentials"),

    /**
     * 密码模式
     */
    PASSWORD("password"),

    /**
     * 短信验证码
     */
    PHONE("phone"),

    /**
     * 微信
     */
    WECHAT("wechat"),

    /**
     * QQ
     */
    QQ("qq"),
    ;

    /**
     * 登录类型编码
     */
    @JsonValue
    private final String value;

    /**
     * 根据登录类型编码获取登录类型枚举类
     *
     * @param code 登录类型编码
     * @return 登录类型枚举类 默认是验证码登录类型
     */
    public static LoginTypeEnums getByValue(String code) {
        for (LoginTypeEnums e : LoginTypeEnums.values()) {
            if (e.getValue().equals(code)) {
                return e;
            }
        }
        return LoginTypeEnums.PASSWORD;
    }
}
