package com.xht.system.modules.dict.domain.query;

import com.xht.framework.core.domain.query.PageBasicQuery;
import com.xht.system.modules.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询字典项分页查询参数
 *
 * @author xht
 **/
@Data
@Schema(description = "查询字典项分页查询参数")
public class SysDictItemBasicQuery extends PageBasicQuery {

    /**
     * 所属字典ID
     */
    @Schema(description = "所属字典ID", example = "101")
    private Long dictId;

    /**
     * 字典项编码
     */
    @Schema(description = "字典项编码", example = "001")
    private String dictCode;

    /**
     * 字典项标签
     */
    @Schema(description = "字典项标签", example = "标签1")
    private String itemLabel;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值", example = "值1")
    private String itemValue;


    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态(1:启用 0:禁用)", example = "1")
    private DictStatusEnums status;

}
