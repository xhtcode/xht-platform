package com.xht.cloud.security.feign;

import com.xht.cloud.security.feign.factory.RemoteUserServiceFallbackFactory;
import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.annotation.NoAuthentication;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户查询客户端
 *
 * @author xht
 **/
@FeignClient(
        contextId = "remoteUserService",
        value = ServiceNameConstant.SYSTEM_SERVICE,
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
    @NoAuthentication
    @ResponseBody
    @GetMapping("/api/sys/user/{username}/{loginType}")
    R<BasicUserDetails> loadUserByUsername(@PathVariable("username") String username, @PathVariable("loginType") LoginTypeEnums loginType);

}
