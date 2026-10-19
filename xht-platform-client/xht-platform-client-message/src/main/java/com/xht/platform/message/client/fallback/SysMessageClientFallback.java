package com.xht.platform.message.client.fallback;

import com.xht.framework.common.domain.R;
import com.xht.framework.openfeign.fallback.BasicFallback;
import com.xht.platform.common.message.core.MessagePayload;
import com.xht.platform.message.client.SysMessageFeignClient;

/**
 * 系统管理-站内信 Fallback
 *
 * @author xht
 **/
public class SysMessageClientFallback extends BasicFallback implements SysMessageFeignClient {

    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public SysMessageClientFallback(Throwable cause) {
        super(cause);
    }

    /**
     * 发送站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @Override
    public R<Void> sendMessage(MessagePayload payload) {
        return error();
    }

}
