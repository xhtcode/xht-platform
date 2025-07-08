package com.xht.framework.log.properties;

import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.properties.EnableProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "xht.system.log")
public final class BasicLogProperties extends EnableProperties {

    /**
     * 放行字段
     */
    private Set<String> excludeFields;

    /**
     * 请求报文最大存储长度
     */
    private Integer maxLength = 2000;

    /**
     * 配置
     */
    @NestedConfigurationProperty
    private RemoteClientProperties remote;

    /**
     * @return 放行字段
     */
    public Set<String> getExcludeFields() {
        if (excludeFields == null) {
            excludeFields = new HashSet<>();
        }
        excludeFields.add("password");
        excludeFields.add("mobile");
        excludeFields.add("idCard");
        excludeFields.add("phone");
        excludeFields.add("email");
        return excludeFields;
    }

    @Data
    public static class RemoteClientProperties {
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
        private String url = "/sys/log/save";
    }

}
