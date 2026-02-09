package com.xht.modules.admin.system.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.core.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门岗位状态枚举
 *
 * @author xht
 */
@Getter
@AllArgsConstructor
public enum DeptPostStatusEnums implements XhtEnum<Integer> {
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