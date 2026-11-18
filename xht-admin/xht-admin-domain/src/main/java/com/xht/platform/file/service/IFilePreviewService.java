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
     * 头像预览
     * <p>地址只暴露文件存储 key，不带文件后缀；可选携带 {@code ~宽x高} 尺寸后缀按需输出缩略图</p>
     *
     * @param request
     * @param response       响应对象，用于输出头像图片流
     * @param fileStorageKey 头像文件名：{@code 文件存储key[~宽x高]}
     */
    void preview(HttpServletRequest request, HttpServletResponse response, String fileStorageKey);

}
