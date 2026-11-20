package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

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

}
