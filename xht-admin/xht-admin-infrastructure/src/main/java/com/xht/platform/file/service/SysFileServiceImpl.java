package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.ServletUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.file.XhtFileUtils;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.file.XhtFilePreview;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 描述： 系统文件服务实现
 * <p>
 * 提供系统文件记录（sys_file）的元数据 CRUD；围绕文件内容的操作类业务
 * 删除前校验文件存在、未被关联业务、当前用户有权访问；删除时同步清理物理文件
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl implements ISysFileService {

    private final SysFileDao sysFileDao;

    private final SysFileConverter sysFileConverter;

    private final FileStorageDao fileStorageDao;

    private final XhtFilePreview xhtFilePreview;

    /**
     * 根据文件ID删除文件记录（逻辑删除），并同步删除物理文件
     *
     * @param fileId 文件ID
     */
    @Override
    public void removeById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        // 已关联业务的文件不允许删除
        AssertUtils.isTrue(!Objects.equals(FileStatusEnum.ASSOCIATED, sysFileEntity.getFileStatus()), BusinessErrorCode.FILE_ASSOCIATED, "文件已关联业务，无法删除");
        sysFileDao.updateFileStatusById(fileId, FileStatusEnum.DELETED);
    }

    /**
     * 根据文件ID查询文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    @Override
    public SysFileResponse findById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 分页查询文件列表
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysFileResponse> findPageList(SysFileQuery query) {
        Page<SysFileEntity> page = sysFileDao.findPageList(PageTool.getPage(query), query);
        return sysFileConverter.toResponse(page);
    }

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        return sysFileConverter.toResponse(fileStorageDao.uploadMultipartFile(uploadBo));
    }

    /**
     * 文件下载
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @Override
    public void downloadFile(HttpServletResponse response, String fileStorageKey) {
        SysFileEntity sysFileEntity = sysFileDao.findOneOptional(SysFileEntity::getFileStorageKey, fileStorageKey).orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
        try (InputStream download = fileStorageDao.download(sysFileEntity)) {
            XhtFileUtils.writeFile(response, resolveOriginalFilename(sysFileEntity), sysFileEntity.getFileType(), download);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 文件预览
     * <p>支持 HTTP Range 分段请求（PDF 分页加载、视频拖动进度），命中区间返回 206 + Content-Range；
     * 无 Range 头、Range 解析失败或多区间请求时退化为全量 200 响应；区间起点超出文件大小时返回 416</p>
     *
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @Override
    public void filePreview(HttpServletResponse response, String fileStorageKey) {
        SysFileEntity sysFileEntity = sysFileDao.findOneOptional(SysFileEntity::getFileStorageKey, fileStorageKey).orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
        // 预览能力由预览器决定，不支持的格式提示下载
        AssertUtils.isTrue(xhtFilePreview.support(sysFileEntity.getFileSuffix()), BusinessErrorCode.FILE_PREVIEW_NOT_SUPPORT);
        String previewPath = xhtFilePreview.filePreviewHandle(sysFileEntity.getFileStoragePath());
        String fileName = resolveOriginalFilename(sysFileEntity);
        String contentType = sysFileEntity.getFileType();
        long totalSize = sysFileEntity.getFileSize();
        HttpRange httpRange = resolvePreviewRange(ServletUtils.getHttpServletRequest().getHeader(HttpHeaders.RANGE));
        if (httpRange == null) {
            writeFullPreview(response, previewPath, fileName, contentType, totalSize);
            return;
        }
        long start = httpRange.getRangeStart(totalSize);
        long end = httpRange.getRangeEnd(totalSize);
        if (start >= totalSize) {
            // 区间不可满足：416 + Content-Range: bytes */总大小
            response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
            response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes */" + totalSize);
            return;
        }
        try (InputStream inputStream = fileStorageDao.downloadRange(previewPath, start, end - start + 1)) {
            XhtFileUtils.writeRangeFile(response, fileName, contentType, inputStream, start, end, totalSize);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("文件分段预览失败，文件Key：{}，区间：{}-{}", fileStorageKey, start, end, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 全量预览：直接输出文件全部内容（200 OK）
     *
     * @param response    响应
     * @param previewPath 预览用文件存储路径
     * @param fileName    文件名
     * @param contentType mime
     * @param totalSize   文件总大小
     */
    private void writeFullPreview(HttpServletResponse response, String previewPath, String fileName, String contentType, long totalSize) {
        try (InputStream inputStream = fileStorageDao.download(previewPath)) {
            XhtFileUtils.writePreviewFile(response, fileName, contentType, inputStream, totalSize);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("文件预览失败，存储路径：{}", previewPath, e);
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 解析 Range 请求头
     * <p>解析失败或多区间请求返回 null，由调用方按全量响应处理（HTTP 允许服务端忽略 Range）</p>
     *
     * @param rangeHeader Range 请求头
     * @return 单区间对象；按全量响应时返回 null
     */
    private HttpRange resolvePreviewRange(String rangeHeader) {
        if (StringUtils.isEmpty(rangeHeader)) {
            return null;
        }
        List<HttpRange> ranges;
        try {
            ranges = HttpRange.parseRanges(rangeHeader);
        } catch (IllegalArgumentException e) {
            log.warn("文件预览 Range 请求头解析失败，按全量响应：{}", rangeHeader);
            return null;
        }
        if (ranges.size() != 1) {
            log.debug("文件预览暂不支持多区间请求，按全量响应：{}", rangeHeader);
            return null;
        }
        return ranges.get(0);
    }

    /**
     * 解析原始文件名：优先取元数据中的完整原始文件名，缺失时用文件记录中的原始文件名
     *
     * @param sysFileEntity 文件实体
     * @return 原始文件名
     */
    private String resolveOriginalFilename(SysFileEntity sysFileEntity) {
        FileMetadata fileMetadata = sysFileEntity.getFileMetadata();
        if (fileMetadata != null && !StringUtils.isEmpty(fileMetadata.getFullOriginalFilename())) {
            return fileMetadata.getFullOriginalFilename();
        }
        return sysFileEntity.getFileOriginalName();
    }

}
