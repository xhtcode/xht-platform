package com.xht.modules.admin.notice.api;

import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.modules.admin.audit.api.factory.BLogClientFallbackFactory;
import com.xht.framework.core.support.message.core.MessagePayload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述 ： 系统管理-站内信
 *
 * @author xht
 **/
@FeignClient(
        contextId = "sysMessageClient",
        value = "xht-module-admin",
        fallbackFactory = BLogClientFallbackFactory.class
)
public interface ISysMessageClient {

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @FeignIgnoreAuth
    @PostMapping("/api/sys/message/send")
    R<Void> sendMessage(@RequestBody MessagePayload payload);

}
