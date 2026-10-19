package com.xht.platform.message.client;

import com.xht.framework.common.domain.R;
import com.xht.framework.openfeign.annotation.FeignIgnoreAuth;
import com.xht.platform.common.AdminConstants;
import com.xht.platform.common.message.api.ISysMessageClient;
import com.xht.platform.common.message.core.MessagePayload;
import com.xht.platform.message.client.factory.SysMessageClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述：系统管理-站内信的 Feign 客户端
 * <p>
 * 契约（方法签名与出入参）来自 {@link ISysMessageClient}，本接口只承载 Feign 与 Web 注解，
 * 因此 common 不需要依赖 openfeign。方法注解必须在子接口重声明，不依赖父接口注解继承。
 *
 * @author xht
 **/
@FeignClient(
        name = AdminConstants.APPLICATION_NAME,
        contextId = "sysMessageClient",
        path = "/api/sys/message",
        fallbackFactory = SysMessageClientFallbackFactory.class
)
public interface SysMessageFeignClient extends ISysMessageClient {

    /**
     * 发送站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @Override
    @FeignIgnoreAuth
    @PostMapping("/send")
    R<Void> sendMessage(@RequestBody MessagePayload payload);

}
