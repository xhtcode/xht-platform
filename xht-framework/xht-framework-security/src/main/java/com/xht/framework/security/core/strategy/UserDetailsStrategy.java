package com.xht.framework.security.core.strategy;

import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.framework.security.domain.RequestUserBO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户查询策略
 *
 * @author xht
 **/
public abstract class UserDetailsStrategy {

    /**
     * 查询用户请求
     *
     * @param requestUserBO 用户请求信息
     * @param request       当前request请求
     * @return 用户详细信息
     * @throws UsernameNotFoundException 异常
     */
    public abstract BasicUserDetails loadUserByUsername(RequestUserBO requestUserBO, HttpServletRequest request) throws UsernameNotFoundException;

    /**
     * 获取登录类型
     *
     * @return 登录类型 {@link LoginTypeEnums}
     */
    public abstract LoginTypeEnums loginType();
}
