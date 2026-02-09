package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysNoticeAttachmentDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticeAttachmentMapper;
import com.xht.modules.admin.notice.entity.SysNoticeAttachmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 ： 系统管理-通知附件 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysNoticeAttachmentDaoImpl extends MapperRepositoryImpl<SysNoticeAttachmentMapper, SysNoticeAttachmentEntity> implements SysNoticeAttachmentDao {

    /**
     * 根据通知id 删除所有附件信息
     *
     * @param noticeId 通知id
     */
    @Override
    public void removeByNoticeId(Long noticeId) {
        LambdaUpdateWrapper<SysNoticeAttachmentEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysNoticeAttachmentEntity::getNoticeId, noticeId);
        remove(updateWrapper);
    }

    /**
     * 根据通知id 查询所有附件信息
     *
     * @param noticeId 通知id
     * @return 附件信息
     */
    @Override
    public List<SysNoticeAttachmentEntity> findListByNoticeId(Long noticeId) {
        LambdaQueryWrapper<SysNoticeAttachmentEntity> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(SysNoticeAttachmentEntity::getNoticeId, noticeId);
        return list(updateWrapper);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysNoticeAttachmentEntity, ?> getFieldId() {
        return SysNoticeAttachmentEntity::getId;
    }


}