package com.xht.api.system.user.feign.factory;

import com.xht.api.system.user.dto.UserInfoDTO;
import com.xht.api.system.user.feign.RemoteUserService;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.enums.LoginTypeEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 用户服务降级工厂
 *
 * @author xht
 **/
@Slf4j
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("用户查询服务调用失败:{}", cause.getMessage(), cause);
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
                return R.errorMsg("客户端认证服务调用失败");
            }

            /**
             * 注册手机用户
             *
             * @param phone 手机号
             * @return 注册用户信息
             */
            @Override
            public R<UserInfoDTO> registerPhoneUser(String phone) {
                return R.errorMsg("用户注册失败");
            }

            /**
             * 验证手机号是否存在
             *
             * @param phone 手机号
             * @return 是否存在
             */
            @Override
            public R<Boolean> checkPhoneExists(String phone) {
                return R.errorData(false);
            }
        };
    }
}
