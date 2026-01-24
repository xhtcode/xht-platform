package com.xht.modules.admin.notice.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ： 通知权限类型枚举 0:角色权限;1:用户权限;2:部门权限
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum NoticePermTypeEnums implements IEnum<Integer> {

    ROLE_PERM(0, "角色权限"),
    USER_PERM(1, "用户权限"),
    DEPT_PERM(2, "部门权限")
    ;
    @JsonValue
    private final Integer value;
    private final String desc;

}
