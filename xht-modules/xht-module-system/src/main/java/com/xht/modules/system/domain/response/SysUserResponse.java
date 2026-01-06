package com.xht.modules.system.domain.response;

import com.xht.framework.core.domain.response.MetaResponse;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息响应信息")
public class SysUserResponse extends MetaResponse {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private UserStatusEnums userStatus;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String userPhone;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String userAvatar;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
}
