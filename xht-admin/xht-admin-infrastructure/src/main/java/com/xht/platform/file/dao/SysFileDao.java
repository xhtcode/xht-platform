package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述： 系统文件数据访问层
 *
 * @author xht
 **/
public interface SysFileDao extends MapperRepository<SysFileEntity> {

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
    Page<SysFileEntity> findPageList(Page<SysFileEntity> page, SysFileQuery query);

    /**
     * 根据文件ID更新文件状态
     *
     * @param fileId         文件ID
     * @param fileStatusEnum 文件状态枚举
     */
    void updateFileStatusById(Long fileId, FileStatusEnum fileStatusEnum);

    /**
     * 根据文件ID回写缩略图存储信息（异步生成完成后调用）
     *
     * @param fileId        文件ID
     * @param fileThumbKey  缩略图存储key
     * @param fileThumbPath 缩略图存储路径
     */
    void updateThumbnailById(Long fileId, String fileThumbKey, String fileThumbPath);

    /**
     * 分页查询过期的未关联文件（孤儿文件候选：UPLOADED 且上传时间早于指定时间）
     *
     * @param page             分页对象
     * @param expireBeforeTime 过期时间界限
     * @return 过期的未关联文件列表
     */
    List<SysFileEntity> findExpiredUploadedFiles(Page<SysFileEntity> page, LocalDateTime expireBeforeTime);

    /**
     * 根据ID更新文件状态与关联时间（单文件，禁止使用批处理）
     *
     * @param fileId            文件ID
     * @param fileStatusEnum    目标状态
     * @param fileAssociateTime 关联时间（解绑时传 null）
     */
    void updateStatusAndAssociateTimeById(Long fileId, FileStatusEnum fileStatusEnum, LocalDateTime fileAssociateTime);
}
