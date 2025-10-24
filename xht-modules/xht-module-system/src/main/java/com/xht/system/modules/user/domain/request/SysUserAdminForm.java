package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 用户信息表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息表单请求参数")
public class SysUserAdminForm extends FormRequest {

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别")
    @NotNull(message = "用户性别不能为空")
    private Integer gender;

    /**
     * 出生日期
     */
    @Schema(description = "出生日期")
    @NotNull(message = "出生日期不能为空")
    private LocalDate birthDate;

    /**
     * 用户年龄
     */
    @Schema(description = "用户年龄")
    @NotNull(message = "用户年龄不能为空")
    private Integer age;

    /**
     * 地址
     */
    @Schema(description = "用户地址")
    @NotBlank(message = "用户地址不能为空")
    private String address;

    /**
     * 邮政编码
     */
    @Schema(description = "邮政编码")
    @NotBlank(message = "邮政编码不能为空")
    private String postalCode;

    /**
     * 紧急联系人
     */
    @Schema(description = "紧急联系人")
    @NotBlank(message = "紧急联系人不能为空")
    private String emergencyContact;

    /**
     * 紧急联系人电话
     */
    @Schema(description = "紧急联系人电话")
    @NotBlank(message = "紧急联系人电话不能为空")
    private String emergencyPhone;

    /**
     * 用户民族
     */
    @Schema(description = "用户民族")
    @NotBlank(message = "用户民族不能为空")
    private String nation;

}
