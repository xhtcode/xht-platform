package com.xht.platform.file.domain.response;

import com.xht.framework.common.domain.response.MetaResponse;
import com.xht.platform.file.enums.PreviewTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 描述： 文件预览类型配置响应实体
 * <p>
 * 继承 {@link MetaResponse} 携带创建/更新时间与创建/更新用户等公共信息
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件预览类型配置响应实体")
public class SysFileTypeResponse extends MetaResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 预览类型编码
     */
    @Schema(description = "预览类型编码(image/pdf/video/audio/text/json/other)")
    private PreviewTypeEnum previewType;

    /**
     * 预览类型名称
     */
    @Schema(description = "预览类型名称(图片/PDF/视频/音频/文本/JSON/其他)")
    private String previewName;

    /**
     * 文件后缀
     */
    @Schema(description = "文件后缀，单个，不带点，如：jpg")
    private String fileSuffix;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

}
