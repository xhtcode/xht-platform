package com.xht.system.dict.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 字典项创建请求
 */
@Data
@Schema(description = "字典项创建请求")
public class SysDictItemFormRequest extends FormRequest {

    /**
     * 字典项唯一标识
     */
    @Null(message = "字典项唯一标识不能为空", groups = {Groups.Create.class})
    @NotNull(message = "字典项唯一标识不能为空", groups = {Groups.Update.class})
    @Positive(message = "字典项唯一标识不合法")
    @Schema(description = "字典项唯一标识", example = "101")
    private Long id;


    /**
     * 所属字典类别
     */
    @Schema(description = "所属字典类别", example = "101")
    @NotNull(message = "所属字典类别不能为空")
    @Positive(message = "所属字典类别不合法")
    private Long dictId;

    /**
     * 字典项标签
     */
    @Schema(description = "字典项标签", example = "标签1")
    @NotBlank(message = "字典项标签不能为空")
    @Size(max = 100, message = "字典项标签长度不能超过100")
    private String itemLabel;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值", example = "值1")
    @NotBlank(message = "字典项值不能为空")
    @Size(max = 255, message = "字典项值长度不能超过255")
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
    @NotNull(message = "排序序号不能为空")
    @Min(value = 0, message = "排序序号不能小于0")
    @Max(value = 999, message = "排序序号不能大于999")
    private Integer sortOrder;

    /**
     * 字典项描述
     */
    @Schema(description = "字典项描述", example = "这是一个字典项")
    @Size(max = 255, message = "字典项描述长度不能超过255")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态(1:启用 0:禁用)", example = "1")
    @NotNull(message = "状态不能为空")
    private DictStatusEnums status;

}
