package com.xht.platform.notice.api.factory;

import  com.xht.platform.notice.api.ISysMessageClient;
import  com.xht.platform.notice.api.fallback.SysMessageClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 系统管理-站内信 FallbackFactory
 *
 * @author xht
 **/
@Slf4j
@Component
public class SysMessageClientFallbackFactory implements FallbackFactory<ISysMessageClient> {

    @Override
    public ISysMessageClient create(Throwable cause) {
        log.info("系统管理-站内信接口调用错误: {}", cause.getMessage(), cause);
        return new SysMessageClientFallback(cause);
    }

}
