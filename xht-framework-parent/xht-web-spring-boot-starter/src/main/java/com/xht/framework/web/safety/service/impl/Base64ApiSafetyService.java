package com.xht.framework.web.safety.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.utils.Base64Utils;
import com.xht.framework.web.safety.autoconfigure.ApiEncryptProperties;
import com.xht.framework.web.safety.service.ApiSafetyService;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述： Base64加密服务实现类
 *
 * @author xht
 **/
@Slf4j
public class Base64ApiSafetyService extends ApiSafetyService {

    private final ObjectMapper objectMapper;

    public Base64ApiSafetyService(ApiEncryptProperties apiEncryptProperties, ObjectMapper objectMapper) {
        super(apiEncryptProperties);
        this.objectMapper = objectMapper;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @return 加密数据
     */
    @Override
    public Object encrypt(Object data) {
        try {
            return Base64Utils.encodeStr(objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            throw new BusinessException("加密失败", e);
        }
    }


}
