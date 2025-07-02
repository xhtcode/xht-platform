package com.xht.boot.log.feign;

import com.xht.framework.core.domain.R;
import com.xht.framework.core.properties.BasicFeignProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志存储
 *
 * @author xht
 **/
@FeignClient(contextId = "${xht.system.log.remote.context-id:remoteLogClientService}", value = "${xht.system.log.remote.service-name:xht-system}")
public interface RemoteLogClientService {

    @GetMapping("${xht.system.log.remote.url:/sys/log/save}")
    R<Boolean> saveLog(@RequestBody BasicFeignProperties client);

}
