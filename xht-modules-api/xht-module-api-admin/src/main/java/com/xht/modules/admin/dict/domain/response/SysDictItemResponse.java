package com.xht.modules.admin.dict.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.admin.dict.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项响应信息
 */
@Data
@Schema(description = "字典项响应信息")
public class SysDictItemResponse extends MetaResponse {

    /**
     * 字典项ID
     */
    @Schema(description = "字典项ID")
    private Long id;

    /**
     * 所属字典ID
     */
    @Schema(description = "所属字典ID")
    private Long dictId;

    /**
     * 字典项编码
     */
    @Schema(description = "字典项编码")
    private String dictCode;

    /**
     * 字典项标签
     */
    @Schema(description = "字典项标签")
    private String itemLabel;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值")
    private String itemValue;

    /**
     * 显示颜色
     */
    @Schema(description = "显示颜色")
    private String itemColor;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号")
    private Integer sortOrder;

    /**
     * 字典项描述
     */
    @Schema(description = "字典项描述")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态(1:启用 0:禁用)")
    private DictStatusEnums status;

}
