package com.xht.platform.file;

import java.util.HashMap;

/**
 * 描述： 文件元数据
 *
 * @author xht
 **/
public class FileMetadata extends HashMap<String, String> {

    /**
     * 创建一个空的文件元数据
     *
     * @return 文件元数据
     */
    public static FileMetadata empty() {
        return new FileMetadata();
    }
}
