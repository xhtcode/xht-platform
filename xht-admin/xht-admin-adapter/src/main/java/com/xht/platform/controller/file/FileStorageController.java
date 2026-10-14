package com.xht.platform.controller.file;

import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.common.domain.R;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.bo.DownloadBO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 描述： 文件存储控制器
 *
 * @author xht
 **/
@Tag(name = "文件存储管理")
@Slf4j
@RestController
@RequestMapping("/file/storage")
@RequiredArgsConstructor
public class FileStorageController {

    private final IFileStorageService IFileStorageService;

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return 文件信息
     */
    @Operation(summary = "文件上传", parameters = {
            @Parameter(name = "file", description = "上传文件", required = true, schema = @Schema(type = "string", format = "binary"))
    })
    @PostMapping("/upload")
    public R<SysFileResponse> uploadFile(@RequestPart(name = "file") MultipartFile file,
                                         @RequestParam(value = "fileUsed", required = false) String fileUsed
    ) {
        return R.ok().build(IFileStorageService.uploadMultipartFile(UploadBO.builder(file).fileUsed(fileUsed).build()));
    }

    /**
     * 文件下载
     *
     * @param fileStorageKey 文件存储key
     * @param response       Http响应
     */
    @Operation(summary = "文件下载", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储key", required = true)
    })
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(@PathVariable String fileStorageKey, HttpServletResponse response) throws IOException {
        DownloadBO downloadBo = IFileStorageService.downloadFile(fileStorageKey);
        response.reset();
        // 文件名 URL 编码，兼容中文等非 ASCII 字符；再将 + 替换为 %20，避免文件名中的空格被解析为 +
        String downloadFilename = URLEncoder.encode(downloadBo.getOriginalFilename(), StringConstant.UTF_8)
                .replace("+", "%20");
        response.setHeader(HttpConstants.Header.DOWNLOAD_FILE.getValue(), downloadFilename);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", downloadFilename));
        response.setContentType(StringUtils.emptyToDefault(downloadBo.getContentType(),
                HttpConstants.ContentType.APPLICATION_OCTET_STREAM_VALUE.getValue()));
        if (Objects.nonNull(downloadBo.getFileSize())) {
            response.setContentLengthLong(downloadBo.getFileSize());
        }
        try (InputStream inputStream = downloadBo.getInputStream()) {
            inputStream.transferTo(response.getOutputStream());
        }
    }
}
