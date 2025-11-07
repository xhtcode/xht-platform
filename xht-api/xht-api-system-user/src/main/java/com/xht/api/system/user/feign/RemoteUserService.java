package com.xht.api.system.user.feign;

import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.api.system.user.feign.factory.RemoteUserServiceFallbackFactory;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.LoginTypeEnums;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户客户端
 *
 * @author xht
 **/
@FeignClient(
        contextId = "remoteUserService",
        value = "xht-system",
        fallbackFactory = RemoteUserServiceFallbackFactory.class
)
public interface RemoteUserService {

    /**
     * 根据用户名和登录类型获取用户信息
     *
     * @param username  用户名
     * @param loginType 登录类型
     * @return 用户信息
     */
    @FeignIgnoreAuth
    @ResponseBody
    @GetMapping("/api/sys/user/{username}/{loginType}")
    R<UserInfoDTO> loadUserByUsername(@PathVariable("username") String username, @PathVariable("loginType") LoginTypeEnums loginType);

    /**
     * 注册手机用户
     *
     * @param phone 手机号
     * @return 注册用户信息
     */
    @FeignIgnoreAuth
    @ResponseBody
    @GetMapping("/api/sys/user/register/{phone}")
    R<UserInfoDTO> registerPhoneUser(@PathVariable("phone") String phone);

    /**
     * 验证手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    @FeignIgnoreAuth
    @ResponseBody
    @GetMapping("/api/sys/user/exists/{phone}")
    R<Boolean> checkPhoneExists(@PathVariable("phone") String phone);

}
