package com.xht.platform.file.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 文件预览服务 - 管理员专用
 *
 * @author xht
 */
public interface IAdminFilePreviewService {

    /**
     * 文件下载
     *
     * @param response       响应对象，用于输出文件流
     * @param fileStorageKey 文件存储唯一标识key
     */
    void downloadFile(HttpServletResponse response, String fileStorageKey);

    /**
     * 文件预览
     *
     * @param request        请求对象
     * @param response       响应对象，用于输出预览流
     * @param fileStorageKey 文件存储唯一标识key
     */
    void filePreview(HttpServletRequest request, HttpServletResponse response, String fileStorageKey);
}
