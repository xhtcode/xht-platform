package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.cache.utils.Keys;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.XhtFileBO;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.framework.utils.AssertUtils;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.framework.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 系统文件数据访问层实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class SysFileDaoImpl extends MapperRepositoryImpl<SysFileMapper, SysFileEntity> implements SysFileDao {

    private final RedisRepository redisRepository;

    /**
     * 分页查询文件
     * <p>
     * 查询条件均为动态条件：关键字模糊匹配文件原始名称、文件存储key与文件存储名称，
     * 其余字段非空时才参与等值/模糊过滤；排序仅支持白名单字段
     *
     * @param page  分页对象（包含分页参数与排序信息）
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Page<SysFileEntity> findPageList(Page<SysFileEntity> page, SysFileQuery query) {
        LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
        // 关键字模糊匹配文件原始名称、文件存储key与文件存储名称
        wrapper.and(condition(query.getKeyWord()), queryWrapper -> queryWrapper
                .like(SysFileEntity::getFileOriginalName, query.getKeyWord())
                .or()
                .like(SysFileEntity::getFileStorageKey, query.getKeyWord())
                .or()
                .like(SysFileEntity::getFileStorageName, query.getKeyWord())
        );
        wrapper.like(condition(query.getFileStorageName()), SysFileEntity::getFileStorageName, query.getFileStorageName());
        wrapper.eq(condition(query.getFileStorageKey()), SysFileEntity::getFileStorageKey, query.getFileStorageKey());
        wrapper.eq(Objects.nonNull(query.getFileStatus()), SysFileEntity::getFileStatus, query.getFileStatus());
        // @formatter:on
        return page(page, wrapper);
    }

    /**
     * 根据文件ID更新文件状态
     *
     * @param fileId         文件ID
     * @param fileStatusEnum 文件状态枚举
     */
    @Override
    public void updateFileStatusById(Long fileId, FileStatusEnum fileStatusEnum) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileStatus, fileStatusEnum);
        wrapper.eq(SysFileEntity::getId, fileId);
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        update(wrapper);
    }

    /**
     * 根据ID查找实体
     *
     * @param id 实体ID
     * @return 实体
     */
    @Override
    public SysFileEntity findById(Serializable id) {
        LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileEntity::getId, id);
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        return super.getOne(wrapper);
    }

    /**
     * 检查是否存在指定ID的实体
     *
     * @param id 实体ID
     * @return 是否存在 true:存在 false:不存在
     */
    @Override
    public boolean existsById(Serializable id) {
        LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileEntity::getId, id);
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        return super.exists(wrapper);
    }

    /**
     * 根据文件存储键查询文件
     *
     * @param fileStorageKey 文件存储键
     * @return 文件信息
     */
    @Override
    public SysFileEntity findByFileStorageKey(String fileStorageKey) {
        LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        return super.getOne(wrapper);
    }

    /**
     * 根据文件存储键查询文件
     *
     * @param fileStorageKey 文件存储键
     * @param fileUsed       文件用途
     * @return 文件信息
     */
    @Override
    public XhtFileBO findFileInfoByFileStorageKey(String fileStorageKey, String fileUsed) {
        AssertUtils.hasText(fileStorageKey, "文件存储键不能为空");
        AssertUtils.hasText(fileUsed, "文件用途不能为空");
        String key = Keys.createKey("xht:file", fileStorageKey, fileUsed);
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
            SysFileEntity sysFileEntity = getOne(wrapper);
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
            redisRepository.set(key, xhtFileBO, Keys.randomExpire(60, 120), TimeUnit.MINUTES);
        }
        return xhtFileBO;
    }

    @Override
    protected SFunction<SysFileEntity, ?> getFieldId() {
        return SysFileEntity::getId;
    }

}
