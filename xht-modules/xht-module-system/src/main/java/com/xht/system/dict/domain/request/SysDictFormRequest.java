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
    @Null(message = "字典类型唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "字典类型唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "字典类型唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(description = "字典类型唯一标识", example = "101")
    private Long id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 20, message = "字典编码长度不能超过20个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典编码", example = "D001")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 50, message = "字典名称长度不能超过50个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典名称", example = "示例字典")
    private String dictName;

    /**
     * 排序序号
     */
    @NotNull(message = "排序序号参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Min(value = 0, message = "排序序号最小值为0", groups = {Groups.Create.class, Groups.Update.class})
    @Max(value = 999, message = "排序序号最大值为999", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 字典描述
     */
    @Size(max = 200, message = "字典描述长度不能超过200个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典描述", example = "这是一个示例字典")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @NotNull(message = "状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "状态（1: 启用 0: 禁用）", example = "1")
    private DictStatusEnums status;

}