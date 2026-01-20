package com.xht.framework.core.properties.log;

import com.xht.framework.core.enums.CallTypeEnums;
import lombok.Data;

/**
 * 日志配置
 *
 * @author xht
 **/
@Data
public class BLogProperties {

    /**
     * 日志存储类型
     */
    private CallTypeEnums repositoryType = CallTypeEnums.JDBC;

}
