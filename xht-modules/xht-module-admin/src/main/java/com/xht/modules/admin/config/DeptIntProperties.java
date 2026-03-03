package com.xht.modules.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.dept.init")
public class DeptIntProperties {

    public List<String> postName;

}
