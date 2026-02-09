package com.xht.modules.admin.notice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;
import com.xht.modules.admin.notice.dao.SysNoticeDao;
import com.xht.modules.admin.notice.dao.mapper.SysNoticeMapper;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.entity.SysNoticeEntity;
import com.xht.modules.admin.notice.enums.NoticeStatusEnums;
import com.xht.modules.admin.notice.enums.NoticeTimedPublishEnums;
import com.xht.modules.admin.notice.enums.NoticeTopEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述 ： 系统管理-通知详情 Dao
 *
 * @author xht
 **/
@Slf4j
@Repository
public class SysNoticeDaoImpl extends MapperRepositoryImpl<SysNoticeMapper, SysNoticeEntity> implements SysNoticeDao {

    /**
     * 根据通知id 修改状态
     *
     * @param noticeId     通知id
     * @param noticeStatus 通知状态
     */
    @Override
    public void updateStatusById(Long noticeId, NoticeStatusEnums noticeStatus) {
        ThrowUtils.throwIf(Objects.equals(noticeStatus, NoticeStatusEnums.NOT_PUBLISH), "请选择正确的状态！");
        LambdaUpdateWrapper<SysNoticeEntity> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(SysNoticeEntity::getNoticeStatus, noticeStatus);
        queryWrapper.set(Objects.equals(noticeStatus, NoticeStatusEnums.PUBLISH), SysNoticeEntity::getNoticePublishTime, LocalDateTime.now());
        queryWrapper.set(Objects.equals(noticeStatus, NoticeStatusEnums.UNDER_SHELVE), SysNoticeEntity::getNoticeOfflineTime, LocalDateTime.now());
        queryWrapper.set(Objects.equals(noticeStatus, NoticeStatusEnums.EXPIRED), SysNoticeEntity::getNoticeExpireTime, LocalDateTime.now());
        queryWrapper.eq(SysNoticeEntity::getId, noticeId);
        update(queryWrapper);
    }

    /**
     * 根据通知id 置顶
     *
     * @param noticeId 通知id
     * @param isTop    是否置顶
     */
    @Override
    public void updateIsTopById(Long noticeId, NoticeTopEnums isTop) {
        LambdaUpdateWrapper<SysNoticeEntity> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(SysNoticeEntity::getNoticeTop, isTop);
        queryWrapper.eq(SysNoticeEntity::getId, noticeId);
        update(queryWrapper);
    }

    /**
     * 根据通知id 修改已读人数
     *
     * @param noticeId 通知id
     */
    @Override
    public void updateReadNumById(Long noticeId) {
        LambdaUpdateWrapper<SysNoticeEntity> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.setIncrBy(SysNoticeEntity::getNoticeReadCount, 1);
        queryWrapper.eq(SysNoticeEntity::getId, noticeId);
        update(queryWrapper);
    }

    /**
     * 根据通知id 修改点击次数
     *
     * @param noticeId 通知id
     */
    @Override
    public void updateClickNumById(Long noticeId) {
        LambdaUpdateWrapper<SysNoticeEntity> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.setIncrBy(SysNoticeEntity::getNoticeClickCount, 1);
        queryWrapper.eq(SysNoticeEntity::getId, noticeId);
        update(queryWrapper);
    }

    /**
     * 根据通知id 获取定时发布的数据
     *
     * @param noticeId 通知id
     */
    @Override
    public List<Long> listByReleaseTime(Long noticeId) {
        LambdaQueryWrapper<SysNoticeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysNoticeEntity::getId);
        queryWrapper.eq(SysNoticeEntity::getId, noticeId);
        queryWrapper.eq(SysNoticeEntity::getNoticeStatus, NoticeStatusEnums.NOT_PUBLISH);
        queryWrapper.eq(SysNoticeEntity::getNoticeTimedPublish, NoticeTimedPublishEnums.PUBLISH);
        queryWrapper.ge(SysNoticeEntity::getNoticePublishTime, LocalDateTime.now());
        return list(queryWrapper).stream().map(SysNoticeEntity::getId).collect(Collectors.toList());
    }

    /**
     * 分页查询
     *
     * @param page        分页参数
     * @param noticeQuery 查询参数
     * @return 分页数据
     */
    @Override
    public Page<SysNoticeResponse> findPageList(Page<SysNoticeEntity> page, SysNoticeQuery noticeQuery) {
        return baseMapper.findPageList(page, noticeQuery);
    }

    /**
     * 获取主键字段名
     *
     * @return 主键字段名
     */
    @Override
    protected SFunction<SysNoticeEntity, ?> getFieldId() {
        return SysNoticeEntity::getId;
    }

}