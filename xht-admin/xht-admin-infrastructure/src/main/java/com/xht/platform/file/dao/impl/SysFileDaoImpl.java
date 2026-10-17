package com.xht.platform.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 描述： 系统文件数据访问层实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysFileDaoImpl extends MapperRepositoryImpl<SysFileMapper, SysFileEntity> implements SysFileDao {

    /**
     * 允许排序的字段白名单（属性名 → 列 lambda），防止按不存在/恶意字段排序
     */
    private static final Map<String, SFunction<SysFileEntity, ?>> SORT_FIELD_MAP = Map.of(
            "id", SysFileEntity::getId,
            "fileOriginalName", SysFileEntity::getFileOriginalName,
            "fileSize", SysFileEntity::getFileSize,
            "fileUsed", SysFileEntity::getFileUsed,
            "fileSuffix", SysFileEntity::getFileSuffix,
            "fileUploadTime", SysFileEntity::getFileUploadTime,
            "createTime", SysFileEntity::getCreateTime
    );

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
        // @formatter:off
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
                SysFileEntity::getFileStorageType,
                SysFileEntity::getFileStorageKey,
                SysFileEntity::getFileStorageName,
                SysFileEntity::getFileThumbKey,
                SysFileEntity::getFileThumbPath,
                SysFileEntity::getFilePreviewUrl,
                SysFileEntity::getFileMetadata,
                SysFileEntity::getFileUploadTime,
                SysFileEntity::getFileAssociateTime,
                SysFileEntity::getFileUploadUser
        );
        // 关键字模糊匹配文件原始名称、文件存储key与文件存储名称
        wrapper.and(condition(query.getKeyWord()), queryWrapper -> queryWrapper
                .like(SysFileEntity::getFileOriginalName, query.getKeyWord())
                .or()
                .like(SysFileEntity::getFileStorageKey, query.getKeyWord())
                .or()
                .like(SysFileEntity::getFileStorageName, query.getKeyWord())
        );
        wrapper.eq(condition(query.getFileStorageKey()), SysFileEntity::getFileStorageKey, query.getFileStorageKey());
        wrapper.eq(condition(query.getFileSuffix()), SysFileEntity::getFileSuffix, query.getFileSuffix());
        wrapper.eq(condition(query.getFileType()), SysFileEntity::getFileType, query.getFileType());
        wrapper.eq(Objects.nonNull(query.getFileStatus()), SysFileEntity::getFileStatus, query.getFileStatus());
        wrapper.eq(condition(query.getFileUsed()), SysFileEntity::getFileUsed, query.getFileUsed());
        wrapper.eq(condition(query.getFileUploadUser()), SysFileEntity::getFileUploadUser, query.getFileUploadUser());
        wrapper.between(Objects.nonNull(query.getStartUploadTime()) || Objects.nonNull(query.getEndUploadTime()),
                SysFileEntity::getFileUploadTime, query.getStartUploadTime(), query.getEndUploadTime());
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        // 排序白名单校验：仅白名单内的字段参与排序，非法字段忽略
        String ascField = sanitizeSortField(query.getAscName());
        String descField = sanitizeSortField(query.getDescName());
        if (ascField != null) {
            wrapper.orderByAsc(SORT_FIELD_MAP.get(ascField));
        }
        if (descField != null) {
            wrapper.orderByDesc(SORT_FIELD_MAP.get(descField));
        }
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
     * 根据文件ID回写缩略图存储信息
     *
     * @param fileId        文件ID
     * @param fileThumbKey  缩略图存储key
     * @param fileThumbPath 缩略图存储路径
     */
    @Override
    public void updateThumbnailById(Long fileId, String fileThumbKey, String fileThumbPath) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileThumbKey, fileThumbKey);
        wrapper.set(SysFileEntity::getFileThumbPath, fileThumbPath);
        wrapper.eq(SysFileEntity::getId, fileId);
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
        update(wrapper);
    }

    /**
     * 分页查询过期的未关联文件（UPLOADED 且上传时间早于指定时间）
     *
     * @param page             分页对象
     * @param expireBeforeTime 过期时间界限
     * @return 过期的未关联文件列表
     */
    @Override
    public List<SysFileEntity> findExpiredUploadedFiles(Page<SysFileEntity> page, LocalDateTime expireBeforeTime) {
        LambdaQueryWrapper<SysFileEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        wrapper.lt(SysFileEntity::getFileUploadTime, expireBeforeTime);
        return page(page, wrapper).getRecords();
    }

    /**
     * 根据ID更新文件状态与关联时间（单文件，禁止使用批处理）
     *
     * @param fileId            文件ID
     * @param fileStatusEnum    目标状态
     * @param fileAssociateTime 关联时间（解绑时传 null）
     */
    @Override
    public void updateStatusAndAssociateTimeById(Long fileId, FileStatusEnum fileStatusEnum, LocalDateTime fileAssociateTime) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileStatus, fileStatusEnum);
        wrapper.set(SysFileEntity::getFileAssociateTime, fileAssociateTime);
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
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysFileEntity, ?> getFieldId() {
        return SysFileEntity::getId;
    }

    /**
     * 校验排序字段是否在白名单内
     *
     * @param sortField 排序字段（属性名）
     * @return 白名单内的字段名，非法字段返回 null
     */
    static String sanitizeSortField(String sortField) {
        if (sortField == null || sortField.isBlank()) {
            return null;
        }
        String normalized = sortField.strip();
        return SORT_FIELD_MAP.containsKey(normalized) ? normalized : null;
    }

}
