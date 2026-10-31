package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.ServletUtils;
import com.xht.framework.utils.file.XhtFileUtils;
import com.xht.platform.common.file.bo.ChunkMergeBO;
import com.xht.platform.common.file.bo.ChunkUploadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.permission.XhtFilePermissionContext;
import jakarta.servlet.http.HttpServletRequest;
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

    private final XhtFilePermissionContext filePermissionContext;

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
     * 查询已上传分片序号列表（断点续传）
     *
     * @param uploadId 分片上传标识
     * @return 已上传分片序号列表（升序），无已上传分片时返回空集合
     */
    @Override
    public List<Integer> findUploadedChunkIndexes(String uploadId) {
        return fileStorageDao.findUploadedChunkIndexes(uploadId);
    }

    /**
     * 分片上传（单分片）
     *
     * @param chunkUploadBo 分片上传参数
     */
    @Override
    public void uploadChunk(ChunkUploadBO<MultipartFile> chunkUploadBo) {
        fileStorageDao.uploadChunk(chunkUploadBo);
    }

    /**
     * 合并文件分片：校验分片完整性，合并为完整文件并生成文件记录
     *
     * @param chunkMergeBo 分片合并参数
     * @return 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileResponse mergeChunks(ChunkMergeBO chunkMergeBo) {
        return sysFileConverter.toResponse(fileStorageDao.mergeChunks(chunkMergeBo));
    }

    /**
     * 初始化文件预览信息
     * <p>按文件存储Key定位文件记录，通过权限校验后返回预览所需的名称、大小、类型与访问地址；
     * 文件用途取自记录本身，调用方无需声明</p>
     *
     * @param fileStorageKey 文件存储Key
     * @return 文件预览初始化信息
     */
    @Override
    public SysFilePreviewResponse initPreviewInfo(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao
                .findOneOptional(SysFileEntity::getFileStorageKey, fileStorageKey)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
        filePermissionContext.check(sysFileEntity);
        return sysFileConverter.buildPreviewInfo(sysFileEntity);
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
        filePermissionContext.check(sysFileEntity);
        try (InputStream download = fileStorageDao.download(sysFileEntity)) {
            XhtFileUtils.writeFile(response, sysFileEntity.getFileStorageName(), sysFileEntity.getFileType(), download);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 文件预览
     * <p>支持 Range 分段请求，可用于 PDF 分页加载、音视频拖动进度</p>
     * <p>按文件存储路径直接输出原文件内容，不做格式转换；是否可内联渲染由调用方按文件后缀／MIME 判断。
     * 支持 HTTP Range 分段请求（PDF 分页加载、视频拖动进度），命中区间返回 206 + Content-Range；
     * 无 Range 头、Range 解析失败或多区间请求时退化为全量 200 响应；区间起点超出文件大小时返回 416</p>
     *
     * @param request        请求
     * @param response       响应
     * @param fileStorageKey 文件存储Key
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, String fileStorageKey) {
        SysFileEntity sysFileEntity = sysFileDao.findOneOptional(SysFileEntity::getFileStorageKey, fileStorageKey).orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
        filePermissionContext.check(sysFileEntity);
        String previewPath = sysFileEntity.getFileStoragePath();
        String fileName = sysFileEntity.getFileStorageName();
        String contentType = sysFileEntity.getFileType();
        long totalSize = sysFileEntity.getFileSize();
        List<HttpRange> httpRanges = ServletUtils.httpRange(request);
        HttpRange httpRange = XhtFileUtils.resolvePreviewRange(httpRanges);
        if (Objects.isNull(httpRange)) {
            try (InputStream inputStream = fileStorageDao.download(previewPath)) {
                XhtFileUtils.writePreviewFile(response, fileName, contentType, inputStream, totalSize);
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.error("文件预览失败，存储路径：{}", previewPath, e);
                throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
            }
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

}
