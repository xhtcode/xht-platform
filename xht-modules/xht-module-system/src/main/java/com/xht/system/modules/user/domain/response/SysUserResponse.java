package com.xht.system.modules.user.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xht.framework.core.domain.response.BasicResponse;
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
     * 用户类型
     */
    @Schema(description = "用户类型")
    private Integer userType;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userAccount;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userName;

    /**
     * 用户密码
     */
    @JsonIgnore
    @Schema(description = "用户密码")
    private String passWord;

    /**
     * 密码盐值
     */
    @JsonIgnore
    @Schema(description = "密码盐值")
    private String passWordSalt;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phoneNumber;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatarUrl;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private Integer userStatus;

    /**
     * 部门id
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 岗位id
     */
    @Schema(description = "岗位ID")
    private Long postId;
}
