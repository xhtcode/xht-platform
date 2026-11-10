package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件访问范围枚举
 * <p>与 sys_file.access_scope 对应：private 私有、public 公开；
 * 公开仅允许管理员设置，且文件用途必须允许公开</p>
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileAccessScopeEnum implements XhtEnum<String> {

    /**
     * 私有：需要登录并通过归属/业务授权
     */
    PRIVATE("private", "私有"),

    /**
     * 公开：允许匿名访问
     */
    PUBLIC("public", "公开"),
    ;

    /**
     * 范围编码（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 范围名称
     */
    private final String desc;

    /**
     * jackson 反序列化
     *
     * @param value 范围编码
     * @return 枚举，未匹配返回 null
     */
    @JsonCreator
    public static FileAccessScopeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (FileAccessScopeEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
