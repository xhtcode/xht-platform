package com.xht.framework.security.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.utils.StringUtils;
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
public enum LoginTypeEnums implements IEnum<String>, Serializable {

    //验证码
    PASSWORD("password"),

    //短信验证码
    SMS_CODE("sms_code"),

    //微信
    WECHAT("wechat"),

    //QQ
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
    @JsonCreator
    public static LoginTypeEnums of(String code) {
        if (StringUtils.isEmpty(code)) {
            return LoginTypeEnums.PASSWORD;
        }
        for (LoginTypeEnums e : LoginTypeEnums.values()) {
            if (e.getValue().equals(code)) {
                return e;
            }
        }
        return LoginTypeEnums.PASSWORD;
    }
}
