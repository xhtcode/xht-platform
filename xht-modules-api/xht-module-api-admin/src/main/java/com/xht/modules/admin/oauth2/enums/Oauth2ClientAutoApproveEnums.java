package com.xht.modules.admin.oauth2.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum Oauth2ClientAutoApproveEnums implements IEnum<Integer> {

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
}
