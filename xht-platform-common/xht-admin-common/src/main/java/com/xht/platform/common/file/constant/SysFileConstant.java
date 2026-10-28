package com.xht.platform.common.file.constant;

/**
 * 描述：
 *
 * @author xht
 **/
public interface SysFileConstant {

    /**
     * 目录
     */
    String PATH_POINT = "..";


    /**
     * 文件占位符
     */
    String FILE_NAME_PLACEHOLDER = "***";

    /**
     * 文件占位符长度
     */
    int FILE_NAME_PLACEHOLDER_LENGTH = FILE_NAME_PLACEHOLDER.length();

    /**
     * 分片临时目录名（相对于存储根路径：{chunks}/{uploadId}/{chunkIndex}.part）
     */
    String CHUNK_DIR = "chunks";

    /**
     * 分片文件后缀
     */
    String CHUNK_PART_SUFFIX = ".part";

    /**
     * 分片上传标识合法格式（字母、数字、中划线，长度8~64，防路径穿越）
     */
    String UPLOAD_ID_PATTERN = "^[a-zA-Z0-9-]{8,64}$";
}
