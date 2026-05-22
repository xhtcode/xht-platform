package com.xht.modules.admin.login;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 登录配置
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties("xht.oauth2.login")
public class LoginProperties implements Serializable {

    /**
     * 登录授权地址
     */
    private String loginUrl;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private List<String> scope;

}
