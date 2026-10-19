package com.xht.platform.common.audit.api;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.dto.BLogDTO;

/**
 * 描述：日志存储契约
 * <p>
 * 只声明方法签名与出入参，不携带 Feign / Web 注解；Feign 客户端为
 * {@code com.xht.platform.blog.client.BLogFeignClient}，服务端实现为
 * {@code com.xht.platform.client.blog.BLogClientImpl}。
 *
 * @author xht
 **/
public interface IBLogClient {

    /**
     * 存储日志
     *
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    R<Void> saveLog(BLogDTO bLogDTO);

}
