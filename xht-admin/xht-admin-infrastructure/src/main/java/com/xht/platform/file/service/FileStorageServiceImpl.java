package com.xht.platform.file.service;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.XhtFilePreviewFactory;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

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

}
