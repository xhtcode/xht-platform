package com.xht.api.system.user.dto;

import com.xht.framework.core.domain.dto.BasicDTO;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * 用户信息DTO
 *
 * @author xht
 **/
@Data
@Schema(description = "用户信息DTO")
public class UserInfoDTO extends BasicDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private UserTypeEnums userType;

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
     * 密码
     */
    @Schema(description = "密码")
    private String passWord;

    /**
     * 密码盐值
     */
    @Schema(description = "密码盐值")
    private String passWordSalt;


    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String userPhone;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String userAvatar;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 注册日期
     */
    @Schema(description = "注册日期")
    private LocalDateTime registerDate;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private List<String> roleCodes = Collections.emptyList();

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private List<String> permissionCodes = Collections.emptyList();

    /**
     * 数据范围(1-全部数据权限,2-自定数据权限,3-本部门数据权限,4-本部门及以下数据权限,5-仅本人数据权限)
     */
    @Schema(description = "数据范围")
    private Integer dataScope;
}
