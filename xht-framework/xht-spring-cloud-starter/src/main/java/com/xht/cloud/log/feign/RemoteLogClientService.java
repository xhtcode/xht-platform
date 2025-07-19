package com.xht.cloud.log.feign;

import com.xht.cloud.log.feign.factory.RemoteLogClientFallbackFactory;
import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.domain.R;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.openfeign.annotation.NoAuthentication;
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
        value = ServiceNameConstant.SYSTEM_SERVICE,
        fallbackFactory = RemoteLogClientFallbackFactory.class
)
public interface RemoteLogClientService {

    /**
     * 存储日志
     * @param logDTO 日志保存信息
     * @return 保存结果
     */
    @NoAuthentication
    @PostMapping("/api/sys/log/save")
    R<Boolean> saveLog(@RequestBody LogDTO logDTO);
}
// @formatter:on