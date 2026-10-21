package com.xht.platform.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * 描述： 文件上传配置
 * <p>
 * 约束上传文件的大小与类型白名单；白名单不包含 html/svg/js 等可执行主动内容类型，
 * 缩小同源 XSS 攻击面
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties("xht.file.upload")
public class FileUploadProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单文件最大大小（服务端权威校验）
     */
    @DataSizeUnit(DataUnit.MEGABYTES)
    private DataSize maxSize = DataSize.ofMegabytes(50);

    /**
     * 允许上传的文件后缀白名单（不含点，忽略大小写）
     */
    private List<String> allowedSuffixes = List.of(
            "jpg", "jpeg", "png", "gif", "bmp", "webp",
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "txt", "csv", "md", "zip", "rar", "7z",
            "mp3", "wav", "mp4", "mov", "avi"
    );

}
