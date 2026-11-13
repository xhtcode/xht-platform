package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件能力清单
 *
 * @author xht
 **/
@Data
@Schema(description = "文件能力清单")
public class FileCapabilityResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 允许预览
     */
    @Schema(description = "允许预览")
    private boolean preview;

    /**
     * 允许下载
     */
    @Schema(description = "允许下载")
    private boolean download;

}
