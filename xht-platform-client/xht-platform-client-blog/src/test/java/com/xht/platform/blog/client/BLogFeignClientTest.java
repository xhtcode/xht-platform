package com.xht.platform.blog.client;

import com.xht.platform.blog.BLogAutoConfiguration;
import com.xht.platform.blog.client.factory.BLogClientFallbackFactory;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 描述：日志存储契约与 Feign 客户端配置测试
 * <p>
 * 契约本身（{@code com.xht.platform.common.audit.api.IBLogClient}）已不含 Feign 注解，
 * 因此这些断言随客户端一起迁到本模块。
 *
 * @author xht
 **/
class BLogFeignClientTest {

    @Test
    void feignClientShouldPointToOwnFallbackFactory() {
        FeignClient feignClient = BLogFeignClient.class.getAnnotation(FeignClient.class);
        assertNotNull(feignClient, "Feign 子接口必须标注 @FeignClient");
        assertEquals(BLogClientFallbackFactory.class, feignClient.fallbackFactory(),
                "降级工厂必须指向本契约的工厂，禁止复用其它契约的工厂");
    }

    @Test
    void fallbackFactoryShouldBeRegisteredAsBean() throws NoSuchMethodException {
        Method method = BLogAutoConfiguration.class.getMethod("bLogClientFallbackFactory");
        assertNotNull(method.getAnnotation(Bean.class),
                "降级工厂必须由自动配置以 @Bean 注册，否则 fallbackFactory 属性解析不到");
        assertEquals(BLogClientFallbackFactory.class, method.getReturnType());
    }

    @Test
    void fallbackFactoryShouldBindToFeignClient() {
        ParameterizedType type = (ParameterizedType) BLogClientFallbackFactory.class.getGenericInterfaces()[0];
        assertEquals(BLogFeignClient.class, type.getActualTypeArguments()[0],
                "降级工厂的泛型实参必须是 Feign 子接口（fallback 需实现该接口才能被代理调用）");
    }

}
