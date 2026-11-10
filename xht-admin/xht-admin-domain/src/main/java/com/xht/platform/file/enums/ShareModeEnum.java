package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件分享模式
 * <p>LINK：链接分享，允许匿名访问；USER：指定用户分享，必须登录并校验接收人</p>
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum ShareModeEnum implements XhtEnum<String> {

    /**
     * 链接分享
     */
    LINK("LINK", "链接分享"),

    /**
     * 指定用户分享
     */
    USER("USER", "指定用户分享"),
    ;

    /**
     * 模式编码（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 模式名称
     */
    private final String desc;

    /**
     * jackson 反序列化
     *
     * @param value 模式编码
     * @return 枚举，未匹配返回 null
     */
    @JsonCreator
    public static ShareModeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (ShareModeEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
