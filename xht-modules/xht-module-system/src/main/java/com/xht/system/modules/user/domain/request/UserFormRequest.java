package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;

/**
 * 用户表单请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户表单请求参数")
public class UserFormRequest extends FormRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    @Null(message = "用户唯一标识必须为空", groups = {Groups.Create.class})
    @NotNull(message = "用户唯一标识参数不合法", groups = {Groups.Update.class})
    @Positive(message = "用户唯一标识参数不合法", groups = {Groups.Update.class})
    @Schema(description = "用户唯一标识，创建时不需要传入")
    private Long id;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "用户类型，例如：1-管理员，2-普通用户")
    private Integer userType;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Size(min = 2, max = 50, message = "用户昵称长度应在2到50之间")
    @Schema(description = "用户昵称，长度应在2到50之间")
    private String userName;

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "用户的手机号码")
    private String phoneNumber;

    /**
     * 账号状态
     */
    @NotNull(message = "账号状态参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "账号状态，0-正常,1-禁用,2-锁定,3-过期,4-禁止登录,5-未注册,6-其他")
    private UserStatusEnums userStatus;

    /**
     * 用户详细信息
     */
    @Valid
    @Schema(description = "用户详细信息")
    private UserProfileFormRequest userProfile;

    /**
     * 部门id
     */
    @NotNull(message = "部门id参数不合法", groups = {Groups.Create.class, Groups.Update.class})
    @Schema(description = "部门id")
    private Long deptId;

}