package com.xht.system.dept.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.dept.common.enums.DeptStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 部门表单请求参数
 *
 * @author xht
 */
@Data
@Schema(description = "部门表单请求参数")
public class SysDeptFormRequest extends FormRequest {

    /**
     * 部门唯一标识
     */
    @Null(message = "部门唯一标识不能为空", groups = {Groups.Create.class})
    @NotNull(message = "部门唯一标识不能为空", groups = {Groups.Update.class})
    @Positive(message = "部门唯一标识不合法")
    @Schema(description = "部门唯一标识", example = "101")
    private Long id;

    /**
     * 父部门id
     */
    @Schema(description = "父部门ID", example = "101")
    @NotNull(message = "父部门ID不能为空")
    @Digits(integer = 19, fraction = 0, message = "父部门ID必须为数字")
    private Long parentId;

    /**
     * 部门编码
     */
    @Schema(description = "部门编码", example = "DEPT001")
    @NotBlank(message = "部门编码不能为空")
    @Pattern(regexp = "^[A-Z]{2,5}\\d{3}$", message = "部门编码格式不正确，格式示例：DEPT001")
    private String deptCode;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", example = "技术部")
    @NotBlank(message = "部门名称不能为空")
    private String deptName;


    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "部门状态", example = "0")
    @NotNull(message = "部门状态不能为空")
    private DeptStatusEnums deptStatus;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序", example = "1")
    @NotNull(message = "显示顺序不能为空")
    @Min(value = 1, message = "显示顺序最小值为1")
    private Integer deptSort;


    /**
     * 负责人用户ID
     */
    @Schema(description = "负责人用户ID")
    private Long leaderUserId;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话", example = "13800138000")
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "联系电话格式不正确，格式示例：13800138000")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "这是一个技术部门")
    private String remark;


}
