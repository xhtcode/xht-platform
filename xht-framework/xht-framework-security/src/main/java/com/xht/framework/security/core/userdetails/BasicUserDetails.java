package com.xht.framework.security.core.userdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义用户信息
 *
 * @author xht
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 扩展属性，方便存放oauth 上下文相关信息
     */
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 添加扩展属性
     *
     * @param key   属性名
     * @param value 属性值
     */
    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

}
