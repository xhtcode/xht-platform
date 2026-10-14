package com.xht.platform.common.file.bo;

import com.xht.framework.common.domain.bo.XhtBO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

/**
 * 下载文件信息BO
 * 封装文件下载所需的文件流、原始文件名、内容类型与文件大小，使用静态工厂构建，为不可变对象
 *
 * @author xht
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DownloadBO implements XhtBO {

    /**
     * 文件输入流，由调用方负责关闭
     */
    private final InputStream inputStream;

    /**
     * 文件原始名称，用于下载响应文件名
     */
    private final String originalFilename;

    /**
     * 文件内容类型（MIME类型）
     */
    private final String contentType;

    /**
     * 文件大小（单位：字节）
     */
    private final Long fileSize;

    /**
     * 创建下载文件信息BO
     *
     * @param inputStream      文件输入流，由调用方负责关闭
     * @param originalFilename 文件原始名称
     * @param contentType      文件内容类型（MIME类型）
     * @param fileSize         文件大小（单位：字节）
     * @return 下载文件信息BO
     */
    public static DownloadBO of(InputStream inputStream, String originalFilename, String contentType, Long fileSize) {
        return new DownloadBO(inputStream, originalFilename, contentType, fileSize);
    }

}
