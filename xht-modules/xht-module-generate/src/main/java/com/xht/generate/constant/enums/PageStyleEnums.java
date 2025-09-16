package com.xht.generate.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 页面风格枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum PageStyleEnums implements IEnum<Integer> {

    /**
     * 抽屉
     */
    DRAWER(0, "drawer"),

    /**
     * 对话框
     */
    DIALOG(1, "dialog"),
    ;
    /**
     * 页面风格
     */
    @JsonValue
    private final Integer value;

    private final String desc;

}
