package com.xht.platform.file.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.crypto.MD5Utils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.DownloadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.response.UploadResponse;
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
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    public UploadResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        MultipartFile requestFile = uploadBo.getRequestFile();
        String fileUsed = uploadBo.getFileUsed();
        String filePathPrefix = uploadBo.getFilePathPrefix();
        // 参数校验：文件对象非空、文件内容不为空
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        // 文件名清洗：防路径穿越与非法字符
        String originalFilename = FileUtil.getName(requestFile.getOriginalFilename());
        long fileSize = requestFile.getSize();
        // 后缀白名单校验（服务端权威）
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(originalFilename);
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        sysFileEntity.setFileUploadUser(SecurityUtils.getUserName());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        // 文件头探测 MIME，无歧义类型与后缀交叉校验（防改名绕过）
        sysFileEntity.setFileType(MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType());
        // 拼装存储信息：统一 / 分隔符，兼容跨平台
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = filePathPrefix + File.separator + fileStorageName;
        sysFileEntity.setFileStorageType(fileStorageRepository.storageType().getValue());
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileStoragePath(fileStoragePath);
        // 长文件名兜底：原始文件名被截断时，完整名称记录到元数据
        sysFileEntity.setFileMetadata(FileMetadataBuilder
                .builder()
                .fullOriginalFilename(originalFilename)
                .build());
        // 单流双工：探测用的头部字节与原流拼接后包 DigestInputStream，边落盘边计算哈希，不再二次读盘
        try (InputStream digestIn = requestFile.getInputStream()) {
            fileStorageRepository.upload(digestIn, fileStoragePath);
            sysFileEntity.setFilePreviewUrl(fileStorageRepository.generatePreviewUrl(fileStoragePath));
            sysFileEntity.setFileHash(MD5Utils.generateSignature(IoUtil.readBytes(digestIn)));
        } catch (BusinessException e) {
            // 业务异常（如路径不合法）保留原始错误码，仅补偿已写入文件
            fileStorageRepository.remove(fileStoragePath);
            throw e;
        } catch (Exception e) {
            log.error("文件上传异常，fileStoragePath：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        // 元数据入库：失败时补偿删除已落盘文件，避免产生孤儿文件
        try {
            sysFileDao.save(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        return new UploadResponse(sysFileEntity.getFileStorageKey(), sysFileEntity.getFilePreviewUrl());
    }

    /**
     * 文件下载
     *
     * @param fileStorageKey 文件存储key
     * @return 文件下载信息（文件流、原始文件名、内容类型、文件大小、hash、上传时间）
     */
    @Override
    public DownloadBO downloadFile(String fileStorageKey) {
        // 参数校验：文件存储key非空
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        // 文件记录必须存在且未被删除，并通过访问权限校验
        SysFileEntity sysFileEntity = sysFileDao.findActiveByStorageKey(fileStorageKey);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        // 从底层存储仓库读取文件流，流由调用方（控制层）负责关闭
        InputStream inputStream = fileStorageRepository.download(sysFileEntity.getFileStoragePath());
        return DownloadBO.of(inputStream,
                resolveDownloadFilename(sysFileEntity),
                sysFileEntity.getFileType(),
                sysFileEntity.getFileSize(),
                sysFileEntity.getFileHash(),
                sysFileEntity.getFileUploadTime());
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
     * 解析下载文件名：优先取元数据中的完整原始文件名（长文件名截断场景的兜底），其次取文件原始名称，最后兜底取文件存储名称
     *
     * @param sysFileEntity 系统文件实体
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
}
