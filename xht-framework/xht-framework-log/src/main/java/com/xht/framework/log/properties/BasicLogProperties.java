package com.xht.framework.log.properties;

import com.xht.framework.core.constant.ServiceNameConstant;
import com.xht.framework.core.domain.properties.CommonProperties;
import com.xht.framework.core.properties.BasicFeignProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ConfigurationProperties("xht.system.log")
public final class BasicLogProperties extends CommonProperties {

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
    private BasicFeignProperties remote = new BasicFeignProperties("remoteLogClientService", ServiceNameConstant.SYSTEM_SERVICE, "/sys/log/save");

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
}
