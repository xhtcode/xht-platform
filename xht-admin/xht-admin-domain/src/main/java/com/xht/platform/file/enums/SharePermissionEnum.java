package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件分享权限
 * <p>VIEW：仅查看；VIEW_DOWNLOAD：查看 + 下载；DOWNLOAD：仅下载，不生成预览内容</p>
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum SharePermissionEnum implements XhtEnum<String> {

    /**
     * 仅查看
     */
    VIEW("VIEW", "仅查看", true, false, false),

    /**
     * 查看 + 下载
     */
    VIEW_DOWNLOAD("VIEW_DOWNLOAD", "查看+下载", true, true, true),

    /**
     * 仅下载
     */
    DOWNLOAD("DOWNLOAD", "仅下载", false, true, false),
    ;

    /**
     * 权限编码（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 权限名称
     */
    private final String desc;

    /**
     * 是否允许预览
     */
    private final boolean preview;

    /**
     * 是否允许下载
     */
    private final boolean download;

    /**
     * 是否允许打印
     */
    private final boolean print;

    /**
     * jackson 反序列化
     *
     * @param value 权限编码
     * @return 枚举，未匹配返回 null
     */
    @JsonCreator
    public static SharePermissionEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (SharePermissionEnum e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

}
