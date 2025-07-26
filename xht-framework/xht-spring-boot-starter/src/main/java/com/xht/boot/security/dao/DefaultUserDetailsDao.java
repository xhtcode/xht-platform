package com.xht.boot.security.dao;

import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

/**
 * 密码模式用户查询
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class DefaultUserDetailsDao implements UserDetailsDao {

    private final static String DEFAULT_PASSWORD = "123456";

    /**
     * 根据用户名和登录类型查询用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public BasicUserDetails loadUserByUsername(String username, LoginTypeEnums loginType) throws UsernameNotFoundException {
        return new BasicUserDetails(username, DEFAULT_PASSWORD, null, Collections.emptyList());
    }
}
