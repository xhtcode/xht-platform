package com.xht.framework.file.core;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * 上传入参抽象BO
 * 定义文件上传基础入参规范，由不同上传场景子类实现
 * 实现{@link IUploadInfoBO}统一标记上传BO类型
 *
 * @author xht
 */
public abstract class AbstractUploadBO<T> implements IUploadInfoBO {

    /**
     * 文件占位符
     */
    private static final String FILE_NAME_PLACEHOLDER = "***";

    /**
     * 文件占位符长度
     */
    private static final int FILE_NAME_PLACEHOLDER_LENGTH = FILE_NAME_PLACEHOLDER.length();

    /**
     * HTTP请求对象
     */
    protected final HttpServletRequest servletRequest;

    /**
     * 文件源对象，泛型支持MultipartFile、字节数组等多种文件类型
     */
    protected final T requestFile;

    /**
     * 文件原始名称（带后缀）
     */
    protected String fileOriginalName;

    /**
     * 文件字节大小
     */
    protected long fileSize;

    /**
     * 格式化文件大小展示文本，例如：10.2MB
     */
    protected String fileSizeInfo;

    /**
     * 文件后缀，不带点，例如：jpg、pdf
     */
    protected String fileSuffix;

    /**
     * 文件MIME类型，例如：image/jpeg
     */
    protected String contentType;

    /**
     * 文件用途标识
     */
    @Setter
    private String fileUsed;

    /**
     * 文件存储路径前缀
     */
    @Setter
    private String filePathPrefix;

    /**
     * 文件描述信息
     */
    @Setter
    private String fileDesc;

    /**
     * 文件上传人账号/标识
     */
    @Setter
    private String fileUploadUser;

    /**
     * 构造方法
     * 传入请求与文件源对象，自动调用子类init方法解析文件信息
     *
     * @param servletRequest HTTP请求对象
     * @param requestFile    上传的文件源对象
     */
    protected AbstractUploadBO(HttpServletRequest servletRequest, T requestFile) {
        AssertUtils.notNull(requestFile, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        this.servletRequest = servletRequest;
        this.requestFile = requestFile;
        init(requestFile);
        this.fileOriginalName = formatFileOriginalName(this.fileOriginalName, this.fileSuffix);
    }

    /**
     * 获取文件名
     *
     * @param originalFilename 原文件名
     * @param fileSuffix       文件后缀
     * @return 文件名
     */
    private String formatFileOriginalName(String originalFilename, String fileSuffix) {
        AssertUtils.hasText(originalFilename, "文件名不能为空");
        originalFilename = StringUtils.replace(originalFilename, fileSuffix, StringConstant.EMPTY);
        String name = FileUtil.getName(originalFilename);
        int lastIndex = -1;
        if (StringUtils.hasText(fileSuffix)) {
            lastIndex = name.length();
        }
        if (lastIndex > 200) {
            lastIndex = 200 - FILE_NAME_PLACEHOLDER_LENGTH;
            originalFilename = originalFilename.substring(0, lastIndex) + FILE_NAME_PLACEHOLDER;
        }
        return originalFilename + fileSuffix;
    }

    /**
     * 初始化文件信息，由子类实现，解析requestFile填充文件基础属性
     *
     * @param requestFile 上传的文件源对象
     */
    protected abstract void init(T requestFile);

    /**
     * 获取文件用途标识
     *
     * @return 文件用途
     */
    public final String getFileUsed() {
        return fileUsed;
    }

    /**
     * 获取文件存储路径前缀
     *
     * @return 路径前缀
     */
    public final String getFilePathPrefix() {
        return filePathPrefix;
    }

    /**
     * 获取上传用户标识
     *
     * @return 上传人
     */
    public final String getFileUploadUser() {
        return fileUploadUser;
    }

    /**
     * 获取文件描述
     *
     * @return 文件描述
     */
    public final String getFileDesc() {
        return fileDesc;
    }

    /**
     * 上传参数统一校验处理
     * <p>路径为空时自动生成默认路径；校验路径非法字符、上传人不能为空</p>
     *
     */
    public final void validate() {
        if (!StringUtils.hasText(filePathPrefix)) {
            this.filePathPrefix = generateDefaultFilePath();
        }
        this.filePathPrefix = Path.of(this.filePathPrefix).normalize().toString();
        AssertUtils.isTrue(!this.filePathPrefix.contains(FileConstant.PATH_POINT), BusinessErrorCode.FILE_PATH_ILLEGAL);
        AssertUtils.hasText(fileUploadUser, BusinessErrorCode.FILE_UPLOAD_USER_ILLEGAL);
    }

    /**
     * 获取默认文件路径
     *
     * @return 默认文件路径
     */
    private String generateDefaultFilePath() {
        LocalDateTime now = LocalDateTime.now();
        return StringConstant.XHT + File.separator + now.getYear() + File.separator + now.getMonthValue();
    }
}
