package com.xht.api.system.feign.factory;

import com.xht.api.system.feign.RemoteLogClientService;
import com.xht.api.system.feign.fallback.RemoteLogClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 日志保存 FallbackFactory
 *
 * @author xht
 **/
@Slf4j
@Component
public class RemoteLogClientFallbackFactory implements FallbackFactory<RemoteLogClientService> {

    @Override
    public RemoteLogClientService create(Throwable cause) {
        log.error("日志保存接口调用错误: {}", cause.getMessage(), cause);
        return new RemoteLogClientFallback(cause);
    }

}
