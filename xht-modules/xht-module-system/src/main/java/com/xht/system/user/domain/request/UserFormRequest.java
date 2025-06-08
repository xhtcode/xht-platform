package com.xht.system.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.framework.web.validation.Groups;
import com.xht.system.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 用户创建请求参数
 *
 * @author xht
 **/
@Data
@Schema(description = "用户创建请求参数")
public class UserFormRequest extends FormRequest {

    /**
     * 用户唯一标识
     */
    @Null(message = "用户唯一标识不能为空", groups = {Groups.Create.class})
    @NotNull(message = "用户唯一标识不能为空", groups = {Groups.Update.class})
    @Positive(message = "用户唯一标识不合法")
    @Schema(description = "用户唯一标识", example = "101")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "星辰")
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 2, max = 50, message = "用户昵称长度应在2到50之间")
    private String nickName;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @NotNull(message = "账号状态不能为空")
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空")
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位id
     */
    @NotNull(message = "岗位信息不能为空")
    @Schema(description = "岗位id")
    private Long postId;

    @Valid
    @Schema(description = "用户详细信息")
    private UserProfilesFormRequest profile;

}
