package com.xht.system.modules.user.domain.response;

import com.xht.framework.core.domain.response.BasicResponse;
import com.xht.system.modules.user.common.enums.UserStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户信息响应信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息响应信息")
public class SysUserResponse extends BasicResponse {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatarUrl;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

}
