package com.xht.modules.admin.notice.api.fallback;

import com.xht.framework.core.domain.R;
import com.xht.framework.openfeign.fallback.BasicFallback;
import com.xht.modules.admin.notice.api.ISysMessageClient;
import com.xht.platform.common.notice.core.MessagePayload;

/**
 * 系统管理-站内信 Fallback
 *
 * @author xht
 **/
public class SysMessageClientFallback extends BasicFallback implements ISysMessageClient {


    /**
     * 构造函数，创建BasicFallback实例并记录错误日志
     *
     * @param cause 异常原因
     */
    public SysMessageClientFallback(Throwable cause) {
        super(cause);
    }

    /**
     * 发送 站内信
     *
     * @param payload 站内信参数
     * @return 发送结果
     */
    @Override
    public R<Void> sendMessage(MessagePayload payload) {
        return error();
    }
}
