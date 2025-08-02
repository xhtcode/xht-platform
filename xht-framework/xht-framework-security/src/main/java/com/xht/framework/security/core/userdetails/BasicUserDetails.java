package com.xht.framework.security.core.userdetails;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.io.Serial;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义用户信息
 *
 * @author xht
 **/
@Getter
public class BasicUserDetails extends User implements UserDetails, Principal, OAuth2AuthenticatedPrincipal {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 扩展属性，方便存放oauth 上下文相关信息
     */
    private final Map<String, Object> attributes = new HashMap<>();

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
                            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, accountNonLocked, authorities);
        this.userId = userId;
        this.deptId = deptId;
        this.mobile = mobile;
    }

    /**
     * 添加扩展属性
     *
     * @param key   属性名
     * @param value 属性值
     */
    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        return this.getUsername();
    }
}
