package com.xht.platform.message;

import com.xht.platform.common.message.IMessagePublisher;
import com.xht.platform.common.message.api.ISysMessageClient;
import com.xht.platform.message.impl.MessageClientPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@ComponentScan(value = "com.xht.platform.common.message.api.factory")
@EnableFeignClients(clients = ISysMessageClient.class)
public class MessageAutoConfiguration {

    public MessageAutoConfiguration() {
        log.debug("系统消息自动配置启动");
    }

    /**
     * 创建消息发布者
     *
     * @return 消息发布者
     */
    @Bean
    public IMessagePublisher messageClientPublisher() {
        return new MessageClientPublisher();
    }

}
