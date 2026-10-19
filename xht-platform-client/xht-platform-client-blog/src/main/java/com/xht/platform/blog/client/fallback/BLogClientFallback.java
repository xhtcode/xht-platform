package com.xht.platform.blog.client.fallback;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;
import com.xht.framework.openfeign.fallback.BasicFallback;
import com.xht.platform.blog.client.BLogFeignClient;

/**
 * 日志保存 Fallback
 *
 * @author xht
 **/
public class BLogClientFallback extends BasicFallback implements BLogFeignClient {

    public BLogClientFallback(Throwable cause) {
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
