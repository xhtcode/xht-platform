package com.xht.framework.file.configurers;

import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.file.enums.StorageType;
import com.xht.framework.utils.StringUtils;
import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Storage properties
 */
@Data
@ConfigurationProperties(prefix = FileConstant.CONFIG_KEY)
public class StorageProperties {

    /**
     * 存储类型
     */
    private StorageType type = StorageType.LOCAL;

    /**
     * 文件访问地址前缀（静态资源映射前缀，如 /uploads）
     */
    private String viewPrefix = "/uploads";

    /**
     * 本地存储配置
     */
    private final Local local = new Local();


    /**
     * 本地存储配置
     */
    @Setter
    public static class Local {

        /**
         * 本地存储基础路径
         */
        private String basePath;

        /**
         * 获取本地存储基础路径
         *
         * @return 本地存储基础路径
         */
        public String getBasePath() {
            return StringUtils.emptyToDefault(this.basePath, System.getProperty("user.dir"));
        }

    }

}
