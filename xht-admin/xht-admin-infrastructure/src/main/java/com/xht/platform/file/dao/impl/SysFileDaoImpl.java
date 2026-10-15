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

/**
 * 描述： 系统文件数据访问层实现类
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysFileDaoImpl extends MapperRepositoryImpl<SysFileMapper, SysFileEntity> implements SysFileDao {

    /**
     * 分页查询文件
     * <p>
     * 查询条件均为动态条件：关键字模糊匹配文件原始名称与文件存储名称，
     * 其余字段非空时才参与等值/模糊过滤
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
                SysFileEntity::getFileStoragePath,
                SysFileEntity::getFilePreviewUrl,
                SysFileEntity::getFileUploadTime,
                SysFileEntity::getFileAssociateTime,
                SysFileEntity::getFileUploadUser
        );
        // 关键字模糊匹配文件原始名称与文件存储名称
        wrapper.and(condition(query.getKeyWord()), queryWrapper -> queryWrapper
                .like(SysFileEntity::getFileStorageKey, query.getKeyWord())
                .or()
                .like(SysFileEntity::getFileStorageName, query.getKeyWord())
        );
        wrapper.eq(condition(query.getFileStorageKey()), SysFileEntity::getFileStorageKey, query.getFileStorageKey());
        wrapper.eq(condition(query.getFileSuffix()), SysFileEntity::getFileSuffix, query.getFileSuffix());
        wrapper.ne(SysFileEntity::getFileStatus, FileStatusEnum.DELETED);
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
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysFileEntity, ?> getFieldId() {
        return SysFileEntity::getId;
    }
}
