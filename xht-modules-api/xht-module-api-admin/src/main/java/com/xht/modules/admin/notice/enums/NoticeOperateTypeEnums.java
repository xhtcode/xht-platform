package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知操作类型(1:阅读;2:点击)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeOperateTypeEnums implements IEnum<Integer> {

    READ(1, "阅读"),
    CLICK(2, "点击");

    @JsonValue
    private final Integer value;
    private final String desc;

}
