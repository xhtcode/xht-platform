package com.xht.platform.file.properties;

import com.xht.framework.utils.AssertUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件预览配置属性类
 * 读取配置文件中 xht.sys.file.preview 前缀相关配置
 *
 * @author xht
 */
@Data
@Component
@ConfigurationProperties(prefix = "xht.sys.file.preview")
public class FilePreviewProperties {

    /**
     * 用户头像预览地址前缀
     */
    private String userAvatar = "/img/user-avatar/";

    /**
     * 拼接头像访问完整地址
     *
     * @param fileStorageKey 文件存储唯一key
     * @return 拼接完成的头像预览访问地址
     */
    public String getUserAvatar(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, "文件存储key不能为空");
        return userAvatar + fileStorageKey;
    }

}
