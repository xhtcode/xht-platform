package com.xht.auth.security.web.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 抽象认证令牌
 *
 * @author xht
 **/
@Slf4j
public abstract class AbstractXhtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 根据所提供的权限数组创建一个令牌。*
     *
     * @param authorities：表示由此认证对象所代表的主体的权限集合（即 <tt>GrantedAuthority</tt> 类型的集合）
     */
    public AbstractXhtAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

}
