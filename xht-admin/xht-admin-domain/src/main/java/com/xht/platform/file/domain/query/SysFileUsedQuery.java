package com.xht.platform.file.domain.query;

import com.xht.framework.common.domain.query.PageBasicQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 描述： 文件用途配置分页查询参数
 * <p>
 * 未传参的字段不参与过滤（动态条件）：用途编码、用途名称模糊匹配，启用状态、是否内置精确匹配
 *
 * @author xht
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文件用途配置分页查询参数")
public class SysFileUsedQuery extends PageBasicQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用途编码（模糊查询）")
    private String usedCode;

    @Schema(description = "用途名称（模糊查询）")
    private String usedName;

    @Schema(description = "是否启用：1 启用 / 0 停用（精确查询）")
    private Integer enabled;

    @Schema(description = "是否内置：1 内置 / 0 自定义（精确查询）")
    private Integer builtIn;

}
