package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件初始化状态枚举
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum FileInitStatusEnum implements XhtEnum<Integer> {

    /**
     * SUCCESS：文件预览请求处理成功
     */
    SUCCESS(0, "文件预览请求处理成功"),
    /**
     * CONVERTING：文件正在转换中
     */
    CONVERTING(1, "文件正在转换中"),
    /**
     * NOT_SUPPORTED：不支持的文件类型
     */
    NOT_SUPPORTED(2, "不支持的文件类型"),

    /**
     * FAILED：文件转换失败
     */
    FAILED(3, "文件转换失败"),

    /**
     * DOWNLOAD_ONLY：仅支持下载，无在线预览
     */
    DOWNLOAD_ONLY(4, "仅支持下载");

    /**
     * 状态值
     */
    @JsonValue
    private final Integer value;

    /**
     * 状态描述
     */
    private final String desc;

}
