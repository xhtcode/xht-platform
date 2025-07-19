package com.xht.system.modules.authority.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：菜单类型
 *
 * @author xht
 * @version : 1.0
 **/
@Getter
@AllArgsConstructor
public enum MenuTypeEnums implements IEnum<String> {

    M("M", "目录"),
    C("C", "菜单"),
    B("B", "按钮"),

    ALL("ALL", "全部");

    @JsonValue
    private final String value;

    private final String desc;
}