package com.xht.generate.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生成状态枚举
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum GenerateStatusEnums implements IEnum<String> {
    /**
     * 成功
     */
    SUCCESS("success"),
    /**
     * 失败
     */
    FAIL("fail");

    @JsonValue
    private final String value;

}