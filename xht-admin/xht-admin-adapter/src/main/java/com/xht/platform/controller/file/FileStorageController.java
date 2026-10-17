package com.xht.platform.controller.file;

import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * 描述： 文件存储控制器
 * <p>
 * 提供文件内容的上传、下载、预览能力；文件记录元数据的管理接口见 {@link SysFileController}
 *
 * @author xht
 **/
@Tag(name = "文件存储管理")
@Slf4j
@RestController
@RequestMapping("/file/storage")
@RequiredArgsConstructor
public class FileStorageController {

    /**
     * 内联预览：浏览器可直接渲染展示（图片、PDF、音视频等）
     */
    private static final String DISPOSITION_INLINE = "inline";

    /**
     * 附件下载：浏览器以下载方式保存文件
     */
    private static final String DISPOSITION_ATTACHMENT = "attachment";

    /**
     * 禁止浏览器 MIME 嗅探的响应头名
     */
    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

    /**
     * 不允许内联预览的主动内容类型（可被浏览器执行脚本，存在同源 XSS 风险，预览时降级为附件下载）
     */
    private static final Set<String> UNSAFE_INLINE_CONTENT_TYPES = Set.of(
            "text/html",
            "application/xhtml+xml",
            "image/svg+xml",
            "application/xml",
            "text/xml",
            "application/javascript",
            "text/javascript"
    );

    private final IFileStorageService fileStorageService;

    /**
     * 文件上传
     *
     * @param file 上传文件
     * @return 文件信息
     */
    @BLog(value = "文件管理", description = "上传文件")
    @CheckMenu("sys:file:upload")
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
     * 文件下载
     *
     * @param fileStorageKey 文件存储key
     * @param response       Http响应
     */
    @BLog(value = "文件管理", description = "下载文件")
    @Operation(summary = "文件下载", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储key", required = true)
    })
    @GetMapping("/download/{fileStorageKey}")
    public void downloadFile(@PathVariable String fileStorageKey, HttpServletResponse response) throws IOException {
        DownloadBO downloadBo = fileStorageService.downloadFile(fileStorageKey);
        writeFileResponse(downloadBo, response, DISPOSITION_ATTACHMENT);
    }

    /**
     * 文件预览
     * <p>
     * 浏览器可直接渲染的类型（图片、PDF、音视频、文本等）以 inline 方式内联预览；
     * HTML、SVG 等可执行脚本的主动内容类型存在同源 XSS 风险，强制以 attachment 方式下载
     *
     * @param fileStorageKey 文件存储key
     * @param response       Http响应
     */
    @BLog(value = "文件管理", description = "预览文件")
    @Operation(summary = "文件预览", parameters = {
            @Parameter(name = "fileStorageKey", description = "文件存储key", required = true)
    })
    @GetMapping("/preview/{fileStorageKey}")
    public void previewFile(@PathVariable String fileStorageKey, HttpServletResponse response) throws IOException {
        DownloadBO downloadBo = fileStorageService.downloadFile(fileStorageKey);
        String disposition = isUnsafeInlineContentType(downloadBo.getContentType())
                ? DISPOSITION_ATTACHMENT : DISPOSITION_INLINE;
        writeFileResponse(downloadBo, response, disposition);
    }

    /**
     * 写入文件响应：统一处理响应头与文件流输出
     *
     * @param downloadBo  文件下载信息
     * @param response    Http响应
     * @param disposition 内容处置类型（inline：内联预览，attachment：附件下载）
     */
    private void writeFileResponse(DownloadBO downloadBo, HttpServletResponse response, String disposition) throws IOException {
        response.resetBuffer();
        // 文件名 URL 编码，兼容中文等非 ASCII 字符；再将 + 替换为 %20，避免文件名中的空格被解析为 +
        String downloadFilename = URLEncoder.encode(downloadBo.getOriginalFilename(), StringConstant.UTF_8)
                .replace("+", "%20");
        response.setHeader(HttpConstants.Header.DOWNLOAD_FILE.getValue(), downloadFilename);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("%s; filename=%s", disposition, downloadFilename));
        response.setContentType(StringUtils.emptyToDefault(downloadBo.getContentType(),
                HttpConstants.ContentType.APPLICATION_OCTET_STREAM_VALUE.getValue()));
        // 禁止浏览器 MIME 嗅探，防止内容被错误推断为可执行类型
        response.setHeader(X_CONTENT_TYPE_OPTIONS, "nosniff");
        if (Objects.nonNull(downloadBo.getFileSize())) {
            response.setContentLengthLong(downloadBo.getFileSize());
        }
        try (InputStream inputStream = downloadBo.getInputStream()) {
            inputStream.transferTo(response.getOutputStream());
        }
    }

    /**
     * 判断是否为不允许内联预览的主动内容类型（可被浏览器执行脚本，存在同源 XSS 风险）
     *
     * @param contentType 文件内容类型
     * @return true：不允许内联预览
     */
    private boolean isUnsafeInlineContentType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return false;
        }
        // 归一化 MIME 类型：忽略 charset 等参数，并统一小写后比对
        String mimeType = contentType.split(StringConstant.SEMICOLON)[0].trim().toLowerCase(Locale.ROOT);
        return UNSAFE_INLINE_CONTENT_TYPES.contains(mimeType);
    }
}
