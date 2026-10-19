package com.xht.framework.security.interceptor;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.exception.IgnoreAuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link IgnoreAuthInterceptor} 单元测试
 *
 * @author xht
 **/
class IgnoreAuthInterceptorTest {

    private static final String AUTH_KEY = "X-Auth-Token";

    private static final String AUTH_VALUE = "123456";

    private SecurityHeaderProperties properties;

    private IgnoreAuthInterceptor interceptor;

    @BeforeEach
    void setUp() {
        properties = new SecurityHeaderProperties();
        properties.getHeader().setAuthKey(AUTH_KEY);
        properties.getHeader().setAuthValue(AUTH_VALUE);
        interceptor = new IgnoreAuthInterceptor(properties);
    }

    @Test
    void shouldRejectWhenAnnotationOnlyOnInterfaceAndHeaderMissing() throws Exception {
        assertThrows(IgnoreAuthException.class, () -> invoke(new InternalApiImpl(), "internal", null));
    }

    @Test
    void shouldPassWhenAnnotationOnlyOnInterfaceAndHeaderMatched() throws Exception {
        assertTrue(invoke(new InternalApiImpl(), "internal", AUTH_VALUE));
    }

    @Test
    void shouldPassWhenImplOverridesAopToFalse() throws Exception {
        assertTrue(invoke(new OverrideController(), "internal", null));
    }

    @Test
    void shouldPassWhenNoAnnotation() throws Exception {
        assertTrue(invoke(new PlainController(), "plain", null));
    }

    @Test
    void shouldPassWhenAnnotationDeclaredOnClass() throws Exception {
        assertTrue(invoke(new ClassLevelController(), "open", null));
    }

    @Test
    void shouldRejectWhenAuthValueNotConfigured() throws Exception {
        properties.getHeader().setAuthValue("");
        interceptor = new IgnoreAuthInterceptor(properties);
        assertThrows(IgnoreAuthException.class, () -> invoke(new InternalApiImpl(), "internal", AUTH_VALUE));
    }

    @Test
    void shouldRejectWhenHeaderNotMatched() throws Exception {
        assertThrows(IgnoreAuthException.class, () -> invoke(new InternalApiImpl(), "internal", "wrong"));
    }

    @Test
    void shouldPassWhenHandlerIsNotHandlerMethod() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/demo");
        assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
    }

    private boolean invoke(Object bean, String methodName, String headerValue) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/demo");
        if (headerValue != null) {
            request.addHeader(AUTH_KEY, headerValue);
        }
        HandlerMethod handlerMethod = new HandlerMethod(bean, bean.getClass().getMethod(methodName));
        return interceptor.preHandle(request, new MockHttpServletResponse(), handlerMethod);
    }

    /**
     * 内部接口契约：只在接口方法上标注注解
     */
    interface InternalApi {

        @IgnoreAuth
        String internal();

    }

    /**
     * 实现类漏标注解，注解继承自接口
     */
    static class InternalApiImpl implements InternalApi {

        @Override
        public String internal() {
            return "ok";
        }

    }

    /**
     * 实现类的注解优先于接口
     */
    static class OverrideController implements InternalApi {

        @Override
        @IgnoreAuth(aop = false)
        public String internal() {
            return "ok";
        }

    }

    /**
     * 类级注解：只进白名单，不校验内部头
     */
    @IgnoreAuth
    static class ClassLevelController {

        public String open() {
            return "ok";
        }

    }

    /**
     * 无任何注解
     */
    static class PlainController {

        public String plain() {
            return "ok";
        }

    }

}
