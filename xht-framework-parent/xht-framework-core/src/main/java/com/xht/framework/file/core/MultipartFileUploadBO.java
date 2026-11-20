package com.xht.framework.file.core;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.utils.AssertUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件上传入参BO
 * 基于Spring MultipartFile 的文件上传入参实现类
 * 构造时将MultipartFile信息解析赋值到父类protected字段
 *
 * @author xht
 */
public class MultipartFileUploadBO extends AbstractUploadBO<MultipartFile> {

    /**
     * 构造方法，用于子类实现，进行参数初始化
     *
     * @param servletRequest HTTP请求对象
     * @param requestFile    上传的文件对象
     */
    public MultipartFileUploadBO(HttpServletRequest servletRequest, MultipartFile requestFile) {
        super(servletRequest, requestFile);
        AssertUtils.notNull(servletRequest, "HTTP请求对象不能为空");
        AssertUtils.notNull(requestFile, "文件对象不能为空");
    }

    /**
     * 初始化方法，用于子类实现，进行参数初始化
     *
     * @param requestFile 上传的文件对象
     */
    @Override
    protected void init(MultipartFile requestFile) {
        long size = requestFile.getSize();
        this.fileOriginalName = requestFile.getOriginalFilename();
        this.fileSize = size;
        this.fileSizeInfo = FileUtil.readableFileSize(size);
        this.contentType = requestFile.getContentType();
        this.fileSuffix = FileUtil.getSuffix(this.fileOriginalName);
    }


    /**
     * 获取文件原始名称
     *
     * @return 原始文件名（带后缀）
     */
    @Override
    public String getFileOriginalName() {
        return fileOriginalName;
    }

    /**
     * 获取文件字节大小
     *
     * @return 文件字节数
     */
    @Override
    public long getFileSize() {
        return fileSize;
    }

    /**
     * 获取格式化文件大小展示文本，例如：10.2MB
     *
     * @return 格式化后的文件大小信息
     */
    @Override
    public String getFileSizeInfo() {
        return fileSizeInfo;
    }

    /**
     * 获取文件后缀，不带点，例如：jpg、pdf
     *
     * @return 文件后缀
     */
    @Override
    public String getFileSuffix() {
        return fileSuffix;
    }

    /**
     * 获取文件MIME类型，例如：image/jpeg
     *
     * @return MIME类型
     */
    @Override
    public String getContentType() {
        return contentType;
    }

    /**
     * 获取文件输入流
     *
     * @return 文件输入流
     * @throws Exception 获取流异常
     */
    @Override
    public InputStream getInputStream() throws Exception {
        return requestFile.getInputStream();
    }

}
