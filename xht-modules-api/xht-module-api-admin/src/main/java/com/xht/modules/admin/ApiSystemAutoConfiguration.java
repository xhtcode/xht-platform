package com.xht.modules.admin;

import com.xht.modules.admin.dict.SysDictApiFactory;
import com.xht.modules.admin.listener.BLogApplicationListener;
import com.xht.modules.admin.notice.MessageClientPublisher;
import com.xht.platform.common.dict.ISysDictFactory;
import com.xht.platform.common.notice.IMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统API自动配置
 *
 * @author xht
 **/
@Slf4j
@ComponentScan(value = "com.xht.modules.admin.*.api.factory")
@AutoConfiguration
@EnableFeignClients(value = "com.xht.modules.admin.*.api")
public class ApiSystemAutoConfiguration {

    /**
     * feign 方式的 blog 日志监听器
     *
     * @return blog 日志监听器
     */
    @Bean
    @ConditionalOnProperty(value = "xht.blog.repository-type", havingValue = "feign")
    public BLogApplicationListener feignBLogApplicationListener() {
        return new BLogApplicationListener();
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
    @Bean
    public ISysDictFactory sysDictClient() {
        return new SysDictApiFactory();
    }

}
