package com.xht.framework.security.core.userdetails;

import com.xht.framework.core.utils.HttpServletUtils;
import com.xht.framework.security.constant.SecurityConstant;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.properties.SecurityProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class BasicUserDetailsService implements UserDetailsService {

    @Resource
    private UserDetailsDao userDetailsDao;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest httpServletRequest = HttpServletUtils.getHttpServletRequest();
        String loginTypeStr = HttpServletUtils.getParams(httpServletRequest, SecurityConstant.REQUEST_LOGIN_TYPE);
        LoginTypeEnums loginType = LoginTypeEnums.of(loginTypeStr);
        BasicUserDetails basicUserDetails = userDetailsDao.loadUserByUsername(username, loginType);
        basicUserDetails.setPassword(securityProperties.buildSalt(basicUserDetails.getPassword(), basicUserDetails.getSalt()));
        return userDetailsDao.loadUserByUsername(username, loginType);
    }

}
