package com.xht.platform.file.preview;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.utils.ServletUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.file.XhtFileUtils;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.entity.SysFileEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 描述： 原文件内容预览写入工具
 * <p>图片、PDF、音视频、JSON 等类型的预览不做格式转换，直接把存储中的文件内容写入响应：
 * 无 Range 头时返回 200 全量内容；命中单区间时返回 206 + Content-Range，
 * 供 PDF 分页加载、音视频拖动进度使用；区间起点超出文件大小时返回 416</p>
 *
 * @author xht
 **/
@Slf4j
public abstract class OriginalFilePreviewWriter {

    private OriginalFilePreviewWriter() {
    }

    /**
     * 把原文件内容写入响应（支持 Range 分段）
     *
     * @param request        请求
     * @param response       响应
     * @param sysFileEntity  文件实体
     * @param fileStorageDao 文件存储数据访问层
     */
    public static void write(HttpServletRequest request, HttpServletResponse response,
                             SysFileEntity sysFileEntity, FileStorageDao fileStorageDao) {
        String fileStoragePath = sysFileEntity.getFileStoragePath();
        String fileName = StringUtils.hasText(sysFileEntity.getFileOriginalName())
                ? sysFileEntity.getFileOriginalName() : sysFileEntity.getFileStorageName();
        String contentType = StringUtils.hasText(sysFileEntity.getFileType())
                ? sysFileEntity.getFileType() : MimeTypeEnums.DEFAULT_.getContentType();
        long totalSize = Objects.requireNonNullElse(sysFileEntity.getFileSize(), 0L);
        // 空文件没有内容可读，直接返回空响应，避免写入工具因总大小为 0 拒绝写出
        if (totalSize <= 0) {
            writeEmpty(response, fileName, contentType);
            return;
        }
        HttpRange httpRange = resolveRange(request);
        if (Objects.isNull(httpRange)) {
            try (InputStream inputStream = fileStorageDao.download(fileStoragePath)) {
                XhtFileUtils.writePreviewFile(response, fileName, contentType, inputStream, totalSize);
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.error("文件预览失败，fileStoragePath:{}", fileStoragePath, e);
                throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, e);
            }
            return;
        }
        long start = httpRange.getRangeStart(totalSize);
        if (start >= totalSize) {
            // 区间不可满足：416 + Content-Range: bytes */总大小
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + totalSize);
            return;
        }
        // 结束位置按文件大小收敛，请求区间超出文件末尾时只返回剩余部分
        long end = Math.min(httpRange.getRangeEnd(totalSize), totalSize - 1);
        try (InputStream inputStream = fileStorageDao.downloadRange(fileStoragePath, start, end - start + 1)) {
            XhtFileUtils.writeRangeFile(response, fileName, contentType, inputStream, start, end, totalSize);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("文件分段预览失败，fileStoragePath:{}，区间：{}-{}", fileStoragePath, start, end, e);
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, e);
        }
    }

    /**
     * 解析单区间 Range 请求头
     * <p>未携带、多区间或格式非法时返回 null，由调用方按全量响应处理（HTTP 允许服务端忽略 Range）</p>
     */
    private static HttpRange resolveRange(HttpServletRequest request) {
        try {
            return XhtFileUtils.resolvePreviewRange(ServletUtils.httpRange(request));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 写出空文件响应：只设置内联展示所需的响应头，无响应体
     */
    private static void writeEmpty(HttpServletResponse response, String fileName, String contentType) {
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.inline().filename(fileName, StandardCharsets.UTF_8).build().toString());
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        response.setContentLengthLong(0);
    }

}
