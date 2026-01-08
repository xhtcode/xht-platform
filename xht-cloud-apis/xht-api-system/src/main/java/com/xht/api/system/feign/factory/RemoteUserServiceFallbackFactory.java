package com.xht.api.system.feign.factory;

import com.xht.api.system.feign.fallback.RemoteUserServiceFallback;
import com.xht.api.system.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级工厂
 *
 * @author xht
 **/
@Slf4j
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable cause) {
        log.error("用户服务降级：{}", cause.getMessage(), cause);
        return new RemoteUserServiceFallback(cause);
    }

}
