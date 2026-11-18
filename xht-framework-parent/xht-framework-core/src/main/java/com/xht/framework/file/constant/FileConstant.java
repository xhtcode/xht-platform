package com.xht.framework.file.constant;

/**
 * 描述： 文件常量
 *
 * @author xht
 **/
public interface FileConstant {

    /**
     * 配置项key
     */
    String CONFIG_KEY = "xht.file.storage";

    /**
     * 存储类型配置项key
     */
    String CONFIG_STORAGE_TYPE_KEY = "xht.file.storage.type";

    /**
     * 默认缓存大小 8192 8KB
     */
    int DEFAULT_BUFFER_SIZE = 2 << 12;

}
