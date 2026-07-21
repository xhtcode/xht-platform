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
public enum CaptchaBusinessTypeEnum implements XhtEnum<String> {

    SSO("sso"),

    OAUTH2("oauth2");

    @JsonValue
    private final String value;

    /**
     * 根据value获取枚举
     *
     * @param value 值
     * @return 枚举{@link CaptchaBusinessTypeEnum}
     */
    public static CaptchaBusinessTypeEnum getByValue(String value) {
        for (CaptchaBusinessTypeEnum item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

}
