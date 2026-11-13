package com.xht.platform.common.file.core;

import java.util.HashMap;

/**
 * 描述： 文件级配置覆盖
 * <p>只保存用户显式覆盖的 key，未覆盖项继续继承用途/全局配置</p>
 *
 * @author xht
 **/
public final class FileConfig extends HashMap<String, Object> {

    /**
     * 允许下载
     */
    public static final String KEY_DOWNLOAD_ALLOWED = "downloadAllowed";
    /**
     * 允许打印
     */
    public static final String KEY_PRINT_ALLOWED = "printAllowed";
}
