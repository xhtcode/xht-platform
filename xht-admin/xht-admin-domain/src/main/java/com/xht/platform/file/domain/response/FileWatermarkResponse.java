package com.xht.platform.file.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 文件水印信息
 *
 * @author xht
 **/
@Data
@Schema(description = "文件水印信息")
public class FileWatermarkResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否启用水印
     */
    @Schema(description = "是否启用水印")
    private boolean enabled;

    /**
     * 水印等级：off/l1/l2
     */
    @Schema(description = "水印等级")
    private String level;

    /**
     * 水印文案
     */
    @Schema(description = "水印文案")
    private String text;

    /**
     * 前端水印透明度
     */
    @Schema(description = "前端水印透明度")
    private Double opacity;

}
