package com.xht.framework.security.core.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xht.framework.core.enums.LoginTypeEnums;
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
     * 用户密码
     */
    @JsonIgnore
    private final String password;

    /**
     * 用户名
     */
    @Setter
    private String username;

    /**
     * 登录方式
     */
    @Setter
    private LoginTypeEnums loginType;

    /**
     * 用户权限集合
     */
    private final Set<GrantedAuthority> authorities;

    /**
     * 账户是否未过期
     */
    private final boolean accountNonExpired;

    /**
     * 账户是否未锁定
     */
    private final boolean accountNonLocked;

    /**
     * 凭证是否未过期
     */
    private final boolean credentialsNonExpired;

    /**
     * 账户是否启用
     */
    private final boolean enabled;
    
    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long userId;

    /**
     * 部门ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long deptId;

    /**
     * 手机号
     */
    private final String mobile;

    /**
     * 构造器
     *
     * @param userId           用户ID
     * @param deptId           部门ID
     * @param username         用户名
     * @param password         密码
     * @param mobile           手机号
     * @param accountNonLocked 账户是否未锁定
     * @param authorities      权限列表
     */
    public BasicUserDetails(Long userId,
                            Long deptId,
                            String username,
                            String password,
                            String mobile,
                            boolean accountNonLocked,
                            Set<GrantedAuthority> authorities) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.userId = userId;
        this.deptId = deptId;
        this.mobile = mobile;
    }


    @Override
    public String getName() {
        return this.username;
    }
}