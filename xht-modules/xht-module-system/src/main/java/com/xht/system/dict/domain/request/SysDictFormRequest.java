package com.xht.system.dict.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 字典类型创建请求
 */
@Data
@Schema(description = "字典类型创建请求")
public class SysDictFormRequest extends FormRequest {

    /**
     * 字典类型唯一标识
     */
    @Null(message = "字典类型唯一标识不能为空", groups = {Groups.Create.class})
    @NotNull(message = "字典类型唯一标识不能为空", groups = {Groups.Update.class})
    @Positive(message = "字典类型唯一标识不合法")
    @Schema(description = "字典类型唯一标识", example = "101")
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码", example = "D001")
    @NotBlank(message = "字典编码不能为空")
    @Size(max = 20, message = "字典编码长度不能超过20个字符")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称", example = "示例字典")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称长度不能超过50个字符")
    private String dictName;

    /**
     * 排序序号
     */
    @Schema(description = "排序序号", example = "1")
    @NotNull(message = "排序序号不能为空")
    @Min(value = 0, message = "排序序号最小值为0")
    @Max(value = 999, message = "排序序号最大值为999")
    private Integer sortOrder;

    /**
     * 字典描述
     */
    @Schema(description = "字典描述", example = "这是一个示例字典")
    @Size(max = 200, message = "字典描述长度不能超过200个字符")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @Schema(description = "状态（1: 启用 0: 禁用）", example = "1")
    @NotNull(message = "状态不能为空")
    private DictStatusEnums status;

}