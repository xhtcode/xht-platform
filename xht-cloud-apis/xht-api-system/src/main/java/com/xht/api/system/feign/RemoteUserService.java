package com.xht.api.system.feign;

import com.xht.api.system.domain.vo.UserLoginVo;
import com.xht.api.system.feign.factory.RemoteUserServiceFallbackFactory;
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
    R<UserLoginVo> loadUserByUsername(@PathVariable("username") String username, @PathVariable("loginType") LoginTypeEnums loginType);

}
