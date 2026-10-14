package com.xht.platform.file.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.crypto.MD5Utils;
import com.xht.platform.common.file.FileMetadataBuilder;
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
import java.util.Date;

/**
 * 描述： 文件存储服务实现类
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    private final SysFileDao sysFileDao;

    private final SysFileConverter sysFileConverter;

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param fileUsed      文件用途
     * @return 文件信息
     */
    @Override
    public SysFileResponse uploadFile(MultipartFile multipartFile, String fileUsed) {
        AssertUtils.notNull(multipartFile, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        AssertUtils.isTrue(!multipartFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        SysFileEntity sysFileEntity = new SysFileEntity();
        String originalFilename = multipartFile.getOriginalFilename();
        long fileSize = multipartFile.getSize();
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        sysFileEntity.setFileOriginalName(SysFileUtils.truncateFileName(originalFilename));
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileType(multipartFile.getContentType());
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileStorageKey = IdUtils.simpleUUID();
            String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
            String fileStoragePath = Paths.get(DateUtil.format(new Date(), "yyyy/MM/dd"), fileStorageName).normalize().toString();
            sysFileEntity.setFileStorageType(fileStorageRepository.storageType().getValue());
            sysFileEntity.setFileStorageName(fileStorageName);
            sysFileEntity.setFileStorageKey(fileStorageKey);
            sysFileEntity.setFileStoragePath(fileStoragePath);
            String uploadPath = fileStorageRepository.upload(inputStream, fileStoragePath);
            sysFileEntity.setFileHash(MD5Utils.generateSignature(FileUtil.readBytes(Paths.get(uploadPath))));
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e);
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
