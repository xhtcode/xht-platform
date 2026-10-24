package com.xht.framework.security.interceptor;

import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.security.annotation.IgnoreAuth;
import com.xht.framework.security.exception.IgnoreAuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * 内部接口权限拦截器
 *
 * <p>负责校验标注了 {@link IgnoreAuth} 且 {@code aop = true} 的接口是否携带内部调用头。</p>
 *
 * <p>为什么不用 AOP：{@code @annotation(...)} 切点只匹配目标方法自身声明的注解，无法识别契约接口
 * （{@code IXxxClient}）方法上的注解；而白名单 {@code PermitAllUrlProperties} 使用
 * {@code AnnotationUtils.findAnnotation} 会沿接口查找。两者口径不一致时，实现类漏标注解会导致
 * 「URL 已进白名单、但内部头校验被跳过」的 fail-open。此处改用 {@link HandlerMethod#getMethodAnnotation}
 * 显式解析，与白名单保持同一套查找规则。</p>
 *
 * @author xht
 **/
@Slf4j
public class IgnoreAuthInterceptor implements HandlerInterceptor {

    private final SecurityHeaderProperties securityHeaderProperties;

    public IgnoreAuthInterceptor(SecurityHeaderProperties securityHeaderProperties) {
        this.securityHeaderProperties = securityHeaderProperties;
        if (!StringUtils.hasText(securityHeaderProperties.getAuthValue())) {
            log.error("【xht-security-spring-boot-starter】未配置 xht.security.header.auth-value，所有 @IgnoreAuth(aop = true) 的内部接口都将拒绝访问");
        }
    }

    /**
     * 内部调用头校验
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 是否放行
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            // 静态资源、CORS 预检等非控制器处理，直接放行
            return true;
        }
        // 方法级注解：findAnnotation 语义，会沿实现类继承的契约接口方法查找
        // 类级 @IgnoreAuth 只负责把 URL 加入白名单，不触发内部头校验
        IgnoreAuth ignoreAuth = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
        if (ignoreAuth == null || !ignoreAuth.aop()) {
            return true;
        }
        String authValue = securityHeaderProperties.getAuthValue();
        if (!StringUtils.hasText(authValue)) {
            // fail-closed：不能退化成 equals(null, null) == true 的静默放行
            log.error("请求地址'{}'，内部调用头未配置（xht.security.header.auth-value），拒绝访问", request.getRequestURI());
            throw new IgnoreAuthException("内部调用头未配置，不允许访问");
        }
        String actual = request.getHeader(securityHeaderProperties.getAuthKey());
        if (!Objects.equals(authValue, actual)) {
            log.warn("请求地址'{}'，没有内部访问权限", request.getRequestURI());
            throw new IgnoreAuthException("没有内部访问权限，不允许访问");
        }
        return true;
    }

}
