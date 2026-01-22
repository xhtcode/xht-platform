package com.xht.modules.admin.audit.api.factory;

import com.xht.modules.admin.audit.api.IBLogClient;
import com.xht.modules.admin.audit.api.fallback.IBLogClientFallback;
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
public class BLogClientFallbackFactory implements FallbackFactory<IBLogClient> {

    @Override
    public IBLogClient create(Throwable cause) {
        log.error("日志保存接口调用错误: {}", cause.getMessage(), cause);
        return new IBLogClientFallback(cause);
    }

}
