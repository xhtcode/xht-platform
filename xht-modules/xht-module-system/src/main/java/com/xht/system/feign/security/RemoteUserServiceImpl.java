package com.xht.system.feign.security;

import com.xht.cloud.oauth2.dto.UserInfoDTO;
import com.xht.cloud.security.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.system.modules.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户查询客户端
 *
 * @author xht
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class RemoteUserServiceImpl implements RemoteUserService {

    private final IUserService userService;

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @Override
    @GetMapping("/api/sys/user/{username}/{loginType}")
    @NoAuthentication
    public R<UserInfoDTO> loadUserByUsername(@PathVariable("username") String username, @PathVariable("loginType") LoginTypeEnums loginType) {
        return R.ok(userService.loadUserByUsername(username, loginType));
    }
}
