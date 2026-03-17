package com.xht.framework.security.core.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.core.enums.UserStatusEnums;
import com.xht.framework.core.enums.UserTypeEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义用户信息
 * 实现Spring Security的UserDetails接口，用于存储和提供用户认证授权相关信息
 * 同时实现Principal和OAuth2AuthenticatedPrincipal接口，支持OAuth2认证
 *
 * @author xht
 **/
@Getter
@JsonSerialize
@NoArgsConstructor(force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicUserDetails implements UserDetails, OAuth2AuthenticatedPrincipal, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 扩展属性，方便存放oauth 上下文相关信息
     */
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private final Long userId;

    /**
     * 用户类型
     */
    @Schema(description = "用户类型")
    private final UserTypeEnums userType;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private final String username;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private final String nickName;

    /**
     * 密码
     */
    @JsonIgnore
    @Schema(description = "密码")
    private final String password;

    /**
     * 用户权限集合
     */
    private final Set<GrantedAuthority> authorities;

    /**
     * 账号状态(1-正常,2-锁定,3-禁用,4-过期)
     */
    @Setter
    @Schema(description = "账号状态(1-正常,2-锁定,3-禁用,4-过期)")
    private UserStatusEnums userStatus;

    /**
     * 密码盐
     */
    @Setter
    @JsonIgnore
    private String passWordSalt;

    /**
     * 手机号
     */
    @Setter
    @Schema(description = "手机号")
    private String userPhone;

    /**
     * 部门id
     */
    @Setter
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @Setter
    @Schema(description = "部门名称")
    private String deptName;

    /**
     * 角色列表
     */
    @Setter
    @Schema(description = "角色列表")
    private Set<String> roleCodes;

    /**
     * 权限列表
     */
    @Setter
    @Schema(description = "权限列表")
    private Set<String> menuButtonCodes;

    /**
     * 登录类型
     */
    @Setter
    @Schema(description = "登录类型")
    private LoginTypeEnums loginType;


    public BasicUserDetails(Long userId, UserTypeEnums userType, String username, String nickName, String password, Set<GrantedAuthority> authorities) {
        this.userId = userId;
        this.userType = userType;
        this.username = username;
        this.nickName = nickName;
        this.password = password;
        this.authorities = authorities;
    }


    @Override
    public String getName() {
        return this.username;
    }


    /**
     * 指示用户的账户是否已过期。过期的账户无法进行身份验证。
     *
     * @return <code>true</code> 如果用户的账户有效（即未过期），
     * <code>false</code> 如果不再有效（即已过期）
     */
    @Override
    public boolean isAccountNonExpired() {
        return !Objects.equals(this.userStatus, UserStatusEnums.EXPIRED);
    }

    /**
     * 指示用户是锁定还是未锁定。锁定的用户无法进行身份验证。
     *
     * @return <code>true</code> 如果用户未被锁定，否则返回 <code>false</code>
     */
    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(this.userStatus, UserStatusEnums.LOCKED);
    }

    /**
     * 指示用户的凭据（密码）是否已过期。过期的凭据会阻止身份验证。
     *
     * @return <code>true</code> 如果用户的凭据有效（即未过期），
     * <code>false</code> 如果不再有效（即已过期）
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 指示用户是启用还是禁用。禁用的用户无法进行身份验证。
     *
     * @return <code>true</code> 如果用户已启用，否则返回 <code>false</code>
     */
    @Override
    public boolean isEnabled() {
        return !Objects.equals(this.userStatus, UserStatusEnums.DISABLED);
    }

}