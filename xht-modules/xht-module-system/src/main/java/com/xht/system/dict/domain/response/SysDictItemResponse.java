package com.xht.system.dict.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.system.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典项响应信息
 */
@Data
@Schema(description = "字典项响应信息")
public class SysDictItemResponse extends BasicResponse {

    /**
     * 字典项ID
     */
    @Schema(description = "字典项ID", example = "1")
    private Long id;

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
     * 显示颜色
     */
    @Schema(description = "显示颜色", example = "#FFFFFF")
    private String itemColor;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 字典项描述
     */
    @Schema(description = "字典项描述", example = "这是一个字典项")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态(1:启用 0:禁用)", example = "1")
    private DictStatusEnums status;

}
