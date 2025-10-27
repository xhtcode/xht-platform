package com.xht.api.system.log.feign.factory;

import com.xht.api.system.log.feign.RemoteLogClientService;
import com.xht.framework.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * 日志保存 FallbackFactory
 *
 * @author xht
 **/
@Slf4j
@SuppressWarnings("unused")
public class RemoteLogClientFallbackFactory implements FallbackFactory<RemoteLogClientService> {

    @Override
    public RemoteLogClientService create(Throwable cause) {
        log.error("日志保存接口调用错误:", cause);
        return clientId -> R.errorMsg("日志保存接口调用失败:" + cause.getMessage());
    }
}
