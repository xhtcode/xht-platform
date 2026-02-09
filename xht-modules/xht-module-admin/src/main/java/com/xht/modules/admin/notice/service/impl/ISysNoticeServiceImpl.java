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
import com.xht.modules.admin.notice.converter.SysNoticePermissionConverter;
import com.xht.modules.admin.notice.dao.*;
import com.xht.modules.admin.notice.domain.form.SysNoticeForm;
import com.xht.modules.admin.notice.domain.form.SysNoticePermissionForm;
import com.xht.modules.admin.notice.domain.query.SysNoticeQuery;
import com.xht.modules.admin.notice.domain.response.SysNoticeResponse;
import com.xht.modules.admin.notice.domain.vo.NoticeVo;
import com.xht.modules.admin.notice.entity.SysNoticeAttachmentEntity;
import com.xht.modules.admin.notice.entity.SysNoticeEntity;
import com.xht.modules.admin.notice.entity.SysNoticePermissionEntity;
import com.xht.modules.admin.notice.entity.SysNoticeTypeEntity;
import com.xht.modules.admin.notice.enums.*;
import com.xht.modules.admin.notice.service.ISysNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    private final SysNoticePermissionDao sysNoticePermissionDao;

    private final SysNoticeUserOperateDao sysNoticeUserOperateDao;

    private final SysNoticeConverter sysNoticeConverter;

    private final SysNoticeAttachmentConverter sysNoticeAttachmentConverter;

    private final SysNoticePermissionConverter sysNoticePermissionConverter;

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

        // 处理通知权限
        List<SysNoticePermissionEntity> permissionEntities = new ArrayList<>();
        if (Objects.equals(NoticeAllVisibleEnums.NO, noticeEntity.getNoticeAllVisible())) {
            List<SysNoticePermissionForm> permissionList = form.getPermissionList();
            ThrowUtils.throwIf(CollectionUtils.isEmpty(permissionList), "权限列表不能为空");
            permissionEntities = sysNoticePermissionConverter.toEntity(permissionList, noticeId);
        }

        // 处理定时发布时间
        handleTimedPublish(noticeEntity, form);

        // 处理跳转URL
        handleJumpUrlValidation(noticeEntity);

        // 3、保存通知详情
        sysNoticeDao.save(noticeEntity);

        // 4、保存通知附件
        if (!CollectionUtils.isEmpty(attachmentEntityList)) {
            sysNoticeAttachmentDao.saveAll(attachmentEntityList);
        }

        // 5、保存通知权限
        if (!CollectionUtils.isEmpty(permissionEntities)) {
            sysNoticePermissionDao.saveAll(permissionEntities);
        }

    }

    private void handleTimedPublish(SysNoticeEntity noticeEntity, SysNoticeForm form) {
        if (Objects.equals(NoticeTimedPublishEnums.PUBLISH, noticeEntity.getNoticeTimedPublish())) {
            ThrowUtils.notNull(form.getNoticePublishTime(), "定时发布时间不能为空");
            noticeEntity.setNoticePublishTime(form.getNoticePublishTime());
        } else {
            noticeEntity.setNoticePublishTime(null);
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
        // 1、校验上传表单数据
        ThrowUtils.notNull(form, "通知详情表单参数不能为空");
        ThrowUtils.notNull(form.getId(), "通知ID不能为空");

        // 检查通知是否存在
        Boolean existingNotice = sysNoticeDao.exists(SysNoticeEntity::getId, form.getId());
        ThrowUtils.throwIf(!existingNotice, "通知不存在!");

        // 校验通知类型是否存在
        if (form.getNoticeTypeId() != null) {
            Boolean exists = sysNoticeTypeDao.exists(SysNoticeTypeEntity::getId, form.getNoticeTypeId());
            ThrowUtils.throwIf(!exists, "通知类型不存在!");
        }

        // 封装通知实体
        SysNoticeEntity noticeEntity = sysNoticeConverter.toEntity(form);

        // 处理通知附件
        List<SysNoticeAttachmentEntity> attachmentEntityList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(form.getAttachmentList())) {
            attachmentEntityList = sysNoticeAttachmentConverter.toEntity(form.getAttachmentList(), form.getId());
        }

        // 处理通知权限
        List<SysNoticePermissionEntity> permissionEntities = new ArrayList<>();
        if (Objects.equals(NoticeAllVisibleEnums.NO, noticeEntity.getNoticeAllVisible())) {
            List<SysNoticePermissionForm> permissionList = form.getPermissionList();
            ThrowUtils.throwIf(CollectionUtils.isEmpty(permissionList), "权限列表不能为空");
            permissionEntities = sysNoticePermissionConverter.toEntity(permissionList, form.getId());
        }

        // 处理定时发布时间
        handleTimedPublish(noticeEntity, form);

        // 处理跳转URL
        handleJumpUrlValidation(noticeEntity);

        // 3、更新通知详情
        sysNoticeDao.updateById(noticeEntity);

        // 4、删除旧附件并保存新附件
        sysNoticeAttachmentDao.removeByNoticeId(form.getId());
        if (!CollectionUtils.isEmpty(attachmentEntityList)) {
            sysNoticeAttachmentDao.saveAll(attachmentEntityList);
        }

        // 5、删除旧权限并保存新权限
        sysNoticePermissionDao.removeByNoticeId(form.getId());
        if (!CollectionUtils.isEmpty(permissionEntities)) {
            sysNoticePermissionDao.saveAll(permissionEntities);
        }

    }


    /**
     * 根据通知id 修改状态
     *
     * @param noticeId     通知id
     * @param noticeStatus 通知状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusById(Long noticeId, NoticeStatusEnums noticeStatus) {
        sysNoticeDao.updateStatusById(noticeId, noticeStatus);
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
        BasicUserDetails user = SecurityUtils.getUser();
        Boolean admin = SecurityUtils.isAdmin();
        Long userId = user.getUserId();
        Long deptId = user.getDeptId();
        // @formater:off
        Optional.ofNullable(noticeResponse).ifPresent(item -> {
            // 查询权限
            if (Objects.equals(item.getNoticeAllVisible(), NoticeAllVisibleEnums.NO) && !admin ) {
                Set<String> roleCodes = user.getRoleCodes();
                boolean hashPermission = sysNoticePermissionDao.hashPermission(noticeId, userId, deptId, roleCodes);
                if (!hashPermission) {
                    throw new BusinessException("暂无权限查询通知详情!");
                }
            }
            // 补充通知类型名称
            noticeResponse.setNoticeTypeName(sysNoticeTypeDao.findTypeName(noticeId));
            // 获取通知附件
            List<SysNoticeAttachmentEntity> attachmentList = sysNoticeAttachmentDao.findListByNoticeId(noticeId);
            result.setAttachments(sysNoticeAttachmentConverter.toResponse(attachmentList));
            // 获取通知权限
            List<SysNoticePermissionEntity> permissionList = sysNoticePermissionDao.findListByNoticeId(noticeId);
            result.setPermissions(sysNoticePermissionConverter.toResponse(permissionList));
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




