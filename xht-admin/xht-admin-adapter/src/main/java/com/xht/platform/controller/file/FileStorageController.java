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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述： 文件存储控制器
 * <p>
 * 提供文件内容的上传、下载、预览能力；
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
        return R.ok().build(fileStorageService.uploadMultipartFile(UploadBO
                .builder(file)
                .fileUsed(fileUsed)
                .build())
        );
    }

    /**
     * 文件下载
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @BLog(value = "文件管理", description = "下载文件")
    @IsAdmin
    @Operation(summary = "文件下载")
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(HttpServletResponse response, @PathVariable String fileStorageKey) throws IOException {
        fileStorageService.downloadFile(response, fileStorageKey);
    }

}
