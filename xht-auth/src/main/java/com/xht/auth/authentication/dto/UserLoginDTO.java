package com.xht.auth.authentication.dto;

import com.xht.framework.core.domain.dto.BasicDTO;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * 用户登录信息
 *
 * @author xht
 **/
@Data
@Schema(description = "用户登录信息")
public class UserLoginDTO extends BasicDTO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;
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


    /**
     * 注册日期
     */
    @Schema(description = "注册日期")
    private LocalDateTime registerDate;

    /**
     * 角色列表
     */
    @Schema(description = "角色列表")
    private Set<String> roleCodes = Collections.emptySet();

    /**
     * 权限列表
     */
    @Schema(description = "权限列表")
    private Set<String> menuButtonCodes = Collections.emptySet();

    /**
     * 数据范围(1-全部数据权限,2-自定数据权限,3-本部门数据权限,4-本部门及以下数据权限,5-仅本人数据权限)
     */
    @Schema(description = "数据范围")
    private Integer dataScope;
}
