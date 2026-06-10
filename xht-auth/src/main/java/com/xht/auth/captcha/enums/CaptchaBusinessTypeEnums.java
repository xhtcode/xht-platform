package com.xht.auth.captcha.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码业务类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum CaptchaBusinessTypeEnums implements XhtEnum<String> {

    SSO("sso"),

    OAUTH2("oauth2");

    @JsonValue
    private final String value;

    /**
     * 根据value获取枚举
     *
     * @param value 值
     * @return 枚举{@link CaptchaBusinessTypeEnums}
     */
    public static CaptchaBusinessTypeEnums getByValue(String value) {
        for (CaptchaBusinessTypeEnums item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

}
