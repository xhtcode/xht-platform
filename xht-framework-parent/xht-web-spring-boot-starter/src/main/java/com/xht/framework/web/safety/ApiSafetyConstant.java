package com.xht.framework.web.safety;

/**
 * 描述： API加密相关常量
 *
 * @author xht
 **/
public interface ApiSafetyConstant {

    /**
     * API解密 配置属性 前缀
     */
    String API_DECRYPT_PROPERTIES_PREFIX = "xht.safety.api.decrypt";


    /**
     * API加密 配置属性 前缀
     */
    String API_ENCRYPT_PROPERTIES_PREFIX = "xht.safety.api.encrypt";

    /**
     * API加密 配置属性 类型
     */
    String API_ENCRYPT_PROPERTIES_TYPE = "xht.safety.api.encrypt.type";
}
