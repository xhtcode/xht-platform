package com.xht.system.modules.dict.domain.request;

import com.xht.framework.core.domain.request.PageQueryRequest;
import com.xht.system.modules.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询字典分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "查询字典分页查询参数")
public class SysDictQueryRequest extends PageQueryRequest {

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
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态（1: 启用 0: 禁用）", example = "1")
    private DictStatusEnums status;
}
