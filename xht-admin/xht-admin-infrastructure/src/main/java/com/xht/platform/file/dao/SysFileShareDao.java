package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.query.SysFileShareQuery;
import com.xht.platform.file.entity.SysFileShareEntity;

import java.util.Optional;

/**
 * 描述： 文件分享数据访问层
 *
 * @author xht
 **/
public interface SysFileShareDao extends MapperRepository<SysFileShareEntity> {

    /**
     * 根据分享定位串查询
     *
     * @param shareToken 定位串
     * @return 分享
     */
    Optional<SysFileShareEntity> findByToken(String shareToken);

    /**
     * 我创建的分享分页
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SysFileShareEntity> findPageByCreator(Page<SysFileShareEntity> page, SysFileShareQuery query);

    /**
     * 原子自增访问次数
     *
     * @param shareId 分享ID
     * @return 影响行数
     */
    int incrementAccessCount(Long shareId);

    /**
     * 更新最后访问信息
     *
     * @param shareId 分享ID
     * @param ip      访问IP
     */
    void updateLastAccess(Long shareId, String ip);

    /**
     * 查询文件当前有效分享的最晚到期时间
     *
     * @param fileId 文件ID
     * @param now    当前时间
     * @return 最晚到期时间，无有效分享返回 null
     */
    java.time.LocalDateTime findMaxActiveExpire(Long fileId, java.time.LocalDateTime now);

}
