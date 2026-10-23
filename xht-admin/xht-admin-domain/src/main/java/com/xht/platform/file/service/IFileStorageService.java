package com.xht.platform.file.service;

import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.response.SysFileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
     * 文件下载
     * <p>
     * 以附件方式（attachment）输出，支持 Range 断点续传（206）与
     * ETag/Last-Modified 协商缓存（304）；访问权限校验（仅超级管理员或文件上传人本人）
     * 在服务内部完成，响应头与文件流均由服务构建
     *
     * @param fileStorageKey 文件存储key
     * @return 文件响应（200 全量 / 206 部分内容 / 304 未修改），body 为流式输出，不占用堆内存
     */
    ResponseEntity<StreamingResponseBody> downloadFile(String fileStorageKey);

    /**
     * 文件预览
     * <p>
     * 浏览器可直接渲染的类型（图片、PDF、音视频、文本等）以 inline 方式内联预览；
     * HTML、SVG 等可执行脚本的主动内容类型存在同源 XSS 风险，强制以 attachment 方式下载；
     * 同样支持 Range 断点续传与 ETag/Last-Modified 协商缓存，访问权限校验同下载
     *
     * @param fileStorageKey 文件存储key
     * @return 文件响应（200 全量 / 206 部分内容 / 304 未修改），body 为流式输出，不占用堆内存
     */
    ResponseEntity<StreamingResponseBody> previewFile(String fileStorageKey);

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
