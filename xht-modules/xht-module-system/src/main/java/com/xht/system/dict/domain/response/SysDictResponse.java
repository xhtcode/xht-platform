package com.xht.system.dict.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.system.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典类型响应信息
 */
@Data
@Schema(description = "字典类型响应信息")
public class SysDictResponse extends BasicResponse {
    /**
     * 字典ID
     */
    @Schema(description = "字典ID", example = "12345")
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码", example = "D001")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称", example = "示例字典")
    private String dictName;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述", example = "这是一个示例字典")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态（1: 启用 0: 禁用）", example = "1")
    private DictStatusEnums status;

}
