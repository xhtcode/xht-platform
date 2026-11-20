package com.xht.framework.file.core;

import java.io.InputStream;

/**
 * 文件上传信息BO标记接口
 * 所有上传入参BO统一实现该接口，用于泛型/类型统一约束
 *
 * @author xht
 */
interface IUploadInfoBO {

    /**
     * 获取文件原始名称
     *
     * @return 原始文件名（带后缀）
     */
    String getFileOriginalName();

    /**
     * 获取文件字节大小
     *
     * @return 文件字节数
     */
    long getFileSize();

    /**
     * 获取格式化文件大小展示文本，例如：10.2MB
     *
     * @return 格式化后的文件大小信息
     */
    String getFileSizeInfo();

    /**
     * 获取文件后缀，不带点，例如：jpg、pdf
     *
     * @return 文件后缀
     */
    String getFileSuffix();

    /**
     * 获取文件MIME类型，例如：image/jpeg
     *
     * @return MIME类型
     */
    String getContentType();

    /**
     * 获取文件输入流
     *
     * @return 文件输入流
     * @throws Exception 获取流异常
     */
    InputStream getInputStream() throws Exception;

}
