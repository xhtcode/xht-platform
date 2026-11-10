package com.xht.platform.file.dao;

import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.entity.SysFileConvertEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件转换任务数据访问层
 *
 * @author xht
 **/
public interface SysFileConvertDao extends MapperRepository<SysFileConvertEntity> {

    /**
     * 根据文件与目标格式查询任务
     *
     * @param fileId       文件ID
     * @param targetFormat 目标格式
     * @return 转换任务
     */
    Optional<SysFileConvertEntity> findByFileAndTarget(Long fileId, String targetFormat);

    /**
     * 查询已过期且成功的任务
     *
     * @param now 当前时间
     * @param limit 单批数量
     * @return 任务列表
     */
    List<SysFileConvertEntity> findExpiredSuccess(LocalDateTime now, int limit);

    /**
     * 查询指定文件全部任务
     *
     * @param fileId 文件ID
     * @return 任务列表
     */
    List<SysFileConvertEntity> findByFileId(Long fileId);

}
