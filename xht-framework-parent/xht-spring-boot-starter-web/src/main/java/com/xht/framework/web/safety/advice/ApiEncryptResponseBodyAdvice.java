package com.xht.framework.web.safety.advice;

import com.xht.framework.common.domain.R;
import com.xht.framework.common.enums.DataTypeEnum;
import com.xht.framework.utils.ThrowUtils;
import com.xht.framework.web.safety.autoconfigure.ApiEncryptProperties;
import com.xht.framework.web.safety.service.ApiSafetyService;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 全局响应加密
 * 职责：对标记了 @ResponseEncrypt 的接口，把响应体 data 部分 AES 加密后返回
 * 前端拿到 data 密文后，用约定密钥解密得到真实数据
 *
 * @author xht
 */
@RestControllerAdvice
public class ApiEncryptResponseBodyAdvice implements ResponseBodyAdvice<R<Object>> {

    private final ApiEncryptProperties apiEncryptProperties;

    private final ApiSafetyService apiSafetyService;

    public ApiEncryptResponseBodyAdvice(ApiEncryptProperties apiEncryptProperties, ApiSafetyService apiSafetyService) {
        this.apiEncryptProperties = apiEncryptProperties;
        this.apiSafetyService = apiSafetyService;
        ThrowUtils.notNull(apiSafetyService, "apiSafetyService must not be null");
    }

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return apiEncryptProperties.isEnable() && returnType.getParameterType().isAssignableFrom(R.class);
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public R<Object> beforeBodyWrite(R<Object> body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (Objects.nonNull(body) && Objects.nonNull(body.getData()) && !Objects.equals(body.getDataType(), DataTypeEnum.ENCRYPT)) {
            body.setData(apiSafetyService.encrypt(body.getData()));
            return body;
        }
        return body;
    }

}