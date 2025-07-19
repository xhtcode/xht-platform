package com.xht.boot.security.strategy;

import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.strategy.UserDetailsStrategy;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.domain.RequestUserBO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 密码模式用户查询
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class PassWordUserDetailsStrategy extends UserDetailsStrategy {

    /**
     * 查询用户请求
     *
     * @param requestUserBO 用户请求信息
     * @param request       当前request请求
     * @return 用户详细信息
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public BasicUserDetails loadUserByUsername(RequestUserBO requestUserBO, HttpServletRequest request) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 获取登录类型
     *
     * @return 登录类型 {@link LoginTypeEnums}
     */
    @Override
    public LoginTypeEnums loginType() {
        return null;
    }
}
