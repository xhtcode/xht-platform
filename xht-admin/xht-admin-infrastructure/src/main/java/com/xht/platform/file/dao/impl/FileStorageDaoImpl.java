package com.xht.platform.file.dao.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.FileMetadataSupplier;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FileStorageDaoImpl implements FileStorageDao {

    /**
     * 文件存储仓库，对接底层存储实现（本地/对象存储等）
     */
    private final FileStorageRepository fileStorageRepository;

    /**
     * 文件Mapper
     */
    private final SysFileMapper sysFileMapper;

    /**
     * 上传文件
     *
     * @param multipartFileUploadBO 上传入参（封装MultipartFile）
     * @param fileMetadataSupplier  文件元数据提供者
     * @return 文件上传响应信息
     * @throws FileStorageException 文件存储异常
     */
    @Override
    public SysFileEntity uploadMultipartFile(UploadBO<MultipartFile> multipartFileUploadBO, FileMetadataSupplier<MultipartFile> fileMetadataSupplier) {
        AssertUtils.notNull(multipartFileUploadBO, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        AssertUtils.notNull(fileMetadataSupplier, BusinessErrorCode.PARAM_ERROR);
        MultipartFile requestFile = multipartFileUploadBO.getRequestFile();
        String fileUsed = multipartFileUploadBO.getFileUsed();
        String filePathPrefix = multipartFileUploadBO.getFilePathPrefix();
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
        sysFileEntity.setFileUploadUser(multipartFileUploadBO.getFileUploadUser());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileType(MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType());
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = filePathPrefix + File.separator + fileStorageName;
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileDesc(multipartFileUploadBO.getFileDesc());
        sysFileEntity.setFileMetadata(fileMetadataSupplier.get(multipartFileUploadBO));
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
            sysFileMapper.insert(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        return sysFileEntity;
    }

    /**
     * 下载文件
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param fileStoragePath 文件存储路径
     * @return 文件输入流
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    @Override
    public InputStream download(String fileStoragePath) {
        AssertUtils.notNull(fileStoragePath, BusinessErrorCode.FILE_NOT_EXIST);
        return fileStorageRepository.download(fileStoragePath);
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
    public void associateFile(String fileStorageKey, String fileUsed, String fileDesc) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileUsed, fileUsed);
        wrapper.set(SysFileEntity::getFileDesc, fileDesc);
        wrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        wrapper.set(SysFileEntity::getFileAssociateTime, LocalDateTime.now());
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        AssertUtils.isTrue(SqlHelper.retBool(sysFileMapper.update(wrapper)), BusinessErrorCode.FILE_ASSOCIATE_FAIL);
    }

    /**
     * 解关联文件业务（单文件）：标记为已解关联（DISASSOCIATED）并记录解关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     */
    @Override
    public void disassociateFile(String fileStorageKey) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        AssertUtils.isTrue(SqlHelper.retBool(sysFileMapper.update(wrapper)), BusinessErrorCode.FILE_DISASSOCIATE_FAIL);
    }

}
