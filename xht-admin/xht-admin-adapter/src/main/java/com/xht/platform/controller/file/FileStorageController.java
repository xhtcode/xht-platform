package com.xht.platform.controller.file;

import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.common.domain.R;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.oauth2.annotation.CheckMenu;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.bo.DownloadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.utils.RangeHeaderParser;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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
@RequestMapping("/sys/files")
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
     * 协商缓存控制（私有缓存 1 天）
     */
    private static final String CACHE_CONTROL_PRIVATE = "private, max-age=86400";

    /**
     * 流拷贝缓冲区大小
     */
    private static final int COPY_BUFFER_SIZE = 8192;

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
        DownloadBO downloadBo = fileStorageService.downloadFile(fileStorageKey);
        writeFileResponse(downloadBo, request, response, DISPOSITION_ATTACHMENT);
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
        DownloadBO downloadBo = fileStorageService.downloadFile(fileStorageKey);
        String disposition = isUnsafeInlineContentType(downloadBo.getContentType())
                ? DISPOSITION_ATTACHMENT : DISPOSITION_INLINE;
        writeFileResponse(downloadBo, request, response, disposition);
    }

    /**
     * 缩略图预览
     * <p>
     * 按原图存储key返回缩略图（image/jpeg，inline）；缩略图不存在时返回文件不存在错误，
     * 大图异步生成中或非图片文件会出现该情况，前端应兜底展示原图
     *
     * @param fileStorageKey 原图文件存储key
     * @param request        Http请求
     * @param response       Http响应
     */
    @BLog(value = "文件管理", description = "预览缩略图")
    @Operation(summary = "缩略图预览", parameters = {
            @Parameter(name = "fileStorageKey", description = "原图文件存储key", required = true)
    })
    @GetMapping("/thumbnail/{fileStorageKey}")
    public void thumbnailFile(@PathVariable String fileStorageKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        DownloadBO downloadBo = fileStorageService.downloadThumbnail(fileStorageKey);
        writeFileResponse(downloadBo, request, response, DISPOSITION_INLINE);
    }

    /**
     * 写入文件响应：统一处理响应头、协商缓存、Range 与文件流输出
     * <p>
     * 行为说明：ETag/If-None-Match 命中直接 304 不写 body；Range 可解析时 206 部分输出；
     * 文件名按 RFC 5987 输出（ASCII 兜底名 + filename* 编码名）
     *
     * @param downloadBo  文件下载信息
     * @param request     Http请求（读取 Range/If-None-Match）
     * @param response    Http响应
     * @param disposition 内容处置类型（inline：内联预览，attachment：附件下载）
     */
    private void writeFileResponse(DownloadBO downloadBo, HttpServletRequest request, HttpServletResponse response, String disposition) throws IOException {
        // resetBuffer 仅清空 body 缓冲区，保留过滤器已写入的响应头（如 CORS）
        response.resetBuffer();
        Long fileSize = downloadBo.getFileSize();
        // 文件名处理：ASCII 兜底名 + RFC 5987 编码名，兼容各浏览器中文名
        String originalFilename = StringUtils.hasText(downloadBo.getOriginalFilename())
                ? downloadBo.getOriginalFilename() : StringConstant.EMPTY;
        String asciiFallback = originalFilename.replaceAll("[^\\x20-\\x7e]", "_").replace("\"", "_");
        String encodedFilename = URLEncoder.encode(originalFilename, StringConstant.UTF_8).replace("+", "%20");
        response.setHeader(HttpConstants.Header.DOWNLOAD_FILE.getValue(), encodedFilename);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("%s; filename=\"%s\"; filename*=UTF-8''%s", disposition, asciiFallback, encodedFilename));
        response.setContentType(StringUtils.emptyToDefault(downloadBo.getContentType(),
                HttpConstants.ContentType.APPLICATION_OCTET_STREAM_VALUE.getValue()));
        // 禁止浏览器 MIME 嗅探，防止内容被错误推断为可执行类型
        response.setHeader(X_CONTENT_TYPE_OPTIONS, "nosniff");
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        // 协商缓存：ETag（文件hash）+ Last-Modified（上传时间）+ Cache-Control
        if (StringUtils.hasText(downloadBo.getFileHash())) {
            response.setHeader(HttpHeaders.ETAG, "\"" + downloadBo.getFileHash() + "\"");
            String ifNoneMatch = request.getHeader(HttpHeaders.IF_NONE_MATCH);
            if (ifNoneMatch != null && ifNoneMatch.contains(downloadBo.getFileHash())) {
                // 内容未变化，直接 304 不写 body
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                return;
            }
        }
        if (Objects.nonNull(downloadBo.getFileUploadTime())) {
            response.setDateHeader(HttpHeaders.LAST_MODIFIED,
                    downloadBo.getFileUploadTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
        response.setHeader(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_PRIVATE);
        // Range 处理：可解析则 206 部分输出，否则 200 全量
        long start = 0;
        long length = Objects.nonNull(fileSize) ? fileSize : -1;
        Optional<RangeHeaderParser.Range> range = Objects.nonNull(fileSize)
                ? RangeHeaderParser.parse(request.getHeader(HttpHeaders.RANGE), fileSize)
                : Optional.empty();
        if (range.isPresent()) {
            start = range.get().start();
            length = range.get().length();
            response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            response.setHeader(HttpHeaders.CONTENT_RANGE,
                    String.format("bytes %d-%d/%d", start, range.get().end(), fileSize));
        } else {
            response.setStatus(HttpStatus.OK.value());
        }
        if (length >= 0) {
            response.setContentLengthLong(length);
        }
        try (InputStream inputStream = downloadBo.getInputStream()) {
            // 跳过 Range 起始字节，再限长拷贝
            if (start > 0) {
                inputStream.skipNBytes(start);
            }
            OutputStream outputStream = response.getOutputStream();
            if (length < 0) {
                inputStream.transferTo(outputStream);
            } else {
                copyBounded(inputStream, outputStream, length);
            }
        }
    }

    /**
     * 限长拷贝输入流到输出流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param length       最大拷贝字节数
     */
    private void copyBounded(InputStream inputStream, OutputStream outputStream, long length) throws IOException {
        byte[] buffer = new byte[COPY_BUFFER_SIZE];
        long remaining = length;
        while (remaining > 0) {
            int read = inputStream.read(buffer, 0, (int) Math.min(buffer.length, remaining));
            if (read == -1) {
                break;
            }
            outputStream.write(buffer, 0, read);
            remaining -= read;
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
