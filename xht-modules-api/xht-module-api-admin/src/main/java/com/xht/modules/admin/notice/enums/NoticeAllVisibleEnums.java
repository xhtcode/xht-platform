package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否全部可见(0:否(指定范围);1:是(所有用户可见))
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeAllVisibleEnums implements IEnum<Integer> {
    NO(0, "否"),
    YES(1, "是");
    @JsonValue
    private final Integer value;
    private final String desc;
}
