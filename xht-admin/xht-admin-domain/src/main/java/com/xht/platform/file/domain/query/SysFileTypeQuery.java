package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import com.xht.platform.file.enums.PreviewTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 描述： 文件预览类型配置分页查询参数
 * <p>
 * 未传参的字段不参与过滤（动态条件）：预览类型编码、文件后缀精确匹配，预览类型名称模糊匹配
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件预览类型配置分页查询参数")
public class SysFileTypeQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 预览类型编码（精确查询）
     */
    @Schema(description = "预览类型编码（精确查询）")
    private PreviewTypeEnum previewType;

    /**
     * 预览类型名称（模糊查询）
     */
    @Schema(description = "预览类型名称（模糊查询）")
    private String previewName;

    /**
     * 文件后缀（精确查询）
     */
    @Schema(description = "文件后缀（精确查询）")
    private String fileSuffix;

}
