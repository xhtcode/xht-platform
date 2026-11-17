package com.xht.platform.file.service;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.preview.XhtFilePreviewContext;
import com.xht.framework.preview.core.bo.FilePreviewBO;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.file.XhtFileUtils;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.entity.SysFileEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 描述： 文件预览服务 - 管理员专用
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminFilePreviewServiceImpl implements IAdminFilePreviewService {

    private final SysFileDao sysFileDao;

    private final FileStorageDao fileStorageDao;

    private final XhtFilePreviewContext xhtFilePreviewContext;

    /**
     * 文件下载
     *
     * @param response       响应对象，用于输出文件流
     * @param fileStorageKey 文件存储唯一标识key
     */
    @Override
    public void downloadFile(HttpServletResponse response, String fileStorageKey) {
        SysFileEntity file = sysFileDao.findByFileStorageKey(fileStorageKey);
        try (InputStream download = fileStorageDao.download(file)) {
            String downloadName = StringUtils.hasText(file.getFileOriginalName()) ? file.getFileOriginalName() : file.getFileStorageName();
            XhtFileUtils.writeFile(response, downloadName, file.getFileType(), download);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 文件预览
     *
     * @param request        请求对象
     * @param response       响应对象，用于输出预览流
     * @param fileStorageKey 文件存储唯一标识key
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, String fileStorageKey) {
        SysFileEntity file = sysFileDao.findByFileStorageKey(fileStorageKey);
        xhtFilePreviewContext.preview(request, response, FilePreviewBO
                .builder()
                .fileOriginalName(file.getFileOriginalName())
                .fileSize(file.getFileSize())
                .fileType(file.getFileType())
                .fileSuffix(file.getFileSuffix())
                .fileStorageType(file.getFileStorageType())
                .fileStorageName(file.getFileStorageName())
                .fileStoragePath(file.getFileStoragePath())
                .previewType(file.getPreviewType())
                .build()
        );
    }
}
