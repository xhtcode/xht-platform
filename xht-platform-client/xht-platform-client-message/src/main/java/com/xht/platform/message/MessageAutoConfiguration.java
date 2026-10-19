package com.xht.platform.message;

import com.xht.platform.common.message.IMessagePublisher;
import com.xht.platform.message.client.SysMessageFeignClient;
import com.xht.platform.message.client.factory.SysMessageClientFallbackFactory;
import com.xht.platform.message.impl.MessageClientPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = SysMessageFeignClient.class)
public class MessageAutoConfiguration {

    public MessageAutoConfiguration() {
        log.debug("系统消息自动配置启动");
    }

    /**
     * 站内信降级工厂
     * <p>
     * 显式注册而非 {@code @Component}，避免把降级的装配责任推给调用方的组件扫描。
     *
     * @return 降级工厂
     */
    @Bean
    public SysMessageClientFallbackFactory sysMessageClientFallbackFactory() {
        return new SysMessageClientFallbackFactory();
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
