package com.xht.framework.oauth2.properties;

import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.properties.IProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.security.oauth2.remote")
public class RemoteRegisteredClientProperties implements Serializable, IProperties {

    /**
     * 服务名称唯一
     */
    private String contextId = "remoteLogClientService";

    /**
     * 服务名称
     */
    private String serviceName = ServiceNameConstant.SYSTEM_SERVICE;

    /**
     * 链接地址
     */
    private String url = "/client/getClientDetailsById";
}
