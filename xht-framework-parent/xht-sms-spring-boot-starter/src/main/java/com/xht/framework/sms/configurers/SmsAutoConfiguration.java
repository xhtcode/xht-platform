package com.xht.framework.sms.configurers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * 短信服务
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class SmsAutoConfiguration {

    public SmsAutoConfiguration() {
        log.debug("【xht-sms-spring-boot-starter】 SmsAutoConfiguration 启动成功！");
    }

}
