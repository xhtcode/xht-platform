package com.xht.platform.file.service;

import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.crypto.MD5Utils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * 文件存储服务实现类
 * 负责文件上传、存储、元数据入库等核心逻辑，对接底层文件存储仓库
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    /**
     * 文件存储仓库，对接底层存储实现（本地/对象存储等）
     */
    private final FileStorageRepository fileStorageRepository;

    /**
     * 文件DAO，负责文件元数据数据库持久化
     */
    private final SysFileDao sysFileDao;

    /**
     * 文件转换器：实体 <-> Response DTO
     */
    private final SysFileConverter sysFileConverter;

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
        // 参数校验：文件对象非空、文件内容不为空、存储路径前缀非空
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        SysFileEntity sysFileEntity = new SysFileEntity();
        String originalFilename = requestFile.getOriginalFilename();
        long fileSize = requestFile.getSize();
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        sysFileEntity.setFileOriginalName(SysFileUtils.truncateFileName(originalFilename));
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileType(requestFile.getContentType());
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        try (InputStream inputStream = requestFile.getInputStream()) {
            String fileStorageKey = IdUtils.simpleUUID();
            String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
            String fileStoragePath = Paths.get(filePathPrefix, fileStorageName).normalize().toString();
            sysFileEntity.setFileStorageType(fileStorageRepository.storageType().getValue());
            sysFileEntity.setFileStorageName(fileStorageName);
            sysFileEntity.setFileStorageKey(fileStorageKey);
            sysFileEntity.setFileStoragePath(fileStoragePath);
            String uploadPath = fileStorageRepository.upload(inputStream, fileStoragePath);
            // 根据上传后的文件字节计算MD5签名，用于文件完整性校验
            sysFileEntity.setFileHash(MD5Utils.generateSignature(FileUtil.readBytes(Paths.get(uploadPath))));
        } catch (Exception e) {
            log.error("文件上传异常", e);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        sysFileEntity.setFilePreviewUrl(null);
        FileMetadata fileMetadata = FileMetadataBuilder
                .builder()
                .fullOriginalFilename(originalFilename)
                .build();
        sysFileEntity.setFileMetadata(fileMetadata);
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileAssociateTime(null);
        sysFileEntity.setFileUploadUser("admin");
        sysFileDao.save(sysFileEntity);
        return sysFileConverter.toResponse(sysFileEntity);
    }
}
