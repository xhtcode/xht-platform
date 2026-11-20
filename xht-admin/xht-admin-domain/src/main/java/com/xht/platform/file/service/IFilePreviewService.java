package com.xht.platform.file.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 用户头像预览服务
 *
 * @author xht
 **/
public interface IFilePreviewService {

    /**
     * 用户头像预览
     * <p>根据文件存储key读取头像文件流，直接写入response输出给前端预览/访问</p>
     *
     * @param request        请求对象
     * @param response       响应对象，用于输出文件流
     * @param fileStorageKey 文件存储唯一标识key（文件索引）
     */
    void previewUserAvatar(HttpServletRequest request, HttpServletResponse response, String fileStorageKey);

}
