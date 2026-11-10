package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件预览访问地址
 *
 * @author xht
 **/
@Data
@Schema(description = "文件预览访问地址")
public class FilePreviewUrlsResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预览内容地址
     */
    @Schema(description = "预览内容地址")
    private String previewUrl;

    /**
     * 下载地址
     */
    @Schema(description = "下载地址")
    private String downloadUrl;

    /**
     * 状态查询地址
     */
    @Schema(description = "状态查询地址")
    private String statusUrl;

}
