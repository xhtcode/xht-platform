package com.xht.framework.openfeign.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.xht.framework.core.properties.SecurityHeaderProperties;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Optional;

import static com.xht.framework.core.constant.HttpConstants.Header.AUTHORIZATION;

/**
 * feign token拦截器
 *
 * @author xht
 **/
@Slf4j
@RequiredArgsConstructor
public class FeignTokenInterceptor implements RequestInterceptor {

    private final SecurityHeaderProperties securityHeaderProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Collection<String> authValues = requestTemplate.headers().get(securityHeaderProperties.getAuthKey());
        // 带 authKeys请求直接跳过
        if (CollUtil.isNotEmpty(authValues) && authValues.contains(securityHeaderProperties.getAuthValue())) {
            log.debug("请求头中包含认证信息, 跳过token拦截器");
            return;
        }
        // 非web 请求直接跳过
        Optional<HttpServletRequest> optRequest = ServletUtil.getOptHttpServletRequest();
        if (optRequest.isEmpty()) {
            log.debug("非web 请求, 跳过token拦截器");
            return;
        }
        HttpServletRequest request = optRequest.get();
        String token = request.getHeader(AUTHORIZATION.getValue());
        if (StringUtils.isEmpty(token)) {
            log.debug("请求头中未包含认证信息, 跳过token拦截器");
            return;
        }
        log.debug("获取token 成功,  token: {}", token);
        requestTemplate.header(AUTHORIZATION.getValue(), token);
    }
}
