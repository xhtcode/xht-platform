package com.xht.framework.file.properties;

import com.xht.framework.file.enums.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Storage properties
 */
@Data
@Component
@ConfigurationProperties(prefix = "xht.file.storage")
public class StorageProperties {

    /**
     * Storage type
     */
    private StorageType type = StorageType.LOCAL;

    /**
     * Local storage properties
     */
    private final Local local = new Local();


    /**
     * Local storage properties
     */
    @Data
    public static class Local {

        /**
         * Local storage base path
         */
        private String basePath = System.getProperty("user.dir") + "/data/uploads";

        /**
         * 文件访问地址前缀（静态资源映射前缀，如 /uploads）
         */
        private String urlPrefix = "/uploads";

    }

}
