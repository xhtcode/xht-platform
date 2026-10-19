package com.xht.platform.common.message.api;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.message.core.MessagePayload;

/**
 * 描述：系统管理-站内信契约
 * <p>
 * 只声明方法签名与出入参，不携带 Feign / Web 注解；Feign 客户端为
 * {@code com.xht.platform.message.client.SysMessageFeignClient}，服务端实现为
 * {@code com.xht.platform.client.message.SysMessageClientImpl}。
 *
 * @author xht
 **/
public interface ISysMessageClient {

    /**
     * 发送站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    R<Void> sendMessage(MessagePayload payload);

}
