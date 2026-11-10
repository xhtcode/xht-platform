package com.xht.platform.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.mybatis.repository.MapperRepository;
import com.xht.platform.file.domain.query.SysFileShareReceivedQuery;
import com.xht.platform.file.entity.SysFileShareReceiverEntity;

import java.util.List;
import java.util.Optional;

/**
 * 描述： 文件分享接收人数据访问层
 *
 * @author xht
 **/
public interface SysFileShareReceiverDao extends MapperRepository<SysFileShareReceiverEntity> {

    /**
     * 根据分享与接收人查询
     *
     * @param shareId    分享ID
     * @param receiverId 接收人ID
     * @return 接收人记录
     */
    Optional<SysFileShareReceiverEntity> findByShareAndReceiver(Long shareId, Long receiverId);

    /**
     * 分享给我的分页
     *
     * @param page  分页参数
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SysFileShareReceiverEntity> findReceivedPage(Page<SysFileShareReceiverEntity> page, SysFileShareReceivedQuery query);

    /**
     * 未读数量
     *
     * @param receiverId 接收人ID
     * @return 未读数量
     */
    long countUnread(Long receiverId);

    /**
     * 标记已读
     *
     * @param shareId    分享ID
     * @param receiverId 接收人ID
     */
    void markRead(Long shareId, Long receiverId);

    /**
     * 分享的接收人列表
     *
     * @param shareId 分享ID
     * @return 接收人列表
     */
    List<SysFileShareReceiverEntity> findByShareId(Long shareId);

}
