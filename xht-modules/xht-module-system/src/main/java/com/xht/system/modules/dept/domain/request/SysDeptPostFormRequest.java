package com.xht.system.modules.dept.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dept.common.enums.DeptPostStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 新增部门岗位请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "新增部门岗位请求参数")
public class SysDeptPostFormRequest extends FormRequest {

    /**
     * 部门岗位唯一标识
     */
    @Null(message = "ID唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "ID唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "ID唯一标识参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门岗位唯一标识", example = "101")
    private Long id;

    /**
     * 部门id，必须大于0
     */
    @NotNull(message = "部门id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位编码，必须是唯一的且不超过50个字符
     */
    @NotNull(message = "岗位编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 50, message = "岗位编码长度不能超过50个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "岗位编码")
    private String postCode;

    /**
     * 岗位名称，必须不超过100个字符
     */
    @NotNull(message = "岗位名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 100, message = "岗位名称长度不能超过100个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "岗位名称")
    private String postName;

    /**
     * 岗位排序，必须大于等于0
     */
    @NotNull(message = "岗位排序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Min(value = 0, message = "岗位排序必须大于等于0", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "岗位排序")
    private Integer postSort;

    /**
     * 岗位状态（0正常 1停用）
     */
    @NotNull(message = "岗位状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "岗位状态")
    private DeptPostStatusEnums postStatus;

    /**
     * 岗位描述，可以为空但不超过255个字符
     */
    @NotEmpty(message = "岗位描述参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(max = 255, message = "岗位描述长度不能超过255个字符", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "岗位描述")
    private String remark;
}

