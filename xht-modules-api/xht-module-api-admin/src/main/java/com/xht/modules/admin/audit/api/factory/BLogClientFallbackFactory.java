package com.xht.modules.admin.audit.api.factory;

import com.xht.modules.admin.audit.api.BLogClientService;
import com.xht.modules.admin.audit.api.fallback.BLogClientFallback;
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
public class BLogClientFallbackFactory implements FallbackFactory<BLogClientService> {

    @Override
    public BLogClientService create(Throwable cause) {
        log.error("日志保存接口调用错误: {}", cause.getMessage(), cause);
        return new BLogClientFallback(cause);
    }

}
