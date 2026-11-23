package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.constant.FileConstant;
import com.xht.framework.file.core.AbstractUploadBO;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.framework.file.core.XhtFileBO;
import com.xht.framework.file.enums.FileStatusEnum;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileUsedEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
     * Redis仓库
     */
    private final RedisRepository redisRepository;

    /**
     * 上传文件
     *
     * @param multipartFileUploadBO 上传入参（封装MultipartFile）
     * @return 文件上传响应信息
     * @throws FileStorageException 文件存储异常
     */
    @Override
    public <T> SysFileEntity uploadMultipartFile(AbstractUploadBO<T> multipartFileUploadBO) {
        AssertUtils.notNull(multipartFileUploadBO, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        multipartFileUploadBO.validate();
        String fileUsed = multipartFileUploadBO.getFileUsed();
        String filePathPrefix = multipartFileUploadBO.getFilePathPrefix();
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(multipartFileUploadBO.getFileOriginalName());
        sysFileEntity.setFileSize(multipartFileUploadBO.getFileSize());
        sysFileEntity.setFileSizeInfo(multipartFileUploadBO.getFileSizeInfo());
        sysFileEntity.setFileSuffix(multipartFileUploadBO.getFileSuffix());
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        sysFileEntity.setFileUploadUser(multipartFileUploadBO.getFileUploadUser());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileType(multipartFileUploadBO.getContentType());
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + multipartFileUploadBO.getFileSuffix();
        String fileStoragePath = filePathPrefix + File.separator + fileStorageName;
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileDesc(multipartFileUploadBO.getFileDesc());
        try (InputStream digestIn = multipartFileUploadBO.getInputStream()) {
            UploadResponse upload = fileStorageRepository.upload(digestIn, fileStoragePath);
            sysFileEntity.setFileStorageType(upload.getStorageType());
            sysFileEntity.setFileHash(upload.getFileMd5());
            sysFileEntity.setFileStoragePath(upload.getFileStoragePath());
        } catch (Exception e) {
            log.error("文件上传异常:fileStoragePath:{} 信息:{}", fileStoragePath, e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        try {
            sysFileMapper.insert(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败：{}", fileStoragePath, e);
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
     * 根据文件存储键查询文件
     *
     * @param fileStorageKey 文件存储键
     * @param fileUsed       文件用途
     * @param timeout        超时时间
     * @param timeUnit       时间单位
     * @return 文件信息
     */
    @Override
    public XhtFileBO findFileInfoByFileStorageKey(String fileStorageKey, String fileUsed, long timeout, TimeUnit timeUnit) {
        AssertUtils.hasText(fileStorageKey, "文件存储键不能为空");
        AssertUtils.hasText(fileUsed, "文件用途不能为空");
        AssertUtils.isTrue(timeout > 0, "超时时间必须大于0");
        AssertUtils.notNull(timeUnit, "时间单位不能为空");
        String key = Keys.createKey(FileConstant.FILE_CACHE_KEY_PREFIX, fileStorageKey, fileUsed);
        XhtFileBO xhtFileBO = redisRepository.get(key);
        if (Objects.isNull(xhtFileBO)) {
            LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(
                    SysFileEntity::getId,
                    SysFileEntity::getFileOriginalName,
                    SysFileEntity::getFileSize,
                    SysFileEntity::getFileSizeInfo,
                    SysFileEntity::getFileHash,
                    SysFileEntity::getFileType,
                    SysFileEntity::getFileSuffix,
                    SysFileEntity::getFileStatus,
                    SysFileEntity::getFileUsed,
                    SysFileEntity::getFileStorageKey,
                    SysFileEntity::getFileStorageName,
                    SysFileEntity::getFileStoragePath,
                    SysFileEntity::getFileUploadTime,
                    SysFileEntity::getFileUploadUser,
                    SysFileEntity::getFileAssociateTime,
                    SysFileEntity::getFilePreviewType
            );
            wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
            wrapper.eq(SysFileEntity::getFileUsed, fileUsed);
            SysFileEntity sysFileEntity = sysFileMapper.selectOne(wrapper);
            AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
            xhtFileBO = XhtFileBO.builder()
                    .fileOriginalName(sysFileEntity.getFileOriginalName())
                    .fileSize(sysFileEntity.getFileSize())
                    .fileSizeInfo(sysFileEntity.getFileSizeInfo())
                    .fileHash(sysFileEntity.getFileHash())
                    .fileType(sysFileEntity.getFileType())
                    .fileSuffix(sysFileEntity.getFileSuffix())
                    .fileStatus(sysFileEntity.getFileStatus())
                    .fileUsed(sysFileEntity.getFileUsed())
                    .fileStorageKey(sysFileEntity.getFileStorageKey())
                    .fileStorageName(sysFileEntity.getFileStorageName())
                    .fileStoragePath(sysFileEntity.getFileStoragePath())
                    .fileUploadTime(sysFileEntity.getFileUploadTime())
                    .fileUploadUser(sysFileEntity.getFileUploadUser())
                    .fileAssociateTime(sysFileEntity.getFileAssociateTime())
                    .filePreviewType(sysFileEntity.getFilePreviewType())
                    .build();
            redisRepository.set(key, xhtFileBO, timeout, timeUnit);
        }
        return xhtFileBO;
    }

    /**
     * 关联文件
     *
     * @param fileStorageKey 文件存储名称
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    @Override
    public void associateFile(String fileStorageKey, String fileUsed, String fileDesc) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(StringUtils.hasText(fileUsed), SysFileEntity::getFileUsed, fileUsed);
        wrapper.set(StringUtils.hasText(fileDesc), SysFileEntity::getFileDesc, fileDesc);
        wrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        wrapper.set(SysFileEntity::getFileAssociateTime, LocalDateTime.now());
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        sysFileMapper.update(wrapper);
    }

    /**
     * 根据文件存储key删除文件
     *
     * @param fileStorageKey 文件存储key
     * @throws FileStorageException 文件存储异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByFileStorageKey(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, "文件存储键不能为空");
        LambdaUpdateWrapper<SysFileEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        updateWrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        sysFileMapper.update(updateWrapper);
    }

    /**
     * 根据用户名删除用户头像
     *
     * @param username          用户名
     * @param newFileStorageKey 新文件存储key
     * @throws FileStorageException 文件存储异常
     */
    @Override
    public void removeUserAvatar(String username, String newFileStorageKey) {
        LambdaQueryWrapper<SysFileEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysFileEntity::getId, SysFileEntity::getFileStorageKey);
        queryWrapper.eq(SysFileEntity::getFileUsed, FileUsedEnum.AVATAR.getValue());
        queryWrapper.eq(SysFileEntity::getFileUploadUser, username);
        queryWrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        SysFileEntity sysFileEntity = sysFileMapper.selectOne(queryWrapper);
        LambdaUpdateWrapper<SysFileEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        updateWrapper.eq(SysFileEntity::getFileUsed, FileUsedEnum.AVATAR.getValue());
        updateWrapper.eq(SysFileEntity::getFileUploadUser, username);
        updateWrapper.ne(SysFileEntity::getFileStorageKey, newFileStorageKey);
        sysFileMapper.update(updateWrapper);
        if (Objects.nonNull(sysFileEntity)) {
            redisRepository.delete(Keys.createKey(FileConstant.FILE_CACHE_KEY_PREFIX, sysFileEntity.getFileStorageKey(), FileUsedEnum.AVATAR.getValue()));
        }
    }

}
