package com.xht.framework.security.core.userdetails;

import com.xht.framework.core.enums.LoginTypeEnums;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户查询dao
 *
 * @author xht
 **/
public abstract class BasicUserDetailsService implements UserDetailsService {

    @Override
    public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username, LoginTypeEnums.PASSWORD);
    }

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    public abstract BasicUserDetails loadUserByUsername(String username, LoginTypeEnums loginType) throws UsernameNotFoundException;

}
