package com.xht.framework.web.safety.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.framework.web.safety.advice.ApiEncryptResponseBodyAdvice;
import com.xht.framework.web.safety.service.ApiSafetyService;
import com.xht.framework.web.safety.service.impl.Base64ApiSafetyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

/**
 * 描述： API安全自动配置类
 *
 * @author xht
 **/
@AutoConfiguration
@EnableConfigurationProperties({ApiDecryptProperties.class, ApiEncryptProperties.class})
public class ApiEncryptAutoConfigure {

    /**
     * 创建 EncryptResponseBodyAdvice Bean，解决接口加密问题
     *
     * @param apiEncryptProperties 加密配置
     * @param apiSafetyService     加密服务
     * @return 加密ResponseBodyAdvice
     */
    @Bean
    public ApiEncryptResponseBodyAdvice encryptResponseBodyAdvice(ApiEncryptProperties apiEncryptProperties,
                                                                  ObjectMapper objectMapper,
                                                                  @Autowired(required = false) ApiSafetyService apiSafetyService) {
        String encryptType = apiEncryptProperties.getEncryptType();
        if (Objects.isNull(apiSafetyService)) {
            switch (encryptType) {
                case "AES":
                    break;
                case "BASE64":
                    apiSafetyService = new Base64ApiSafetyService(apiEncryptProperties, objectMapper);
                    break;
                default:
                    apiSafetyService = new ApiSafetyService(apiEncryptProperties) {
                        @Override
                        public Object encrypt(Object data) {
                            return data;
                        }
                    };
                    break;
            }
        }
        return new ApiEncryptResponseBodyAdvice(apiEncryptProperties, apiSafetyService);
    }

}
