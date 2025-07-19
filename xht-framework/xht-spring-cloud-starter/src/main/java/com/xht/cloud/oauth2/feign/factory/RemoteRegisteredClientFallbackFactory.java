package com.xht.cloud.oauth2.feign.factory;

import com.xht.cloud.oauth2.feign.RemoteRegisteredClientService;
import com.xht.framework.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author xht
 **/
@Slf4j
@SuppressWarnings("unused")
public class RemoteRegisteredClientFallbackFactory implements FallbackFactory<RemoteRegisteredClientService> {

    @Override
    public RemoteRegisteredClientService create(Throwable cause) {
        log.error("客户端认证服务调用失败:", cause);
        return clientId -> R.errorMsg("客户端认证服务调用失败:");
    }
}
