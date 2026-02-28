package com.xht.modules.admin;

import com.xht.framework.core.support.dict.ISysDictFactory;
import com.xht.framework.core.support.message.IMessagePublisher;
import com.xht.framework.log.repository.BLogRepository;
import com.xht.modules.admin.audit.BLogRepositoryImpl;
import com.xht.modules.admin.dict.SysDictApiFactory;
import com.xht.modules.admin.notice.MessageClientPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@ComponentScan(value = "com.xht.modules.admin.*.api.factory")
@Configuration(proxyBeanMethods = false)
@EnableFeignClients(value = "com.xht.modules.admin.*.api")
public class ApiSystemAutoConfiguration {

    public ApiSystemAutoConfiguration() {
        log.debug("系统API自动配置启动");
    }

    /**
     * feign 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    public BLogRepository bLogRepository() {
        return new BLogRepositoryImpl();
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

    /**
     * 创建字典项查询服务工厂
     *
     * @return 字典项查询服务工厂
     */
    public ISysDictFactory sysDictClient() {
        return new SysDictApiFactory();
    }

}
