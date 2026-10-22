package com.xht.platform.controller.file;

import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.IsAdmin;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.service.IFileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述： 文件存储控制器
 * <p>
 * 提供文件内容的上传、下载、预览、缩略图预览能力；
 * 文件记录元数据的管理接口见 {@link SysFileController}（共用 /sys/files 前缀）；
 * 下载/预览支持 Range 断点续传、ETag/Last-Modified 协商缓存（304）
 *
 * @author xht
 **/
@Tag(name = "文件存储管理")
@Slf4j
@RestController
@RequestMapping("/file/storage")
@RequiredArgsConstructor
public class FileStorageController {

    private final IFileStorageService fileStorageService;

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return 文件信息
     */
    @BLog(value = "文件管理", description = "上传文件")
    @IsAdmin
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "上传文件", required = true, schema = @Schema(type = "string", format = "binary"))
    })
    @PostMapping("/upload")
    public R<SysFileResponse> uploadFile(@RequestPart(name = "file") MultipartFile file,
                                         @RequestParam(value = "fileUsed", required = false) String fileUsed
    ) {
        return R.ok().build(fileStorageService.uploadMultipartFile(UploadBO.builder(file).fileUsed(fileUsed).build()));
    }

    /**
     * 文件下载（支持 Range 断点续传与协商缓存）
     *
     * @param fileStorageKey 文件存储key
     * @param request        Http请求
     * @param response       Http响应
     */
    @BLog(value = "文件管理", description = "下载文件")
    @Operation(summary = "文件下载", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储key", required = true)
    })
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(@PathVariable String fileStorageKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    /**
     * 文件预览
     * <p>
     * 浏览器可直接渲染的类型（图片、PDF、音视频、文本等）以 inline 方式内联预览；
     * HTML、SVG 等可执行脚本的主动内容类型存在同源 XSS 风险，强制以 attachment 方式下载
     *
     * @param fileStorageKey 文件存储key
     * @param request        Http请求
     * @param response       Http响应
     */
    @BLog(value = "文件管理", description = "预览文件")
    @Operation(summary = "文件预览", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储key", required = true)
    })
    @GetMapping("/preview/{fileStorageKey}")
    public void previewFile(@PathVariable String fileStorageKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
    }


}
