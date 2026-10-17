package com.xht.platform.common.audit.api;

import com.xht.platform.common.audit.api.factory.BLogClientFallbackFactory;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 描述：日志存储契约配置测试
 *
 * @author xht
 **/
class IBLogClientTest {

    @Test
    void fallbackFactoryShouldPointToOwnFactory() {
        FeignClient feignClient = IBLogClient.class.getAnnotation(FeignClient.class);
        assertEquals(BLogClientFallbackFactory.class, feignClient.fallbackFactory(),
                "降级工厂必须指向本契约的工厂，禁止复用其它契约的工厂");
        assertTrue(BLogClientFallbackFactory.class.isAnnotationPresent(Component.class),
                "降级工厂必须注册为 bean，否则 fallbackFactory 属性解析不到");
    }

    @Test
    void fallbackFactoryShouldBindToOwnContract() {
        ParameterizedType type = (ParameterizedType) BLogClientFallbackFactory.class.getGenericInterfaces()[0];
        assertEquals(IBLogClient.class, type.getActualTypeArguments()[0],
                "降级工厂的泛型实参必须是 IBLogClient");
    }

}
