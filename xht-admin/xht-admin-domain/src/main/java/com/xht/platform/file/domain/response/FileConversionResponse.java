package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件转换状态
 *
 * @author xht
 **/
@Data
@Schema(description = "文件转换状态")
public class FileConversionResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否需要转换
     */
    @Schema(description = "是否需要转换")
    private boolean required;

    /**
     * 转换状态：NONE/PENDING/PROCESSING/SUCCESS/FAILED
     */
    @Schema(description = "转换状态")
    private String status;

    /**
     * 粗粒度进度：0/50/100
     */
    @Schema(description = "粗粒度进度")
    private Integer progress;

    /**
     * 提示信息
     */
    @Schema(description = "提示信息")
    private String message;

    /**
     * 建议重试间隔（毫秒）
     */
    @Schema(description = "建议重试间隔（毫秒）")
    private Long retryAfterMs;

}
