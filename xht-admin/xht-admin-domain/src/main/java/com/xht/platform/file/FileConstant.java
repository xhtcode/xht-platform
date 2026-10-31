package com.xht.platform.file;

/**
 * 描述： 文件域常量
 * <p>
 * 统一维护文件接口路径，Controller 映射与预览初始化信息中的访问地址共用同一份常量，避免路径漂移
 *
 * @author xht
 **/
public final class FileConstant {

    /**
     * 文件接口基础路径
     */
    public static final String BASE_PATH = "/sys/files";

    /**
     * 文件预览接口路径（支持 Range 分段请求）
     */
    public static final String PREVIEW_PATH = BASE_PATH + "/preview";

    /**
     * 文件下载接口路径
     */
    public static final String DOWNLOAD_PATH = BASE_PATH + "/download";

    /**
     * 私有构造，禁止实例化
     */
    private FileConstant() {
    }

}
