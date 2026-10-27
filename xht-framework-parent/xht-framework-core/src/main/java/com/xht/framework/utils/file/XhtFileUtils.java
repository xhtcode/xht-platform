package com.xht.framework.utils.file;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.utils.AssertUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 描述： 文件写入工具类
 *
 * @author xht
 **/
@Slf4j
public abstract class XhtFileUtils {

    private XhtFileUtils() {
    }

    /**
     * 文件写入响应（输入流版本，适合大文件下载，分片读取，防止OOM）
     * 【重要】入参 inputStream 是调用方传入，本工具类不关闭 inputStream，由调用方负责关闭源流！
     *
     * @param response         响应
     * @param fileOriginalName 文件名
     * @param contentType      mime
     * @param inputStream      源输入流（如文件流、oss流）
     * @param bufferSize       缓冲区大小，建议 4096 / 8192
     */
    public static void writeFile(HttpServletResponse response, String fileOriginalName, String contentType, InputStream inputStream, int bufferSize) {
        AssertUtils.notNull(response, "响应对象不能为空");
        AssertUtils.notNull(fileOriginalName, "文件名不能为空");
        AssertUtils.hasText(contentType, "mime类型不能为空");
        AssertUtils.notNull(inputStream, "输入流不能为空");
        AssertUtils.isTrue(bufferSize > 0, "缓冲区大小必须大于0");
        ServletOutputStream out = null;
        try {
            setDownloadResponseHeader(response, fileOriginalName, contentType);
            out = response.getOutputStream();
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("大文件流下载写入失败，文件名：{}", fileOriginalName, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (Exception e) {
                    // 静默关闭
                }
            }
        }
    }

    /**
     * 重载：大文件下载，使用默认8k缓冲区
     */
    public static void writeFile(HttpServletResponse response, String fileOriginalName, String contentType, InputStream inputStream) {
        writeFile(response, fileOriginalName, contentType, inputStream, FileConstant.DEFAULT_BUFFER_SIZE);
    }

    /**
     * 文件预览写入响应（全量，200 OK）
     * <p>【重要】入参 inputStream 是调用方传入，本工具类不关闭 inputStream，由调用方负责关闭源流！</p>
     * <p>响应头包含 inline 展示、Accept-Ranges 与 Content-Length，浏览器可据此发起分段请求</p>
     *
     * @param response         响应
     * @param fileOriginalName 文件名
     * @param contentType      mime
     * @param inputStream      源输入流
     * @param totalSize        文件总大小（字节）
     */
    public static void writePreviewFile(HttpServletResponse response, String fileOriginalName, String contentType, InputStream inputStream, long totalSize) {
        AssertUtils.notNull(response, "响应对象不能为空");
        AssertUtils.hasText(fileOriginalName, "文件名不能为空");
        AssertUtils.hasText(contentType, "mime类型不能为空");
        AssertUtils.notNull(inputStream, "输入流不能为空");
        AssertUtils.isTrue(totalSize > 0, "文件大小必须大于0");
        try {
            setPreviewResponseHeader(response, fileOriginalName, contentType);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentLengthLong(totalSize);
            copyToResponse(response, inputStream, totalSize);
        } catch (Exception e) {
            log.error("文件预览写入失败，文件名：{}", fileOriginalName, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 文件预览写入响应（分段，206 Partial Content）
     * <p>【重要】入参 inputStream 是调用方传入，本工具类不关闭 inputStream，由调用方负责关闭源流！</p>
     * <p>区间需由调用方按文件总大小裁剪：[start, end] 闭区间，且 0 ≤ start ≤ end &lt; totalSize</p>
     *
     * @param response         响应
     * @param fileOriginalName 文件名
     * @param contentType      mime
     * @param inputStream      源输入流（已定位到 start）
     * @param start            起始字节偏移（含）
     * @param end              结束字节偏移（含）
     * @param totalSize        文件总大小（字节）
     */
    public static void writeRangeFile(HttpServletResponse response, String fileOriginalName, String contentType,
                                      InputStream inputStream, long start, long end, long totalSize) {
        AssertUtils.notNull(response, "响应对象不能为空");
        AssertUtils.hasText(fileOriginalName, "文件名不能为空");
        AssertUtils.hasText(contentType, "mime类型不能为空");
        AssertUtils.notNull(inputStream, "输入流不能为空");
        AssertUtils.isTrue(totalSize > 0, "文件大小必须大于0");
        AssertUtils.isTrue(start >= 0 && end >= start && end < totalSize, "分段区间不合法");
        long rangeLength = end - start + 1;
        try {
            setPreviewResponseHeader(response, fileOriginalName, contentType);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            response.setHeader("Content-Range", String.format("bytes %d-%d/%d", start, end, totalSize));
            response.setContentLengthLong(rangeLength);
            copyToResponse(response, inputStream, rangeLength);
        } catch (Exception e) {
            log.error("文件分段预览写入失败，文件名：{}，区间：{}-{}", fileOriginalName, start, end, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 写出响应体：按预期长度分段拷贝，防止上游流未截断导致写出多余内容
     *
     * @param response     响应
     * @param inputStream  源输入流
     * @param expectLength 预期写出字节数
     */
    private static void copyToResponse(HttpServletResponse response, InputStream inputStream, long expectLength) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        byte[] buffer = new byte[FileConstant.DEFAULT_BUFFER_SIZE];
        long remain = expectLength;
        int len;
        while (remain > 0 && (len = inputStream.read(buffer, 0, (int) Math.min(buffer.length, remain))) != -1) {
            out.write(buffer, 0, len);
            remain -= len;
        }
        out.flush();
    }

    /**
     * 私有方法：统一设置预览响应头（inline 展示 + 支持分段）
     */
    private static void setPreviewResponseHeader(HttpServletResponse response, String fileOriginalName, String contentType) {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", ContentDisposition.inline().filename(fileOriginalName, StandardCharsets.UTF_8).build().toString());
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("X-Content-Type-Options", "nosniff");
    }

    /**
     * 私有方法：统一设置下载响应头
     */
    private static void setDownloadResponseHeader(HttpServletResponse response, String fileOriginalName, String contentType) {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", ContentDisposition.attachment().filename(fileOriginalName, StandardCharsets.UTF_8).build().toString());
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("X-Content-Type-Options", "nosniff");
    }
}
