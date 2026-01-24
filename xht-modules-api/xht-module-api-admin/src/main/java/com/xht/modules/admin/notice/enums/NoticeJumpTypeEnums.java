package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 跳转类型(0:无跳转;1:内部页面;2:外部链接)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeJumpTypeEnums implements IEnum<Integer> {
    NO_JUMP(0, "无跳转"),
    INNER_PAGE(1, "内部页面"),
    OUTER_LINK(2, "外部链接");

    @JsonValue
    private final Integer value;

    private final String desc;

}
