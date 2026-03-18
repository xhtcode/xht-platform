package com.xht.framework.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 用户类型枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum UserTypeEnums implements XhtEnum<Integer> {
    /**
     * 管理员用户
     */
    ADMIN(1, "管理员用户"),
    /**
     * 商家用户
     */
    BUSINESS(2, "商家用户"),
    /**
     * 普通用户
     */
    USER(3, "普通用户"),

    ;
    @JsonValue
    private final Integer value;

    private final String desc;

    /**
     * 根据值获取对应的用户类型枚举
     *
     * @param value 用户类型的值
     * @return 匹配的用户类型枚举，如果未找到则返回null
     */
    public static UserTypeEnums getByValue(Integer value) {
        // 遍历所有枚举值，查找与给定值匹配的枚举项
        for (UserTypeEnums item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 判断给定的用户类型枚举是否为管理员类型
     *
     * @param userType 用户类型枚举
     * @return 如果用户类型为管理员类型，则返回true；否则返回false
     */
    public static boolean isAdmin(UserTypeEnums userType) {
        return Objects.equals(userType, UserTypeEnums.ADMIN);
    }

}
