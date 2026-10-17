package com.xht.platform.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * 描述： 孤儿文件清理配置
 * <p>
 * 定时清理"上传后长期未关联业务"的临时文件，防止存储只增不减
 *
 * @author xht
 **/
@Data
@Component
@ConfigurationProperties("xht.file.clean")
public class FileCleanProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否启用定时清理
     */
    private boolean enabled = true;

    /**
     * 过期天数：上传时间超过该天数仍未关联业务的文件视为孤儿文件
     */
    private int expireDays = 3;

    /**
     * 单轮处理批量大小
     */
    private int batchSize = 500;

}
