package com.xht.auth.security.web.authentication.qr.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二维码状态 0:待扫描，1:已扫描，2:已确认
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum QrCodeStatusEnums implements XhtEnum<Integer> {
    /**
     * 待扫描
     */
    WAIT_SCAN(0, "待扫描"),

    /**
     * 已扫描
     */
    SCANNED(1, "已扫描"),

    /**
     * 已确认
     */
    CONFIRMED(2, "已确认");

    /**
     * 状态码
     */
    @JsonValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 根据code获取枚举
     * @param value  二维码状态码
     */
    public static QrCodeStatusEnums getByValue(Integer value) {
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
