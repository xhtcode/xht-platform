package com.xht.cloud.security.feign.factory;

import com.xht.cloud.oauth2.dto.UserInfoDTO;
import com.xht.cloud.security.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.security.constant.enums.LoginTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 用户查询服务降级工厂
 *
 * @author xht
 **/
@Slf4j
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        return new RemoteUserService() {

            /**
             * 根据用户名和登录类型获取用户信息
             *
             * @param username  用户名
             * @param loginType 登录类型
             * @return 用户信息
             */
            @Override
            public R<UserInfoDTO> loadUserByUsername(String username, LoginTypeEnums loginType) {
                log.error("客户端认证服务调用失败: {}", cause.getMessage(), cause);
                return R.errorMsg("客户端认证服务调用失败:");
            }
        };
    }
}
