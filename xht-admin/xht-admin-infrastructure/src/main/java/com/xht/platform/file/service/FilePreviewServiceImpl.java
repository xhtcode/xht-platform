package com.xht.platform.file.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileHeaderAssembler;
import com.xht.framework.file.core.XhtFileBO;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.ServletUtils;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.enums.FileUsedEnum;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 用户头像预览服务实现
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FilePreviewServiceImpl implements IFilePreviewService {

    private final SysFileDao sysFileDao;

    private final FileStorageDao fileStorageDao;

    @Override
    public void preview(HttpServletRequest request, HttpServletResponse response, String fileStorageKey) {
        // 入参非空校验
        AssertUtils.isTrue(StringUtils.hasText(fileStorageKey), BusinessErrorCode.PARAM_ERROR);
        XhtFileBO xhtFileBO = sysFileDao.findFileInfoByFileStorageKey(fileStorageKey, FileUsedEnum.AVATAR);
        AssertUtils.notNull(xhtFileBO, BusinessErrorCode.FILE_NOT_EXIST);
        String etag = "\"" + xhtFileBO.fileHash() + "\"";
        response.setHeader(HttpHeaders.ETAG, etag);
        // 命中 If-None-Match 则返回 304，不再读取文件内容
        if (ServletUtils.isNotModified(request, etag)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            // RFC 7232：304 响应应附带缓存指令，与 200 保持一致
            response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=600, stale-while-revalidate=3600");
            return;
        }
        try (InputStream inputStream = fileStorageDao.download(xhtFileBO.fileStoragePath())) {
            FileHeaderAssembler.preview()
                    .andThen(aBoolean -> {
                        response.setHeader("X-Content-Type-Options", "nosniff");
                        // public 允许代理/网关缓存；stale-while-revalidate 头像体验更好
                        response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=600, stale-while-revalidate=3600");
                    })
                    .write(response, xhtFileBO);
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("头像读取IO异常，fileName:{}, path:{}", xhtFileBO.fileStorageName(), xhtFileBO.fileStoragePath(), e);
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, "文件读取IO失败", e);
        } catch (Exception e) {
            log.error("头像预览异常，fileName:{}, path:{}", xhtFileBO.fileStorageName(), xhtFileBO.fileStoragePath(), e);
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, "头像预览失败", e);
        }
    }


}
