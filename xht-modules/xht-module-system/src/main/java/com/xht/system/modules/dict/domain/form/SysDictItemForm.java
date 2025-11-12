package com.xht.system.modules.dict.domain.form;

import com.xht.framework.core.domain.form.BasicForm;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dict.common.enums.DictStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 字典项创建请求
 */
@Data
@Schema(description = "字典项创建请求")
public class SysDictItemForm extends BasicForm {

    /**
     * 字典项唯一标识
     */
    @Null(message = "字典项唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "字典项唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "字典项唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典项唯一标识", example = "101")
    private Long id;


    /**
     * 所属字典类别
     */
    @NotNull(message = "所属字典类别不能为空")
    @Positive(message = "所属字典类别参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "所属字典类别", example = "101")
    private Long dictId;

    /**
     * 字典项标签
     */
    @NotBlank(message = "字典项标签参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 100, message = "字典项标签长度不能超过100", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典项标签", example = "标签1")
    private String itemLabel;

    /**
     * 字典项值
     */
    @NotBlank(message = "字典项值参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 255, message = "字典项值长度不能超过255", groups = {Groups.Create.class, Groups.Update.class})
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
    @NotNull(message = "排序序号参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Min(value = 0, message = "排序序号不能小于0", groups = {Groups.Create.class, Groups.Update.class})
    @Max(value = 999, message = "排序序号不能大于999", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "排序序号", example = "1")
    private Integer sortOrder;

    /**
     * 字典项描述
     */
    @Size(max = 255, message = "字典项描述长度不能超过255", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "字典项描述", example = "这是一个字典项")
    private String remark;

    /**
     * 状态(1:启用 0:禁用)
     */
    @NotNull(message = "状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "状态(1:启用 0:禁用)", example = "1")
    private DictStatusEnums status;

}
