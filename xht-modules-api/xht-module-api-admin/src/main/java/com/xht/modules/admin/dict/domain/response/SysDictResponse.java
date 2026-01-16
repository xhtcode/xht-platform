package com.xht.modules.admin.dict.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.modules.admin.dict.enums.DictShowDisabledEnums;
import com.xht.modules.admin.dict.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典类型响应信息
 */
@Data
@Schema(description = "字典类型响应信息")
public class SysDictResponse extends MetaResponse {
    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号")
    private Integer sortOrder;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态（1: 启用 0: 禁用）")
    private DictStatusEnums status;

    /**
     * 子节点是否显示禁用状态
     */
    @Schema(description = "子节点是否显示禁用状态")
    private DictShowDisabledEnums showDisabled;

}
