package com.xht.boot.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * xht-boot 自动配置
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
public class XhtBootAutoConfiguration {

    public XhtBootAutoConfiguration() {
        log.debug("[xht] |- xht-boot 启动成功！");
    }
}
