package com.xht.system.modules.user.domain.request;

import com.xht.framework.core.domain.request.FormRequest;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
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
     * 用户ID
     */
    @Schema(description = "用户唯一标识符", example = "12345")
    private Long id;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型，例如：1-管理员，2-普通用户", example = "1")
    private Integer userType;

    /**
     * 用户昵称
     */
    @Schema(description = "用户的昵称，用于显示", example = "星月")
    private String userName;

    /**
     * 手机号码
     */
    @Schema(description = "用户的手机号码", example = "13800138000")
    private String phoneNumber;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态，例如：0-禁用，1-启用", example = "1")
    private UserStatusEnums userStatus;

    /**
     * 用户详细信息
     */
    @Schema(description = "用户详细信息")
    private UserProfileFormRequest userProfile;

}
