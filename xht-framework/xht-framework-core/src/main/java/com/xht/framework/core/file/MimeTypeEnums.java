package com.xht.framework.core.file;

import cn.hutool.core.util.IdUtil;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.core.utils.file.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述 ：文件类型常量
 *
 * @author xht
 **/
@AllArgsConstructor
@SuppressWarnings("all")
public enum MimeTypeEnums {

    /**
     * 通用的 MIME 类型
     */
    DEFAULT_("", "application/octet-stream"),

    /**
     * 文本文件
     */
    TXT("txt", "text/plain"),

    /**
     * Microsoft Word文档（.doc）
     */
    DOC("doc", "application/msword"),

    /**
     * Microsoft Word文档（.docx）
     */
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

    /**
     * Adobe PDF文档
     */
    PDF("pdf", "application/pdf"),

    /**
     * Microsoft Excel工作簿（.xls）
     */
    XLS("xls", "application/vnd.ms-excel"),

    /**
     * Microsoft Excel工作簿（.xlsx）
     */
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    /**
     * Microsoft Excel工作簿（非标准扩展名）
     */
    EXCEL("xls", "application/vnd.ms-excel"),

    /**
     * MP3音频
     */
    MP3("mp3", "audio/mpeg"),

    /**
     * WAV音频
     */
    WAV("wav", "audio/wav"),

    /**
     * MP4视频
     */
    MP4("mp4", "video/mp4"),

    /**
     * AVI视频
     */
    AVI("avi", "video/x-msvideo"),

    /**
     * ZIP压缩文件
     */
    ZIP("zip", "application/zip"),

    /**
     * CSV数据文件
     */
    CSV("csv", "text/csv"),

    /**
     * XML数据文件
     */
    XML("xml", "application/xml"),

    /**
     * JSON数据文件
     */
    JSON("json", "application/json"),

    /**
     * HTML网页文件
     */
    HTML("html", "text/html"),

    /**
     * CSS样式表
     */
    CSS("css", "text/css"),

    /**
     * JavaScript脚本
     */
    JS("js", "application/javascript"),

    /**
     * Python脚本
     */
    PY("py", "text/x-python"),

    /**
     * Java源代码
     */
    JAVA("java", "text/x-java-source"),

    /**
     * Markdown文档
     */
    MD("md", "text/markdown"),

    /**
     * EPUB电子书
     */
    EPUB("epub", "application/epub+zip"),

    /**
     * SVG矢量图像
     */
    SVG("svg", "image/svg+xml"),

    /**
     * JPEG图像
     */
    JPG("jpg", "image/jpeg"),

    /**
     * PNG图像
     */
    PNG("png", "image/png"),

    /**
     * GIF图像
     */
    GIF("gif", "image/gif"),
    ;

    /**
     * 文件类型 文件后缀
     */
    @Getter
    private final String fileSuffix;

    /**
     * 文件 MIME 类型
     */
    @Getter
    private final String contentType;

    /**
     * 根据文件后缀获取对应的MIME类型枚举
     *
     * @param fileSuffix 文件后缀名
     * @return 匹配的MIME类型枚举，如果未找到匹配项则返回默认值
     */
    public static MimeTypeEnums getByFileSuffix(String fileSuffix) {
        // 如果文件后缀为空，则返回默认值
        if (StringUtils.hasText(fileSuffix)) {
            return DEFAULT_;
        }
        // 遍历所有枚举值，查找匹配的文件后缀
        for (MimeTypeEnums value : values()) {
            if (StringUtils.equalsIgnoreCase(fileSuffix, value.getFileSuffix())) {
                return value;
            }
        }
        // 未找到匹配项，返回默认值
        return DEFAULT_;
    }

    /**
     * @return 生成的文件名称
     */
    public String generateName() {
        return generateName(IdUtil.objectId());
    }

    /**
     * @param fileNamePrefix 文件名称前缀
     * @return 生成的文件名称
     */
    public String generateName(String fileNamePrefix) {
        return FileUtils.generateFileName(fileNamePrefix, this.getFileSuffix());
    }

}
