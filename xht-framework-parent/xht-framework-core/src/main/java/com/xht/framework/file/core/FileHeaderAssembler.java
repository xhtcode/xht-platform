package com.xht.framework.file.core;

import com.xht.framework.common.constant.HttpConstants;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 文件响应头组装器
 * <p>用于组装文件下载/预览的 HTTP 响应头，统一处理 Content-Length、Content-Type、Content-Disposition、
 * 自定义文件头（X-File-*）与 etag，并支持链式追加自定义响应头回调</p>
 *
 * <p>实例不可变（{@link #andThen} 返回新实例），可安全复用；典型用法：</p>
 * <pre>{@code
 * FileHeaderAssembler.download()
 *         .andThen((response, file) -> response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store"))
 *         .write(response, sysFileBO);
 * }</pre>
 *
 * @author xht
 */
public final class FileHeaderAssembler {

    /**
     * 原始文件名响应头（X-File-Original-Name），值为 URL 编码后的原始文件名，便于前端直接获取下载名称
     */
    private static final String HEADER_X_FILE_ORIGINAL_NAME = HttpConstants.Header.HEADER_X_FILE_ORIGINAL_NAME.getValue();

    /**
     * 文件后缀响应头（X-File-Suffix），如 {@code pdf}
     */
    private static final String HEADER_X_FILE_SUFFIX = HttpConstants.Header.HEADER_X_FILE_SUFFIX.getValue();

    /**
     * 文件存储 key 响应头（X-File-Storage-Key），供前端后续按 key 引用文件
     */
    private static final String HEADER_X_FILE_STORAGE_KEY = HttpConstants.Header.HEADER_X_FILE_STORAGE_KEY.getValue();

    /**
     * true：attachment 下载；false：inline 预览
     */
    private final boolean download;
    /**
     * 回调：追加自定义header
     */
    @Nullable
    private final Consumer<Boolean> consumer;

    /**
     * 私有构造
     *
     * @param download true=下载attachment，false=预览inline
     * @param consumer 自定义header回调
     */
    private FileHeaderAssembler(boolean download,
                                @Nullable Consumer<Boolean> consumer) {
        this.download = download;
        this.consumer = consumer;
    }

    // ===================== 原有静态工厂方法【兼容旧代码不动】 =====================

    /**
     * 创建预览模式的组装器（Content-Disposition: inline）
     *
     * @return 预览模式组装器
     */
    public static FileHeaderAssembler preview() {
        return new FileHeaderAssembler(false, null);
    }

    /**
     * 创建下载模式的组装器（Content-Disposition: attachment）
     *
     * @return 下载模式组装器
     */
    public static FileHeaderAssembler download() {
        return new FileHeaderAssembler(true, null);
    }

    /**
     * 链式追加 header 回调，兼容原有写法
     * <p>多个回调按追加顺序依次执行；返回新实例，原实例不受影响</p>
     *
     * @param after 追加的自定义 header 回调，为 {@code null} 时直接返回当前实例
     * @return 追加回调后的新组装器
     */
    public FileHeaderAssembler andThen(@Nullable Consumer<Boolean> after) {
        if (after == null) {
            return this;
        }
        Consumer<Boolean> combined = this.consumer == null ? after : this.consumer.andThen(after);
        return new FileHeaderAssembler(this.download, combined);
    }


    // ===================== 核心执行方法 =====================

    /**
     * 写入响应头
     * <p>依次写入 Content-Length、Content-Type、Content-Disposition、自定义文件头（X-File-*）、etag，
     * 最后执行自定义 header 回调；文件信息为 {@code null} 时不写入任何响应头</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息，为 {@code null} 时跳过
     */
    public void write(@Nonnull HttpServletResponse response, @Nullable XhtFileBO xhtFileBO) {
        if (Objects.isNull(xhtFileBO)) {
            return;
        }
        setContentLength(response, xhtFileBO);
        setContentType(response, xhtFileBO);
        setContentDisposition(response, xhtFileBO);
        setCustomFileHeaders(response, xhtFileBO);
        setEtag(response, xhtFileBO);
        // 执行自定义回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(this.download);
        }
    }

    /**
     * 写入 Content-Length
     * <p>文件大小为 {@code null} 或负数（未知大小）时不写入</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息
     */
    private void setContentLength(@Nonnull HttpServletResponse response, @Nonnull XhtFileBO xhtFileBO) {
        Long fileSize = xhtFileBO.getFileSize();
        if (fileSize != null && fileSize >= 0) {
            response.setHeader(HttpHeaders.CONTENT_LENGTH, fileSize.toString());
        }
    }

    /**
     * 写入 Content-Type
     * <p>取文件的 MIME 类型，为空时不写入</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息
     */
    private void setContentType(@Nonnull HttpServletResponse response, @Nonnull XhtFileBO xhtFileBO) {
        String fileType = xhtFileBO.getFileType();
        if (StringUtils.hasText(fileType)) {
            response.setContentType(fileType);
        }
    }

    /**
     * 写入 Content-Disposition（预览 inline / 下载 attachment）
     * <p>文件名按 UTF-8 编码输出，避免中文等非 ASCII 文件名乱码；原始文件名为空时不写入</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息
     */
    private void setContentDisposition(@Nonnull HttpServletResponse response, @Nonnull XhtFileBO xhtFileBO) {
        String originalName = xhtFileBO.getFileOriginalName();
        if (!StringUtils.hasText(originalName)) {
            return;
        }
        ContentDisposition disposition;
        if (download) {
            disposition = ContentDisposition.attachment()
                    .filename(originalName, StandardCharsets.UTF_8)
                    .build();
        } else {
            disposition = ContentDisposition.inline()
                    .filename(originalName, StandardCharsets.UTF_8)
                    .build();
        }
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
    }

    /**
     * 写入自定义文件头
     * <p>X-File-Original-Name：原始文件名，固定做 URL 编码（响应头不支持非 ASCII 字符）；
     * X-File-Suffix、X-File-Storage-Key：有值时才写入</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息
     */
    private void setCustomFileHeaders(@Nonnull HttpServletResponse response, @Nonnull XhtFileBO xhtFileBO) {
        String originalName = xhtFileBO.getFileOriginalName();
        if (StringUtils.hasText(originalName)) {
            // 固定URL编码
            String encodedName = URLEncoder.encode(originalName, StandardCharsets.UTF_8);
            response.setHeader(HEADER_X_FILE_ORIGINAL_NAME, encodedName);
        }
        if (StringUtils.hasText(xhtFileBO.getFileSuffix())) {
            response.setHeader(HEADER_X_FILE_SUFFIX, xhtFileBO.getFileSuffix());
        }
        if (StringUtils.hasText(xhtFileBO.getFileStorageKey())) {
            response.setHeader(HEADER_X_FILE_STORAGE_KEY, xhtFileBO.getFileStorageKey());
        }
    }

    /**
     * 写入 etag
     * <p>取文件 hash 并加双引号（强校验器），供浏览器协商缓存；文件 hash 为空时不写入</p>
     *
     * @param response  http响应
     * @param xhtFileBO 文件信息
     */
    private void setEtag(@Nonnull HttpServletResponse response, @Nonnull XhtFileBO xhtFileBO) {
        String fileHash = xhtFileBO.getFileHash();
        if (StringUtils.hasText(fileHash)) {
            response.setHeader(HttpHeaders.ETAG, "\"" + fileHash + "\"");
        }
    }
}
