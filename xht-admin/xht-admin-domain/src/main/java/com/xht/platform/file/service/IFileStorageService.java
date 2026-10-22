package com.xht.platform.file.service;

import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.response.SysFileResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述： 文件存储服务
 *
 * @author xht
 **/
public interface IFileStorageService {

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件上传响应信息
     */
    SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo);

    /**
     * 关联文件业务（单文件）：标记为已关联（ASSOCIATED）并记录关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    void associateFile(String fileStorageKey, String fileUsed, String fileDesc);

}
