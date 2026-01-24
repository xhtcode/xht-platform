package com.xht.modules.admin.config;

import com.xht.framework.core.support.blog.BLogEvent;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.modules.admin.audit.service.IBLogService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * bLog 日志监听器
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class BLogApplicationListener implements ApplicationListener<BLogEvent> {

    private final IBLogService ibLogService;

    /**
     * 监听bLog事件
     *
     * @param bLogEvent bLog事件对象
     */
    @Override
    public void onApplicationEvent(@NonNull BLogEvent bLogEvent) {
        BLogDTO bLogDTO = bLogEvent.getBLogDTO();
        ibLogService.create(bLogDTO);
    }

}
