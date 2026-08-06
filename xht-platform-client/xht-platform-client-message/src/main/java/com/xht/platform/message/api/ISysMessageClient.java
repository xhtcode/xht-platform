package com.xht.platform.message.api;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.message.core.MessagePayload;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 描述 ： 系统管理-站内信
 *
 * @author xht
 **/
public interface ISysMessageClient {

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    R<Void> sendMessage(@RequestBody MessagePayload payload);

}