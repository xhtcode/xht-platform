package com.xht.modules.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门状态枚举
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum DeptStatusEnums implements IEnum<Integer> {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 停用
     */
    DISABLE(1, "停用");

    @JsonValue
    private final Integer value;

    private final String desc;

}