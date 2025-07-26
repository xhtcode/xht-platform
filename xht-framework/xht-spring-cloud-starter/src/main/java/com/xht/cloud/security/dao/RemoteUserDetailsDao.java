package com.xht.cloud.security.dao;

import com.xht.cloud.security.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.utils.ROptional;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.core.userdetails.UserDetailsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class RemoteUserDetailsDao implements UserDetailsDao {

    private final RemoteUserService remoteUserService;

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
        R<BasicUserDetails> userDetailsR = remoteUserService.loadUserByUsername(username, loginType);
        ROptional<BasicUserDetails> rOptional = ROptional.of(userDetailsR);
        return rOptional.get().orElseThrow(() -> new UsernameNotFoundException("账号或密码错误"));
    }
}
