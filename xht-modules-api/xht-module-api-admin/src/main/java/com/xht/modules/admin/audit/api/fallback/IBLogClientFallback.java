package com.xht.modules.admin.audit.api.fallback;

import com.xht.modules.admin.audit.api.IBLogClient;
import com.xht.framework.core.domain.R;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.openfeign.fallback.BasicFallback;

/**
 * 日志保存 Fallback
 *
 * @author xht
 **/
public class IBLogClientFallback extends BasicFallback implements IBLogClient {

    public IBLogClientFallback(Throwable cause) {
        super(cause);
    }


    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    @Override
    public R<Void> saveLog(BLogDTO bLogDTO) {
        return error();
    }

}
