package com.xht.system.modules.dept.domain.request;

import com.xht.framework.core.constant.RegexConstant;
import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.dept.common.enums.DeptStatusEnums;
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
public class SysDeptForm extends FormRequest {

    /**
     * 部门唯一标识
     */
    @Null(message = "部门唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "部门唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "部门唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(description = "部门唯一标识", example = "101")
    private Long id;

    /**
     * 父部门id
     */
    @NotNull(message = "父部门ID参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Digits(integer = 19, fraction = 0, message = "父部门ID必须为数字", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "父部门ID", example = "101")
    private Long parentId;

    /**
     * 部门编码
     */
    @NotBlank(message = "部门编码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Pattern(regexp = RegexConstant.DEPT_CODE, message = "部门编码格式不正确，格式示例：DEPT001", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门编码", example = "DEPT001")
    private String deptCode;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门名称", example = "技术部")
    private String deptName;


    /**
     * 状态（0正常 1停用）
     */
    @NotNull(message = "部门状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门状态", example = "0")
    private DeptStatusEnums deptStatus;

    /**
     * 显示顺序
     */
    @Min(value = 1, message = "显示顺序最小值为1")
    @NotNull(message = "显示顺序参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "显示顺序", example = "1")
    private Integer deptSort;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Pattern(regexp = RegexConstant.MOBILE_PHONE, message = "联系电话格式不正确，格式示例：13800138000", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Email(message = "邮箱格式不正确", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "这是一个技术部门")
    private String remark;

}
