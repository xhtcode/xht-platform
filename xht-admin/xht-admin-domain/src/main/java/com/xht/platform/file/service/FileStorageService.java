package com.xht.platform.file.service;

import com.xht.platform.common.file.bo.UploadBO;
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
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo);

}
