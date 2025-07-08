package com.xht.framework.web.xss;

import com.xht.framework.core.properties.EnableProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述 ：xss 配置
 *
 * @author xht
 **/
@Data
@ConfigurationProperties(prefix = "xht.safety.xss")
public class XSSProperties extends EnableProperties {

    /**
     * 需要排除的 URL，默认为空 白名单
     */
    private List<String> excludeUrls = new ArrayList<>();

}
