package com.xht.framework.security.core.userdetails;

import com.xht.framework.core.enums.LoginTypeEnums;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户查询dao
 *
 * @author xht
 **/
public interface UserDetailsDao {

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    BasicUserDetails loadUserByUsername(String username, LoginTypeEnums loginType) throws UsernameNotFoundException;

}
