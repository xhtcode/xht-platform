package com.xht.platform.file.service;

import com.xht.platform.common.file.bo.DownloadBO;
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
     * @return 文件信息
     */
    SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo);

    /**
     * 文件下载
     *
     * @param fileStorageKey 文件存储key
     * @return 文件下载信息（文件流、原始文件名、内容类型、文件大小）
     */
    DownloadBO downloadFile(String fileStorageKey);

    /**
     * 缩略图下载（按原图存储key查询，返回该文件的缩略图流）
     *
     * @param fileStorageKey 原图文件存储key
     * @return 缩略图下载信息（文件流、原始文件名、内容类型、文件大小）
     */
    DownloadBO downloadThumbnail(String fileStorageKey);

}
