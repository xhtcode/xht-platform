package com.xht.modules.admin.notice.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.core.domain.response.PageResponse;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.utils.ThrowUtils;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.security.core.userdetails.BasicUserDetails;
import com.xht.modules.admin.notice.converter.SysNoticeAttachmentConverter;
import com.xht.modules.admin.notice.converter.SysNoticeConverter;
import com.xht.modules.admin.notice.dao.SysNoticeAttachmentDao;
import com.xht.modules.admin.notice.dao.SysNoticeDao;
import com.xht.modules.admin.notice.dao.SysNoticeTypeDao;
import com.xht.modules.admin.notice.dao.SysNoticeUserOperateDao;
import com.xht.modules.admin.notice.domain.form.SysNoticeForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.domain.vo.NoticeVo;
import com.xht.modules.admin.notice.entity.SysNoticeAttachmentEntity;
import com.xht.modules.admin.notice.entity.SysNoticeEntity;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;
import com.xht.modules.admin.notice.enums.*;
import com.xht.modules.admin.notice.service.ISysNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 系统管理-通知详情 的数据库操作Service实现
 *
 * @author admin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ISysNoticeServiceImpl implements ISysNoticeService {

    private final SysNoticeDao sysNoticeDao;

    private final SysNoticeTypeDao sysNoticeTypeDao;

    private final SysNoticeAttachmentDao sysNoticeAttachmentDao;

    private final SysNoticeUserOperateDao sysNoticeUserOperateDao;

    private final SysNoticeConverter sysNoticeConverter;

    private final SysNoticeAttachmentConverter sysNoticeAttachmentConverter;

    /**
     * 创建通知详情
     *
     * @param form 通知详情表单请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysNoticeForm form) {
        // 1、校验上传表单数据
        ThrowUtils.notNull(form, "通知详情表单参数不能为空");

        // 校验通知类型是否存在
        Boolean exists = sysNoticeTypeDao.exists(SysNoticeTypeEntity::getId, form.getNoticeTypeId());
        ThrowUtils.throwIf(!exists, "通知类型不存在!");

        // 生成通知ID
        long noticeId = IdUtil.getSnowflakeNextId();

        // 封装通知实体
        SysNoticeEntity noticeEntity = sysNoticeConverter.toEntity(form);
        noticeEntity.setId(noticeId);
        noticeEntity.setNoticeStatus(NoticeStatusEnums.NOT_PUBLISH);

        // 处理通知附件
        List<SysNoticeAttachmentEntity> attachmentEntityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(form.getAttachmentList())) {
            attachmentEntityList = sysNoticeAttachmentConverter.toEntity(form.getAttachmentList(), noticeId);
        }

        // 处理定时发布时间
        if (Objects.equals(NoticeTimedPublishEnums.PUBLISH, noticeEntity.getNoticeTimedPublish())) {
            ThrowUtils.notNull(form.getNoticePublishTime(), "定时发布时间不能为空");
            noticeEntity.setNoticePublishTime(form.getNoticePublishTime());
        } else {
            noticeEntity.setNoticeStatus(NoticeStatusEnums.PUBLISH);
            noticeEntity.setNoticePublishTime(LocalDateTime.now());
        }

        // 处理跳转URL
        handleJumpUrlValidation(noticeEntity);

        // 3、保存通知详情
        sysNoticeDao.save(noticeEntity);

        // 4、保存通知附件
        if (!CollectionUtils.isEmpty(attachmentEntityList)) {
            sysNoticeAttachmentDao.saveAll(attachmentEntityList);
        }
    }

    private void handleJumpUrlValidation(SysNoticeEntity noticeEntity) {
        if (Objects.equals(NoticeJumpTypeEnums.NO_JUMP, noticeEntity.getNoticeJumpType())) {
            noticeEntity.setNoticeJumpUrl(null);
        } else {
            ThrowUtils.hasText(noticeEntity.getNoticeJumpUrl(), "跳转地址不能为空");
        }
    }


    /**
     * 根据ID删除通知详情
     *
     * @param id 通知详情ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Long id) {
        sysNoticeDao.removeById(id);
    }

    /**
     * 根据ID更新通知详情
     *
     * @param form 通知详情更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(SysNoticeForm form) {
        // 检查通知是否存在
        Boolean existingNotice = sysNoticeDao.exists(SysNoticeEntity::getId, form.getId());
        ThrowUtils.throwIf(!existingNotice, "通知不存在!");
        // 校验通知类型是否存在
        if (form.getNoticeTypeId() != null) {
            Boolean exists = sysNoticeTypeDao.exists(SysNoticeTypeEntity::getId, form.getNoticeTypeId());
            ThrowUtils.throwIf(!exists, "通知类型不存在!");
        }
        // 处理通知附件
        List<SysNoticeAttachmentEntity> attachmentEntityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(form.getAttachmentList())) {
            attachmentEntityList = sysNoticeAttachmentConverter.toEntity(form.getAttachmentList(), form.getId());
        }
        // 处理定时发布时间
        if (Objects.equals(NoticeTimedPublishEnums.PUBLISH, form.getNoticeTimedPublish())) {
            ThrowUtils.notNull(form.getNoticePublishTime(), "定时发布时间不能为空");
        }
        // 处理跳转URL
        if (Objects.equals(NoticeJumpTypeEnums.NO_JUMP, form.getNoticeJumpType())) {
            form.setNoticeJumpUrl(null);
        } else {
            ThrowUtils.hasText(form.getNoticeJumpUrl(), "跳转地址不能为空");
        }
        // 3、更新通知详情
        sysNoticeDao.updateFormRequest(form);
        // 4、删除旧附件并保存新附件
        sysNoticeAttachmentDao.removeByNoticeId(form.getId());
        if (!CollectionUtils.isEmpty(attachmentEntityList)) {
            sysNoticeAttachmentDao.saveAll(attachmentEntityList);
        }
    }

    /**
     * 根据通知id 发布
     *
     * @param noticeId 通知id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishNoticeId(Long noticeId) {
        SysNoticeEntity noticeEntity = sysNoticeDao.findById(noticeId);
        ThrowUtils.notNull(noticeEntity, "通知不存在!");
        if (!(Objects.equals(noticeEntity.getNoticeStatus(), NoticeStatusEnums.NOT_PUBLISH) || Objects.equals(noticeEntity.getNoticeStatus(), NoticeStatusEnums.UNDER_SHELVE))) {
            throw new BusinessException("通知状态不匹配!");
        }
        // 校验定时发布时间是否已到
        if (Objects.equals(NoticeTimedPublishEnums.PUBLISH, noticeEntity.getNoticeTimedPublish())) {
            if (LocalDateTime.now().isBefore(noticeEntity.getNoticePublishTime())) {
                throw new BusinessException("定时发布时间未到,通知发布异常!");
            }
        }
        // 校验过期时间是否过期
        if (LocalDateTime.now().isAfter(noticeEntity.getNoticeExpireTime())) {
            throw new BusinessException("通知已过期,通知禁止发布!");
        }
        sysNoticeDao.updateStatusById(noticeId, NoticeStatusEnums.PUBLISH);
    }

    /**
     * 根据通知id 下架
     *
     * @param noticeId 通知id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void underShelveNoticeId(Long noticeId) {
        SysNoticeEntity noticeEntity = sysNoticeDao.findById(noticeId);
        ThrowUtils.notNull(noticeEntity, "通知不存在!");
        if (!Objects.equals(noticeEntity.getNoticeStatus(), NoticeStatusEnums.PUBLISH)) {
            throw new BusinessException("通知状态不匹配!");
        }
        sysNoticeDao.updateStatusById(noticeId, NoticeStatusEnums.UNDER_SHELVE);
    }

    /**
     * 根据通知id 置顶
     *
     * @param noticeId 通知id
     * @param isTop    是否置顶
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateIsTopById(Long noticeId, NoticeTopEnums isTop) {
        SysNoticeEntity noticeEntity = sysNoticeDao.findById(noticeId);
        ThrowUtils.notNull(noticeEntity, "通知不存在!");
        sysNoticeDao.updateIsTopById(noticeId, isTop);
    }

    /**
     * 根据ID查询通知详情
     *
     * @param noticeId 通知详情ID
     * @return 通知详情信息
     */
    @Override
    public NoticeVo findById(Long noticeId) {
        NoticeVo result = new NoticeVo();
        SysNoticeResponse noticeResponse = sysNoticeConverter.toResponse(sysNoticeDao.findById(noticeId));
        // @formater:off
        Optional.ofNullable(noticeResponse).ifPresent(item -> {
            // 补充通知类型名称
            noticeResponse.setNoticeTypeName(sysNoticeTypeDao.findTypeName(noticeId));
            // 获取通知附件
            List<SysNoticeAttachmentEntity> attachmentList = sysNoticeAttachmentDao.findListByNoticeId(noticeId);
            result.setAttachments(sysNoticeAttachmentConverter.toResponse(attachmentList));
            BasicUserDetails user = SecurityUtils.getUser();
            // 记录已读
            boolean readExists = sysNoticeUserOperateDao.existsNoticeUserOperate(noticeId, user.getUserId(), NoticeOperateTypeEnums.READ);
            if (!readExists) {
                sysNoticeDao.updateReadNumById(noticeId);
                sysNoticeUserOperateDao.createNoticeUserOperate(noticeId, user.getUserId(), NoticeOperateTypeEnums.READ);
            }
            // 记录点击
            boolean clickExists = sysNoticeUserOperateDao.existsNoticeUserOperate(noticeId, user.getUserId(), NoticeOperateTypeEnums.CLICK);
            if (!clickExists) {
                sysNoticeDao.updateClickNumById(noticeId);
                sysNoticeUserOperateDao.createNoticeUserOperate(noticeId, user.getUserId(), NoticeOperateTypeEnums.CLICK);
            }
        });
        result.setNotice(noticeResponse);
        // @formater:on
        return result;
    }

    /**
     * 根据通知id 获取定时发布的数据
     *
     * @param noticeId 通知id
     * @return 定时发布数据
     */
    @Override
    public List<Long> listByReleaseTime(Long noticeId) {
        return sysNoticeDao.listByReleaseTime(noticeId);
    }

    /**
     * 分页查询通知详情
     *
     * @param query 通知详情查询请求参数
     * @return 通知详情分页信息
     */
    @Override
    public PageResponse<SysNoticeResponse> findPageList(SysNoticeQuery query) {
        Page<SysNoticeResponse> pageList = sysNoticeDao.findPageList(PageTool.getPage(query), query);
        return PageTool.getPageVo(pageList);
    }

}




