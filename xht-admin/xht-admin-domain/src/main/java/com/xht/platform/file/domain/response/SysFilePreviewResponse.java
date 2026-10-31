package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件预览初始化信息
 * <p>
 * 前端打开预览前调用初始化接口获取：文件元数据、是否支持在线预览，以及预览/下载地址
 *
 * @author xht
 **/
@Data
@Schema(description = "文件预览初始化信息")
public class SysFilePreviewResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @Schema(description = "文件ID")
    private Long fileId;

    /**
     * 文件原始名称
     */
    @Schema(description = "文件原始名称")
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
     * 文件类型
     */
    @Schema(description = "文件类型")
    private String fileType;

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀")
    private String fileSuffix;

    /**
     * 文件用途
     */
    @Schema(description = "文件用途")
    private String fileUsed;

    /**
     * 是否支持在线预览，false 时前端应引导下载查看
     */
    @Schema(description = "是否支持在线预览")
    private Boolean previewSupported;

    /**
     * 预览地址（相对服务根路径，支持 Range 分段请求）
     */
    @Schema(description = "预览地址（相对服务根路径，支持 Range 分段请求）")
    private String previewUrl;

    /**
     * 下载地址（相对服务根路径）
     */
    @Schema(description = "下载地址（相对服务根路径）")
    private String downloadUrl;

}
