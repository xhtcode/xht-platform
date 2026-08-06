package com.xht.platform.blog.api;

import com.xht.framework.common.domain.R;
import com.xht.platform.common.blog.dto.BLogDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 日志存储
 *
 * @author xht
 **/
public interface IBLogClient {

    /**
     * 存储日志
     * @param bLogDTO 日志保存信息
     * @return 保存结果
     */
    R<Void> saveLog(@RequestBody BLogDTO bLogDTO);

}