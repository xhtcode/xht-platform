package com.xht.framework.web.safety.service;

import com.xht.framework.web.safety.autoconfigure.ApiEncryptProperties;

/**
 * 描述： api 接口 加解密
 *
 * @author xht
 **/
public abstract class ApiSafetyService {

    protected final ApiEncryptProperties apiEncryptProperties;

    public ApiSafetyService(ApiEncryptProperties apiEncryptProperties) {
        this.apiEncryptProperties = apiEncryptProperties;
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @return 加密数据
     */
    public abstract Object encrypt(Object data);
}
