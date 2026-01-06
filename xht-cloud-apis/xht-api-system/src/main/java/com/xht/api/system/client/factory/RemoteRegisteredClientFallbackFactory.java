package com.xht.api.system.client.factory;

import com.xht.api.system.client.fallback.RemoteRegisteredClientFallback;
import com.xht.api.system.client.feign.RemoteRegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * oauth2 客户端 FallbackFactory
 *
 * @author xht
 **/
@Slf4j
@Component
public class RemoteRegisteredClientFallbackFactory implements FallbackFactory<RemoteRegisteredClientService> {

    @Override
    public RemoteRegisteredClientService create(Throwable cause) {
        log.error("oauth2 客户端服务降级：{}", cause.getMessage(), cause);
        return new RemoteRegisteredClientFallback(cause);
    }

}
