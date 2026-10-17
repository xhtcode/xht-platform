package com.xht.platform.common.file.constant;

/**
 * 描述：
 *
 * @author xht
 **/
public interface SysFileConstant {

    /**
     * 默认的文件前缀正则
     */
    String DEFAULT_PREFIX_REGEX = "^[/\\\\]+";

    /**
     * 文件下载地址前缀
     */
    String URL_DOWNLOAD_PREFIX = "/sys/files/download/";

    /**
     * 文件预览地址前缀
     */
    String URL_PREVIEW_PREFIX = "/sys/files/preview/";

    /**
     * 缩略图预览地址前缀
     */
    String URL_THUMBNAIL_PREFIX = "/sys/files/thumbnail/";

    /**
     * 缩略图名称标识（拼接在存储 key 之后）
     */
    String THUMBNAIL_NAME_FLAG = "_thumb";

    /**
     * 兜底上传用户名（无法获取登录用户时使用）
     */
    String DEFAULT_UPLOAD_USER = "anonymous";

    /**
     * 兜底文件名（原始文件名为空时使用）
     */
    String DEFAULT_FILE_NAME = "unnamed";

}
