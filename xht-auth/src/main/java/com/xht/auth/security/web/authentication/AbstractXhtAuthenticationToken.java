package com.xht.auth.security.web.authentication;

import com.xht.framework.common.enums.LoginTypeEnums;
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

    private final LoginTypeEnums loginTypeEnums;

    /**
     * 根据所提供的权限数组创建一个令牌。*
     *
     * @param loginTypeEnums：登录类型枚举
     * @param authorities：表示由此认证对象所代表的主体的权限集合（即 <tt>GrantedAuthority</tt> 类型的集合）
     */
    public AbstractXhtAuthenticationToken(LoginTypeEnums loginTypeEnums, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.loginTypeEnums = loginTypeEnums;
    }

    /**
     * 获取登录类型
     * @return 登录类型枚举
     */
    public final LoginTypeEnums getLoginType() {
        return this.loginTypeEnums;
    }

}
