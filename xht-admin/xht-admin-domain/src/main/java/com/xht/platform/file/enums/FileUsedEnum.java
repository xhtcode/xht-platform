package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import com.xht.framework.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件用途枚举
 * <p>
 * 用于约束文件上传的用途标识（fileUsed），区分不同业务模块的文件；
 * 上传时未传默认 temp（临时文件），传非法值时由调用方拒绝
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileUsedEnum implements XhtEnum<String> {

    /**
     * 临时文件（默认，上传后未关联业务）
     */
    TEMP("temp", "临时文件"),

    /**
     * 头像
     */
    AVATAR("avatar", "头像"),

    /**
     * 通知附件
     */
    NOTICE("notice", "通知附件"),

    /**
     * 合同附件
     */
    CONTRACT("contract", "合同附件"),

    /**
     * 文档资料
     */
    DOCUMENT("document", "文档资料"),

    /**
     * 其他
     */
    OTHER("other", "其他"),
    ;

    /**
     * 用途标识（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 用途描述
     */
    private final String desc;

    /**
     * 判断用途标识是否被支持
     *
     * @param value 用途标识
     * @return true：支持
     */
    public static boolean isSupported(String value) {
        return find(value) != null;
    }

    /**
     * 归一化用途标识：null/空白 → temp；非法值 → null（由调用方抛参数错误）
     *
     * @param value 用途标识
     * @return 归一化后的用途标识（忽略大小写），非法值返回 null
     */
    public static String normalize(String value) {
        if (!StringUtils.hasText(value)) {
            return TEMP.value;
        }
        FileUsedEnum found = find(value.strip());
        return found == null ? null : found.value;
    }

    /**
     * 根据标识查找枚举（忽略大小写）
     *
     * @param value 用途标识
     * @return 匹配的枚举，未找到返回 null
     */
    private static FileUsedEnum find(String value) {
        for (FileUsedEnum fileUsedEnum : values()) {
            if (fileUsedEnum.value.equalsIgnoreCase(value)) {
                return fileUsedEnum;
            }
        }
        return null;
    }

}
