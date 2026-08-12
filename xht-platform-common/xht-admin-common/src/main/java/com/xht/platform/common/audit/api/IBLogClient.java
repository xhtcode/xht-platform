package com.xht.platform.common.audit.api;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.dict.api.factory.ISysDictClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述：日志存储
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "bLogClient",
        path = "/api/sys/log",
        fallbackFactory = ISysDictClientFallbackFactory.class
)
public interface IBLogClient {

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @FeignIgnoreAuth
    @PostMapping("/save")
    R<Void> saveLog(@RequestBody BLogDTO bLogDTO);

}