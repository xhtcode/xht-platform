package com.xht.platform.dict.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum DictStatusEnum implements XhtEnum<Integer> {

    /**
     * 启用
     */
    ENABLE(1),

    /**
     * 禁用
     */
    DISABLE(2),
    ;

    /**
     * 字典状态
     */
    @JsonValue
    private final Integer value;

}
