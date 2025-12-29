package com.xht.system.modules.dict.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
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
public class SysDictQuery extends PageBasicQuery {

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
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态（1: 启用 0: 禁用）")
    private DictStatusEnums status;
}
