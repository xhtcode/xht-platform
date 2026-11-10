package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.FileInitStatusEnum;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 文件预览状态
 *
 * @author xht
 **/
@Data
@Schema(description = "文件预览状态")
public class SysFilePreviewStatusResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预览ID
     */
    @Schema(description = "预览ID")
    private String previewId;

    /**
     * 初始化状态
     */
    @Schema(description = "初始化状态")
    private FileInitStatusEnum initStatus;

    /**
     * 预览模式
     */
    @Schema(description = "预览模式")
    private PreviewTypeEnum previewMode;

    /**
     * 预览来源
     */
    @Schema(description = "预览来源")
    private PreviewSourceEnum previewSource;

    /**
     * 能力清单
     */
    @Schema(description = "能力清单")
    private FileCapabilityResponse capabilities;

    /**
     * 转换状态
     */
    @Schema(description = "转换状态")
    private FileConversionResponse conversion;

    /**
     * 水印信息
     */
    @Schema(description = "水印信息")
    private FileWatermarkResponse watermark;

    /**
     * 会话过期时间
     */
    @Schema(description = "会话过期时间")
    private LocalDateTime expireAt;

}
