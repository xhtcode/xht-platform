package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

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
     * 文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）
     */
    @Schema(description = "文件完整原始名称（含后缀）")
    private String fileName;

    /**
     * 文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）
     */
    @Schema(description = "文件完整原始名称（含后缀，优先取元数据，不受入库截断影响）")
    private String fullOriginalFileName;

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

}
