package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否置顶(0:否;1:是)
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticeTopEnums implements IEnum<Integer> {
    NO(0, "否"),
    YES(1, "是");
    @JsonValue
    private final Integer value;
    private final String desc;
}
