package com.xht.cloud.oauth2.dto;

import com.xht.framework.core.domain.dto.IDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息DTO
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息DTO")
public class UserInfoDTO implements IDto {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 密码
     */
    @Schema(description = "密码")
    private String passWord;

    /**
     * 密码盐值
     */
    @Schema(description = "密码盐值")
    private String salt;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatarUrl;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private Integer userStatus;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位id
     */
    @Schema(description = "岗位id")
    private Long postId;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleCodes = new ArrayList<>();

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<String> permissionCodes = new ArrayList<>();

    /**
     * 数据范围(1-全部数据权限,2-自定数据权限,3-本部门数据权限,4-本部门及以下数据权限,5-仅本人数据权限)
     */
    @Schema(description = "数据范围")
    private Integer dataScope;
}
