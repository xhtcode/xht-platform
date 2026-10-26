package com.xht.framework.utils.file;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.utils.AssertUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;

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
        try {
            setDownloadResponseHeader(response, fileOriginalName, contentType);
            var out = response.getOutputStream();
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("大文件流下载写入失败，文件名：{}", fileOriginalName, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 重载：大文件下载，使用默认8k缓冲区
     */
    public static void writeFile(HttpServletResponse response, String fileOriginalName, String contentType, InputStream inputStream) {
        writeFile(response, fileOriginalName, contentType, inputStream, FileConstant.DEFAULT_BUFFER_SIZE);
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
