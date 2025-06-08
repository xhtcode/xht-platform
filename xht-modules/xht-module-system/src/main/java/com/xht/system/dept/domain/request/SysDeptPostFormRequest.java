package com.xht.system.dept.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dept.common.enums.DeptPostStatusEnums;
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
    @Null(message = "部门岗位唯一标识不能为空", groups = {Groups.Create.class})
    @NotNull(message = "部门岗位唯一标识不能为空", groups = {Groups.Update.class})
    @Positive(message = "部门岗位唯一标识不合法")
    @Schema(description = "部门岗位唯一标识", example = "101")
    private Long id;

    /**
     * 部门id，必须大于0
     */
    @NotNull(message = "部门id不能为空")
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位编码，必须是唯一的且不超过50个字符
     */
    @NotNull(message = "岗位编码不能为空")
    @Size(max = 50, message = "岗位编码长度不能超过50个字符")
    @Schema(description = "岗位编码")
    private String postCode;

    /**
     * 岗位名称，必须不超过100个字符
     */
    @NotNull(message = "岗位名称不能为空")
    @Size(max = 100, message = "岗位名称长度不能超过100个字符")
    @Schema(description = "岗位名称")
    private String postName;

    /**
     * 岗位排序，必须大于等于0
     */
    @NotNull(message = "岗位排序不能为空")
    @Min(value = 0, message = "岗位排序必须大于等于0")
    @Schema(description = "岗位排序")
    private Integer postSort;

    /**
     * 岗位状态（0正常 1停用）
     */
    @NotNull(message = "岗位状态不能为空")
    @Schema(description = "岗位状态")
    private DeptPostStatusEnums postStatus;

    /**
     * 岗位限制人数
     */
    @Min(value = 1, message = "岗位限制人数必须大于等于1")
    @Schema(description = "岗位限制人数")
    private Integer postLimit;

    /**
     * 岗位描述，可以为空但不超过255个字符
     */
    @NotEmpty(message = "岗位描述不能为空")
    @Size(max = 255, message = "岗位描述长度不能超过255个字符")
    @Schema(description = "岗位描述")
    private String remark;
}

