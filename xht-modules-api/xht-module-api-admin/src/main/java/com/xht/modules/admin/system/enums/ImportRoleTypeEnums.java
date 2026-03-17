package com.xht.modules.admin.system.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 导入角色类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ImportRoleTypeEnums implements XhtEnum<Integer> {

    NONE(0, "无角色"),

    BUSINESS(1, "商家用户"),

    USER(2, "普通用户角色");

    @JsonValue
    private final Integer value;

    private final String desc;

}
