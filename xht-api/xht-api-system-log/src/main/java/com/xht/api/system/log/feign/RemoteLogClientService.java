package com.xht.api.system.log.feign;

import com.xht.api.system.log.feign.factory.RemoteLogClientFallbackFactory;
import com.xht.framework.core.domain.R;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志存储
 *
 * @author xht
 **/
// @formatter:off
@FeignClient(
        contextId = "remoteLogClientService",
        value = "xht-system",
        fallbackFactory = RemoteLogClientFallbackFactory.class
)
public interface RemoteLogClientService {

    /**
     * 存储日志
     * @param logDTO 日志保存信息
     * @return 保存结果
     */
    @FeignIgnoreAuth
    @PostMapping("/api/sys/log/save")
    R<Void> saveLog(@RequestBody LogDTO logDTO);
}
// @formatter:on