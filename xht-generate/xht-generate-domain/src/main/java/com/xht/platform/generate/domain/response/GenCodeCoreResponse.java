package com.xht.platform.generate.domain.response;

import lombok.Data;

import java.util.Set;

/**
 * 代码生成核心业务对象
 *
 * @author xht
 **/
@Data
public final class GenCodeCoreResponse {

    /**
     * 忽略的字段 逗号分割
     */
    private Set<String> ignoreField;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 生成的代码内容
     */
    private String code;

    /**
     * 文件类型
     */
    private String fileType;

}


