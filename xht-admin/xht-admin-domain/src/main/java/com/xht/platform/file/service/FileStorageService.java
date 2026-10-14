package com.xht.platform.file.service;

import com.xht.platform.file.domain.response.SysFileResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述： 文件存储服务
 *
 * @author xht
 **/
public interface FileStorageService {
    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param fileUsed      文件用途
     * @return 文件信息
     */
    SysFileResponse uploadFile(MultipartFile multipartFile, String fileUsed);
}
