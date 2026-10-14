package com.xht.framework.file.autoconfigure;

import com.xht.framework.file.annotations.ConditionalOnFile;
import com.xht.framework.file.configurers.StorageProperties;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.LocalFileStorageRepository;
import com.xht.framework.file.enums.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 描述： 文件存储自动配置类
 *
 * @author xht
 **/
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(StorageProperties.class)
public class FileStorageAutoConfiguration {

    /**
     * 本地文件存储仓库
     *
     * @param storageProperties 存储属性
     * @return 文件存储仓库
     */
    @Bean
    @ConditionalOnFile(StorageType.LOCAL)
    public FileStorageRepository localFileStorageRepository(StorageProperties storageProperties) {
        return new LocalFileStorageRepository(storageProperties);
    }

}
