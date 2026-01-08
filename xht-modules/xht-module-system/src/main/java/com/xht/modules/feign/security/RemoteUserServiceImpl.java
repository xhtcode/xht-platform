package com.xht.modules.feign.security;

import com.xht.api.system.dto.UserInfoDTO;
import com.xht.api.system.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.modules.system.service.IUserService;
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
    @IgnoreAuth
    @GetMapping("/api/sys/user/{username}/{loginType}")
    public R<UserInfoDTO> loadUserByUsername(@PathVariable String username, @PathVariable LoginTypeEnums loginType) {
        return R.ok(userService.loadUserByUsername(username, loginType));
    }

    /**
     * 注册手机用户
     *
     * @param phone 手机号
     * @return 注册用户信息
     */
    @Override
    @IgnoreAuth
    @GetMapping("/api/sys/user/register/{phone}")
    public R<UserInfoDTO> registerPhoneUser(@PathVariable String phone) {
        return R.ok(userService.registerPhoneUser(phone));
    }

    /**
     * 验证手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    @Override
    @IgnoreAuth
    @GetMapping("/api/sys/user/exists/{phone}")
    public R<Boolean> checkPhoneExists(@PathVariable String phone) {
        return R.ok(userService.checkPhoneExists(phone));
    }
}
