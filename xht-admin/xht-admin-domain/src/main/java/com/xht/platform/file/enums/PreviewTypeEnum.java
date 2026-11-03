package com.xht.platform.file.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.xht.framework.common.enums.XhtEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： 文件预览类型枚举
 * <p>
 * 与 sys_file_type.preview_type 列对应，固定七种编码：image/pdf/video/audio/text/json/other；
 * 入库与对外返回均使用编码值，非法编码由入参校验拒绝
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor
public enum PreviewTypeEnum implements XhtEnum<String> {

    /**
     * 图片
     */
    IMAGE("image", "图片"),

    /**
     * PDF
     */
    PDF("pdf", "PDF"),

    /**
     * 视频
     */
    VIDEO("video", "视频"),

    /**
     * 音频
     */
    AUDIO("audio", "音频"),

    /**
     * 文本
     */
    TEXT("text", "文本"),

    /**
     * JSON
     */
    JSON("json", "JSON"),

    /**
     * 其他
     */
    OTHER("other", "其他"),
    ;

    /**
     * 预览类型编码（入库值）
     */
    @JsonValue
    private final String value;

    /**
     * 预览类型名称
     */
    private final String desc;
}
