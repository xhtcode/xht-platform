package com.xht.platform.common.message.api;

import com.xht.framework.common.domain.R;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.message.api.factory.SysMessageClientFallbackFactory;
import com.xht.platform.common.message.core.MessagePayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述 ： 系统管理-站内信
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "sysMessageClient",
        path = "/api/sys/message",
        fallbackFactory = SysMessageClientFallbackFactory.class
)
public interface ISysMessageClient {

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @FeignIgnoreAuth
    @PostMapping("/send")
    R<Void> sendMessage(@RequestBody MessagePayload payload);

}