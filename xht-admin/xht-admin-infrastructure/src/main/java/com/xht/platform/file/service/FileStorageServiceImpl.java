package com.xht.platform.file.service;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.common.file.utils.RangeHeaderParser;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.XhtFilePreview;
import com.xht.platform.file.XhtFilePreviewFactory;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.utils.FileAccessChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 描述： 文件存储服务实现类
 * 负责文件上传、存储、元数据入库等核心逻辑，对接底层文件存储仓库
 * <p>
 * 上传安全策略：服务端权威校验大小与后缀白名单、文件头魔术字节探测 MIME 并与后缀交叉校验、
 * 文件名清洗（防路径穿越与非法字符）、单流双工（边落盘边计算 SHA-256，不全量读入内存）、
 * 落盘/入库失败时补偿删除已写入文件
 * <p>
 * 下载/预览策略：越权防护（仅超级管理员或文件上传人本人，禁止仅凭存储 Key 放行）、
 * 禁止对可执行脚本的主动内容类型内联预览（强制 attachment，避免同源 XSS）、
 * 禁止浏览器 MIME 嗅探（nosniff）、Range 断点续传（206 部分输出）与
 * ETag/Last-Modified 协商缓存（304），文件流限长拷贝不全量读入内存
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {

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
     * 禁止浏览器 MIME 嗅探的响应头值
     */
    private static final String X_CONTENT_TYPE_OPTIONS_NOSNIFF = "nosniff";

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

    /**
     * 文件存储仓库，对接底层存储实现（本地/对象存储等）
     */
    private final FileStorageRepository fileStorageRepository;

    /**
     * 文件DAO，负责文件元数据数据库持久化
     */
    private final SysFileDao sysFileDao;

    /**
     * 文件转换器，负责文件实体与响应对象的转换
     */
    private final SysFileConverter sysFileConverter;

    /**
     * 文件访问权限校验器，负责下载/预览的越权防护
     */
    private final FileAccessChecker fileAccessChecker;

    private final XhtFilePreviewFactory xhtFilePreviewFactory;

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    public SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        MultipartFile requestFile = uploadBo.getRequestFile();
        String fileUsed = uploadBo.getFileUsed();
        String filePathPrefix = uploadBo.getFilePathPrefix();
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        String originalFilename = requestFile.getOriginalFilename();
        long fileSize = requestFile.getSize();
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(SysFileUtils.getFileName(originalFilename, fileSuffix));
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        sysFileEntity.setFileUploadUser(SecurityUtils.getUserName());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileType(MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType());
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = filePathPrefix + File.separator + fileStorageName;
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileDesc(uploadBo.getFileDesc());
        sysFileEntity.setFileMetadata(FileMetadataBuilder
                .builder()
                .fullOriginalFilename(requestFile.getOriginalFilename())
                .build());
        try (InputStream digestIn = requestFile.getInputStream()) {
            UploadResponse upload = fileStorageRepository.upload(digestIn, fileStoragePath);
            sysFileEntity.setFileStorageType(upload.getStorageType());
            sysFileEntity.setFileHash(upload.getFileMd5());
            sysFileEntity.setFileStoragePath(upload.getFileStoragePath());
        } catch (BusinessException e) {
            fileStorageRepository.remove(fileStoragePath);
            throw e;
        } catch (Exception e) {
            log.error("文件上传异常:fileStoragePath:{} 信息:{}", fileStoragePath, e.getMessage(), e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        try {
            sysFileDao.save(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 文件下载（支持 Range 断点续传与协商缓存）
     * <p>
     * 以附件方式（attachment）输出，响应头与文件流统一由本服务构建后返回
     *
     * @param fileStorageKey 文件存储key
     * @return 文件响应（200 全量 / 206 部分内容 / 304 未修改）
     */
    @Override
    public ResponseEntity<StreamingResponseBody> downloadFile(String fileStorageKey) {
        SysFileEntity sysFileEntity = findAccessibleFile(fileStorageKey);
        return buildFileResponse(sysFileEntity, DISPOSITION_ATTACHMENT);
    }

    /**
     * 文件预览（支持 Range 断点续传与协商缓存）
     * <p>
     * 浏览器可直接渲染的类型以 inline 方式内联预览；HTML、SVG 等可执行脚本的主动内容类型
     * 存在同源 XSS 风险，强制以 attachment 方式下载
     *
     * @param fileStorageKey 文件存储key
     * @return 文件响应（200 全量 / 206 部分内容 / 304 未修改）
     */
    @Override
    public ResponseEntity<StreamingResponseBody> previewFile(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findActiveByStorageKey(fileStorageKey);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        XhtFilePreview filePreview = xhtFilePreviewFactory.getFilePreview(sysFileEntity.getFileType());
        filePreview.filePreviewHandle(sysFileConverter.toResponse(sysFileEntity));
        return ResponseEntity.ok(null);
    }

    /**
     * 关联文件业务（单文件）：标记为已关联（ASSOCIATED）并记录关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void associateFile(String fileStorageKey, String fileUsed, String fileDesc) {
        SysFileEntity sysFileEntity = sysFileDao.findActiveByStorageKey(fileStorageKey);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        AssertUtils.isTrue(Objects.equals(sysFileEntity.getFileStatus(), FileStatusEnum.ASSOCIATED), BusinessErrorCode.FILE_ACCESS_DENIED);
        sysFileDao.associateFile(fileStorageKey, fileUsed, fileDesc);
    }

    /**
     * 查找可访问的文件：文件记录必须存在且未被删除，并通过越权校验
     * <p>
     * 禁止仅凭存储 Key 直接放行：仅超级管理员或文件上传人本人可访问
     *
     * @param fileStorageKey 文件存储key
     * @return 文件实体
     */
    private SysFileEntity findAccessibleFile(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findActiveByStorageKey(fileStorageKey);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        fileAccessChecker.check(sysFileEntity);
        return sysFileEntity;
    }

    /**
     * 构建文件响应：统一处理响应头、协商缓存、Range 与文件流输出
     * <p>
     * 行为说明：ETag/If-None-Match 命中直接返回 304 且不带 body；Range 可解析时返回 206 部分内容，
     * 否则 200 全量；文件流在响应写出阶段打开并由 try-with-resources 统一关闭，限长拷贝不全量读入内存；
     * 文件名按 RFC 5987 输出（ASCII 兜底名 + filename* 编码名）
     *
     * @param sysFileEntity 文件实体
     * @param disposition   内容处置类型（inline：内联预览，attachment：附件下载）
     * @return 文件响应
     */
    private ResponseEntity<StreamingResponseBody> buildFileResponse(SysFileEntity sysFileEntity, String disposition) {
        // 请求头（Range/If-None-Match）读取来源：当前请求上下文
        HttpServletRequest request = getCurrentRequest();
        Long fileSize = sysFileEntity.getFileSize();
        String fileHash = sysFileEntity.getFileHash();
        String etag = StringUtils.hasText(fileHash) ? "\"" + fileHash + "\"" : null;
        Long lastModified = Objects.nonNull(sysFileEntity.getFileUploadTime())
                ? sysFileEntity.getFileUploadTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                : null;
        // 协商缓存：内容未变化，直接 304 不带响应体
        if (Objects.nonNull(etag) && isEtagMatched(request.getHeader(HttpHeaders.IF_NONE_MATCH), fileHash)) {
            HttpHeaders notModifiedHeaders = new HttpHeaders();
            notModifiedHeaders.setETag(etag);
            notModifiedHeaders.set(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_PRIVATE);
            if (Objects.nonNull(lastModified)) {
                notModifiedHeaders.setLastModified(lastModified);
            }
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).headers(notModifiedHeaders).build();
        }
        // 文件名处理：ASCII 兜底名 + RFC 5987 编码名，兼容各浏览器中文名
        String originalFilename = StringUtils.emptyToDefault(resolveDownloadFilename(sysFileEntity), StringConstant.EMPTY);
        String asciiFallback = originalFilename.replaceAll("[^\\x20-\\x7e]", "_").replace("\"", "_");
        // URLEncoder.encode(Charset) 为 Java 10+ 重载，不抛受检异常，无需额外捕获
        String encodedFilename = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8).replace("+", "%20");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(resolveMediaType(sysFileEntity.getFileType()));
        headers.set(HttpConstants.Header.DOWNLOAD_FILE.getValue(), encodedFilename);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                String.format("%s; filename=\"%s\"; filename*=UTF-8''%s", disposition, asciiFallback, encodedFilename));
        // 禁止浏览器 MIME 嗅探，防止内容被错误推断为可执行类型
        headers.set(X_CONTENT_TYPE_OPTIONS, X_CONTENT_TYPE_OPTIONS_NOSNIFF);
        headers.set(HttpHeaders.ACCEPT_RANGES, "bytes");
        headers.set(HttpHeaders.CACHE_CONTROL, CACHE_CONTROL_PRIVATE);
        if (Objects.nonNull(etag)) {
            headers.setETag(etag);
        }
        if (Objects.nonNull(lastModified)) {
            headers.setLastModified(lastModified);
        }
        // Range 处理：可解析则 206 部分输出，否则 200 全量
        long start = 0;
        long length = Objects.nonNull(fileSize) ? fileSize : -1;
        HttpStatus status = HttpStatus.OK;
        Optional<RangeHeaderParser.Range> range = Objects.nonNull(fileSize)
                ? RangeHeaderParser.parse(request.getHeader(HttpHeaders.RANGE), fileSize)
                : Optional.empty();
        if (range.isPresent()) {
            start = range.get().start();
            length = range.get().length();
            status = HttpStatus.PARTIAL_CONTENT;
            headers.set(HttpHeaders.CONTENT_RANGE,
                    String.format("bytes %d-%d/%d", start, range.get().end(), fileSize));
        }
        if (length >= 0) {
            headers.setContentLength(length);
        }
        long rangeStart = start;
        long copyLength = length;
        // 文件流在响应写出阶段打开，避免异步写出前长时间持有句柄；try-with-resources 保证关闭
        StreamingResponseBody body = outputStream -> {
            try (InputStream inputStream = fileStorageRepository.download(sysFileEntity.getFileStoragePath())) {
                // 跳过 Range 起始字节，再限长拷贝
                if (rangeStart > 0) {
                    inputStream.skipNBytes(rangeStart);
                }
                if (copyLength < 0) {
                    inputStream.transferTo(outputStream);
                } else {
                    copyBounded(inputStream, outputStream, copyLength);
                }
            }
        };
        return ResponseEntity.status(status).headers(headers).body(body);
    }

    /**
     * 判断 If-None-Match 是否与文件 ETag 匹配（兼容 W/ 弱校验与多值场景）
     *
     * @param ifNoneMatch 请求头 If-None-Match
     * @param fileHash    文件hash
     * @return true：内容未修改
     */
    private boolean isEtagMatched(String ifNoneMatch, String fileHash) {
        return StringUtils.hasText(ifNoneMatch) && ifNoneMatch.contains(fileHash);
    }

    /**
     * 解析文件内容类型：缺失或非法时按二进制流兜底
     *
     * @param contentType 文件内容类型
     * @return 响应内容类型
     */
    private MediaType resolveMediaType(String contentType) {
        if (!StringUtils.hasText(contentType)) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
        try {
            return MediaType.parseMediaType(contentType);
        } catch (InvalidMediaTypeException e) {
            log.warn("文件内容类型非法，按二进制流输出:{}", contentType);
            return MediaType.APPLICATION_OCTET_STREAM;
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

    /**
     * 解析下载文件名：优先取元数据中的完整原始文件名（长文件名截断场景的兜底），其次取文件原始名称，最后兜底取文件存储名称
     *
     * @param sysFileEntity 文件实体
     * @return 下载文件名
     */
    private String resolveDownloadFilename(SysFileEntity sysFileEntity) {
        FileMetadata fileMetadata = sysFileEntity.getFileMetadata();
        if (Objects.nonNull(fileMetadata)) {
            String fullOriginalFilename = fileMetadata.getFullOriginalFilename();
            if (StringUtils.hasText(fullOriginalFilename)) {
                return fullOriginalFilename;
            }
        }
        return StringUtils.emptyToDefault(sysFileEntity.getFileOriginalName(), sysFileEntity.getFileStorageName());
    }

    /**
     * 获取当前 HTTP 请求（Range 与协商缓存请求头的读取来源）
     *
     * @return 当前请求
     */
    private HttpServletRequest getCurrentRequest() {
        Object requestAttributes = RequestContextHolder.getRequestAttributes();
        AssertUtils.isTrue(requestAttributes instanceof ServletRequestAttributes,
                BusinessErrorCode.FILE_DOWNLOAD_FAIL, "当前请求上下文不存在，无法读取 Range/协商缓存请求头");
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

}
