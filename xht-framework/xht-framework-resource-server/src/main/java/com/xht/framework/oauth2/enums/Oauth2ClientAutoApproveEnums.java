package com.xht.framework.oauth2.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 client 自动授权枚举值
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum Oauth2ClientAutoApproveEnums implements XhtEnum<Integer> {

    /**
     * 正常
     */
    YES(0),

    /**
     * 停用
     */
    NO(1);

    @JsonValue
    private final Integer value;

    public static Oauth2ClientAutoApproveEnums of(Integer code) {
        for (Oauth2ClientAutoApproveEnums value : values()) {
            if (value.value.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
