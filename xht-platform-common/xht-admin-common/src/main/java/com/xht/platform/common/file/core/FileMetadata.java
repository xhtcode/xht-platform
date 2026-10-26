package com.xht.platform.common.file.core;

import cn.hutool.core.map.MapUtil;

import java.util.HashMap;

/**
 * 描述： 文件元数据
 *
 * @author xht
 **/
public final class FileMetadata extends HashMap<String, Object> {

    /**
     * 完整原始文件名的元数据键名（原始文件名超长被截断时，用于记录完整名称）
     */
    public static final String FULL_ORIGINAL_FILENAME_KEY = "fullOriginalFilename";

    /**
     * 获取完整原始文件名（原始文件名超长被截断时，用于记录完整名称）
     *
     * @return 完整原始文件名
     */
    public String getFullOriginalFilename() {
        return MapUtil.getStr(this, FULL_ORIGINAL_FILENAME_KEY);
    }

}
