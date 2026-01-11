package com.xht.api.system.listener;

import com.xht.api.system.feign.RemoteLogClientService;
import com.xht.framework.core.support.blog.BLogEvent;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * bLog 日志监听器
 *
 * @author xht
 **/
@Slf4j
public class BLogApplicationListener implements ApplicationListener<BLogEvent> {

    @Resource
    private RemoteLogClientService remoteLogClientService;

    @Override
    public void onApplicationEvent(@NonNull BLogEvent event) {
        BLogDTO bLogDTO = event.getBLogDTO();
        remoteLogClientService.saveLog(bLogDTO);
    }

}
