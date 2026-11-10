package com.xht.platform.file.domain.response;

import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.FileInitStatusEnum;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述： 文件预览初始化信息
 * <p>
 * 前端打开预览前调用初始化接口获取：文件基础信息与预览/下载地址；是否可内联渲染由前端按后缀判断
 *
 * @author xht
 **/
@Data
@Schema(description = "文件预览初始化信息")
public class SysFilePreviewResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private FileInitStatusEnum initStatus;

    /**
     * 文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）
     */
    @Schema(description = "文件完整原始名称（含后缀）")
    private String fileName;

    /**
     * 文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）
     */
    @Schema(description = "文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）")
    private String fileOriginalName;

    /**
     * 文件大小（单位：字节）
     */
    @Schema(description = "文件大小（单位：字节）")
    private Long fileSize;

    /**
     * 文件大小信息
     */
    @Schema(description = "文件大小信息")
    private String fileSizeInfo;

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String fileSuffix;

    /**
     * 文件 MIME 类型
     */
    @Schema(description = "文件 MIME 类型")
    private String fileType;

    /**
     * 访问范围
     */
    @Schema(description = "访问范围")
    private FileAccessScopeEnum accessScope;

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
     * 访问地址
     */
    @Schema(description = "访问地址")
    private FilePreviewUrlsResponse urls;

    /**
     * 预览ID
     */
    @Schema(description = "预览ID")
    private String previewId;

    /**
     * 会话过期时间
     */
    @Schema(description = "会话过期时间")
    private LocalDateTime expireAt;


}
