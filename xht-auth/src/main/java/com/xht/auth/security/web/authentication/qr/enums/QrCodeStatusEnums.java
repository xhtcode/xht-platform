package com.xht.auth.security.web.authentication.qr.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二维码状态 0:待扫描，1:已扫描，2:已确认
 * 二维码状态类型
 * waiting: 等待用户扫码
 * scanned: 用户已扫码
 * confirmed: 用户授权成功
 * expired: 二维码已过期
 export type QrCodeStatusType = 'waiting' | 'scanned' | 'confirmed' | 'expired'
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum QrCodeStatusEnums implements XhtEnum<String> {

    /**
     * 等待用户扫码
     */
    WAITING("waiting", "等待用户扫码"),

    /**
     * 用户已扫码
     */
    SCANNED("scanned", "用户已扫码"),

    /**
     * 用户授权成功
     */
    CONFIRMED("confirmed", "用户授权成功"),

    /**
     * 二维码已过期
     */
    EXPIRED("expired", "二维码已过期"),


    ;

    /**
     * 状态码
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据code获取枚举
     * @param value  二维码状态码
     */
    public static QrCodeStatusEnums getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (QrCodeStatusEnums status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
