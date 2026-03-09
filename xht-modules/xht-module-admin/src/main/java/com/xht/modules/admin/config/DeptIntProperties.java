package com.xht.modules.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 部门岗位初始化属性
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties(prefix = "xht.dept.post.init")
public class DeptIntProperties {

    /**
     * 岗位名称
     */
    public String postName = "主管" ;

    /**
     * 岗位描述
     */
    public String remark = "部门管理岗位";

}
