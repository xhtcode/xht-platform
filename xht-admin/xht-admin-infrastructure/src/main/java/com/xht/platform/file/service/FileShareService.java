package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.cache.repository.RedisRepository;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.SysFileShareDao;
import com.xht.platform.file.dao.SysFileShareReceiverDao;
import com.xht.platform.file.domain.access.FileAccessDecision;
import com.xht.platform.file.domain.access.FileAccessSubject;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.domain.form.FileShareCreateForm;
import com.xht.platform.file.domain.form.FileShareOpenForm;
import com.xht.platform.file.domain.query.SysFileShareQuery;
import com.xht.platform.file.domain.query.SysFileShareReceivedQuery;
import com.xht.platform.file.domain.response.*;
import com.xht.platform.file.domain.session.ShareTicket;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.entity.SysFileShareEntity;
import com.xht.platform.file.entity.SysFileShareReceiverEntity;
import com.xht.platform.file.enums.ShareModeEnum;
import com.xht.platform.file.permission.FileAccessEvaluator;
import com.xht.platform.file.policy.FilePolicyResolver;
import com.xht.platform.file.utils.AccessCodeUtils;
import com.xht.platform.system.dao.SysUserDao;
import com.xht.platform.system.dao.SysUserRoleDao;
import com.xht.platform.system.entity.SysUserEntity;
import com.xht.platform.system.entity.SysUserRoleEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 描述： 文件分享服务
 * <p>支持 LINK/USER 两种模式、三态权限、可选提取码、分享中心</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileShareService implements ISysFileShareService {

    private static final String FAIL_KEY_TOKEN = "file:share:code:fail:{}:{}";

    private final SysFileShareDao sysFileShareDao;

    private final SysFileShareReceiverDao sysFileShareReceiverDao;

    private final SysFileDao sysFileDao;

    private final SysUserDao sysUserDao;

    private final SysUserRoleDao sysUserRoleDao;

    private final ShareTicketService shareTicketService;

    private final FilePolicyResolver filePolicyResolver;

    private final FileAccessEvaluator fileAccessEvaluator;

    private final SysFileSettingService sysFileSettingService;

    private final RedisRepository redisRepository;

    /**
     * 创建分享
     *
     * @param form 创建请求
     * @return 分享信息
     */
    @Transactional(rollbackFor = Exception.class)
    public FileShareResponse create(FileShareCreateForm form) {
        AssertUtils.notNull(form, BusinessErrorCode.PARAM_ERROR);
        AssertUtils.hasText(form.getPreviewId(), BusinessErrorCode.PARAM_ERROR);
        AssertUtils.notNull(form.getShareMode(), BusinessErrorCode.PARAM_ERROR);
        AssertUtils.notNull(form.getPermission(), BusinessErrorCode.PARAM_ERROR);
        SysFileEntity file = sysFileDao.findById(form.getPreviewId());
        AssertUtils.notNull(file, BusinessErrorCode.FILE_NOT_EXIST);
        FileAccessSubject subject = FileAccessSubject.anonymous();
        FileAccessDecision decision = fileAccessEvaluator.decide(file, subject);
        if (!decision.isShare()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        FilePolicy policy = filePolicyResolver.resolve(file);
        if (!policy.isShareAllowed()) {
            throw new BusinessException(BusinessErrorCode.SHARE_PERMISSION_DENIED);
        }
        if (form.getPermission().isPreview() && !policy.isPreviewable()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "该文件不支持在线预览，请选择仅下载分享");
        }
        if (form.getPermission().isDownload() && (!policy.isDownloadAllowed() || !decision.isDownload())) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "该文件不允许下载");
        }

        int maxExpireDays = sysFileSettingService.getInt("share.max-expire-days", 30);
        int defaultExpireDays = policy.getShareExpireDays() == null
                ? sysFileSettingService.getInt("share.default-expire-days", 7)
                : policy.getShareExpireDays();
        int expireDays = form.getExpireDays() == null ? defaultExpireDays : form.getExpireDays();
        expireDays = Math.max(1, Math.min(expireDays, maxExpireDays));

        boolean requireCode = form.getRequireCode() != null
                ? form.getRequireCode()
                : sysFileSettingService.getBoolean(
                ShareModeEnum.LINK == form.getShareMode()
                ? "share.link.require-code-default" : "share.user.require-code-default",
                ShareModeEnum.LINK == form.getShareMode());
        if (!requireCode && ShareModeEnum.LINK == form.getShareMode()) {
            int noCodeMax = sysFileSettingService.getInt("share.link.no-code-max-expire-days", 7);
            expireDays = Math.min(expireDays, Math.max(1, noCodeMax));
        }

        String plainCode = null;
        String codeHash = null;
        if (requireCode) {
            if (StringUtils.hasText(form.getAccessCode())) {
                String error = AccessCodeUtils.validate(form.getAccessCode());
                if (error != null) {
                    throw new BusinessException(BusinessErrorCode.PARAM_ERROR, error);
                }
                plainCode = AccessCodeUtils.normalize(form.getAccessCode());
            } else {
                int randomLength = sysFileSettingService.getInt("share.code.random-length", 6);
                plainCode = AccessCodeUtils.random(randomLength);
            }
            codeHash = AccessCodeUtils.encode(plainCode);
        }

        int maxAccessCount = form.getMaxAccessCount() == null
                ? (policy.getShareMaxAccessCount() == null ? 0 : policy.getShareMaxAccessCount())
                : form.getMaxAccessCount();
        if (ShareModeEnum.USER == form.getShareMode()) {
            maxAccessCount = 0;
        } else {
            maxAccessCount = Math.max(0, maxAccessCount);
        }

        SysFileShareEntity share = new SysFileShareEntity();
        share.setShareToken("sh_" + IdUtils.simpleUUID());
        share.setShareMode(form.getShareMode());
        share.setFileId(file.getId());
        share.setPermission(form.getPermission());
        share.setRequireCode(requireCode ? 1 : 0);
        share.setCodeVersion(0);
        share.setAccessCodeHash(codeHash);
        share.setExpireTime(LocalDateTime.now().plusDays(expireDays));
        share.setMaxAccessCount(maxAccessCount);
        share.setAccessCount(0);
        share.setStatus(1);
        share.setNoticeSent(0);
        share.setReceiverCount(0);
        sysFileShareDao.save(share);

        int receiverCount = 0;
        if (ShareModeEnum.USER == form.getShareMode()) {
            receiverCount = createReceivers(share, form);
            share.setReceiverCount(receiverCount);
            sysFileShareDao.updateById(share);
        }

        FileShareResponse response = new FileShareResponse();
        response.setShareId(share.getId());
        response.setShareMode(share.getShareMode());
        response.setPermission(share.getPermission());
        response.setRequireCode(requireCode);
        response.setExpireAt(share.getExpireTime());
        response.setMaxAccessCount(maxAccessCount);
        response.setAccessCount(0);
        response.setReceiverCount(receiverCount);
        response.setStatus(share.getStatus());
        response.setFileName(file.getFileOriginalName());
        response.setFileSizeInfo(file.getFileSizeInfo());
        response.setFileSuffix(file.getFileSuffix());
        response.setCreateTime(share.getCreateTime());
        if (ShareModeEnum.LINK == share.getShareMode()) {
            response.setShareToken(share.getShareToken());
            response.setShareUrl("/s/" + share.getShareToken());
            response.setAccessCode(plainCode);
        }
        return response;
    }

    /**
     * 创建接收人
     *
     * @param share 分享
     * @param form  请求
     * @return 接收人数
     */
    private int createReceivers(SysFileShareEntity share, FileShareCreateForm form) {
        List<Long> ids = form.getReceiverIds();
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "请选择接收人");
        }
        int maxCount = sysFileSettingService.getInt("share.receiver.max-count", 500);
        String type = StringUtils.hasText(form.getReceiverType()) ? form.getReceiverType().toUpperCase() : "USER";
        Map<Long, SysUserEntity> users = new LinkedHashMap<>();
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            if ("DEPT".equals(type)) {
                for (SysUserEntity user : sysUserDao.findList(SysUserEntity::getDeptId, id)) {
                    users.putIfAbsent(user.getId(), user);
                }
            } else if ("ROLE".equals(type)) {
                for (SysUserRoleEntity relation : sysUserRoleDao.findList(SysUserRoleEntity::getRoleId, id)) {
                    SysUserEntity user = sysUserDao.findById(relation.getUserId());
                    if (user != null) {
                        users.putIfAbsent(user.getId(), user);
                    }
                }
            } else {
                SysUserEntity user = sysUserDao.findById(id);
                if (user == null) {
                    throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "接收人不存在：" + id);
                }
                users.putIfAbsent(user.getId(), user);
            }
        }
        if (users.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "未匹配到有效接收人");
        }
        if (users.size() > maxCount) {
            throw new BusinessException(BusinessErrorCode.SHARE_RECEIVER_LIMIT);
        }
        if (share.getCreatorId() != null && users.containsKey(share.getCreatorId())) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "不能分享给自己");
        }

        List<SysFileShareReceiverEntity> receivers = new ArrayList<>(users.size());
        for (SysUserEntity user : users.values()) {
            SysFileShareReceiverEntity receiver = new SysFileShareReceiverEntity();
            receiver.setShareId(share.getId());
            receiver.setReceiverId(user.getId());
            receiver.setReceiverName(StringUtils.hasText(user.getNickName()) ? user.getNickName() : user.getUserName());
            receiver.setReceiverSource(type);
            receiver.setReadStatus(0);
            receiver.setDownloadCount(0);
            receiver.setHidden(0);
            receivers.add(receiver);
        }
        sysFileShareReceiverDao.saveAll(receivers);
        share.setTargetType(type);
        share.setTargetName("指定用户 " + receivers.size() + " 人");
        return receivers.size();
    }

    /**
     * 分享元信息（链接）
     *
     * @param token 分享定位串
     * @return 元信息
     */
    public FileShareMetaResponse metaByToken(String token) {
        SysFileShareEntity share = getValidByToken(token);
        return buildMeta(share);
    }

    /**
     * 分享元信息（指定用户）
     *
     * @param shareId 分享ID
     * @return 元信息
     */
    public FileShareMetaResponse metaByShareId(Long shareId) {
        SysFileShareEntity share = getValidById(shareId);
        requireReceiver(shareId, currentUserId());
        return buildMeta(share);
    }

    /**
     * 打开链接分享
     *
     * @param token   分享定位串
     * @param form    打开请求
     * @param request 请求
     * @return 票据
     */
    public FileShareOpenResponse openByToken(String token, FileShareOpenForm form, HttpServletRequest request) {
        SysFileShareEntity share = getValidByToken(token);
        verifyCode(share, form == null ? null : form.getAccessCode(),
                "token:" + token + ":" + clientIp(request));
        if (share.getMaxAccessCount() != null && share.getMaxAccessCount() > 0
                && share.getAccessCount() != null && share.getAccessCount() >= share.getMaxAccessCount()) {
            throw new BusinessException(BusinessErrorCode.SHARE_NOT_EXIST_OR_EXPIRED);
        }
        ShareTicket ticket = shareTicketService.create(share.getId(), share.getFileId(), share.getShareMode(),
                share.getPermission(), null, share.getCodeVersion(), share.getExpireTime());
        sysFileShareDao.incrementAccessCount(share.getId());
        sysFileShareDao.updateLastAccess(share.getId(), clientIp(request));
        return buildOpenResponse(ticket, share);
    }

    /**
     * 打开指定用户分享
     *
     * @param shareId 分享ID
     * @param form    打开请求
     * @return 票据
     */
    public FileShareOpenResponse openByShareId(Long shareId, FileShareOpenForm form) {
        SysFileShareEntity share = getValidById(shareId);
        SysFileShareReceiverEntity receiver = requireReceiver(shareId, currentUserId());
        verifyCode(share, form == null ? null : form.getAccessCode(),
                "user:" + shareId + ":" + currentUserId());
        if (receiver.getReadStatus() == null || receiver.getReadStatus() == 0) {
            receiver.setReadStatus(1);
            receiver.setReadTime(LocalDateTime.now());
        }
        if (receiver.getFirstViewTime() == null) {
            receiver.setFirstViewTime(LocalDateTime.now());
        }
        sysFileShareReceiverDao.updateById(receiver);
        ShareTicket ticket = shareTicketService.create(share.getId(), share.getFileId(), share.getShareMode(),
                share.getPermission(), receiver.getId(), share.getCodeVersion(), share.getExpireTime());
        sysFileShareDao.incrementAccessCount(share.getId());
        return buildOpenResponse(ticket, share);
    }

    /**
     * 我创建的分享分页
     *
     * @param query 查询条件
     * @return 分页
     */
    public PageResponse<FileShareResponse> pageCreated(SysFileShareQuery query) {
        query.setCreatorId(currentUserId());
        Page<SysFileShareEntity> page = sysFileShareDao.findPageByCreator(PageTool.getPage(query), query);
        Map<Long, SysFileEntity> files = loadFiles(page.getRecords().stream()
                .map(SysFileShareEntity::getFileId).toList());
        List<FileShareResponse> list = page.getRecords().stream()
                .map(share -> buildShareResponse(share, files.get(share.getFileId())))
                .toList();
        return toPageResponse(page, list);
    }

    /**
     * 分享给我的分页
     *
     * @param query 查询条件
     * @return 分页
     */
    public PageResponse<FileShareReceivedResponse> pageReceived(SysFileShareReceivedQuery query) {
        query.setReceiverId(currentUserId());
        Page<SysFileShareReceiverEntity> page = sysFileShareReceiverDao.findReceivedPage(PageTool.getPage(query), query);
        Map<Long, SysFileShareEntity> shares = loadShares(page.getRecords().stream()
                .map(SysFileShareReceiverEntity::getShareId).toList());
        Map<Long, SysFileEntity> files = loadFiles(shares.values().stream()
                .map(SysFileShareEntity::getFileId).toList());
        List<FileShareReceivedResponse> list = page.getRecords().stream()
                .map(receiver -> buildReceivedResponse(receiver, shares.get(receiver.getShareId()),
                        files.get(shares.get(receiver.getShareId()) == null ? null : shares.get(receiver.getShareId()).getFileId())))
                .filter(Objects::nonNull)
                .toList();
        return toPageResponse(page, list);
    }

    /**
     * 分享接收人列表
     *
     * @param shareId 分享ID
     * @return 接收人
     */
    public List<FileShareReceiverResponse> receivers(Long shareId) {
        SysFileShareEntity share = getValidById(shareId);
        if (!Objects.equals(share.getCreatorId(), currentUserId()) && !SecurityUtils.isAdmin()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        return sysFileShareReceiverDao.findByShareId(shareId).stream().map(receiver -> {
            FileShareReceiverResponse response = new FileShareReceiverResponse();
            response.setReceiverId(receiver.getId());
            response.setUserId(receiver.getReceiverId());
            response.setUserName(receiver.getReceiverName());
            response.setReadStatus(receiver.getReadStatus());
            response.setReadTime(receiver.getReadTime());
            response.setFirstViewTime(receiver.getFirstViewTime());
            response.setDownloadCount(receiver.getDownloadCount());
            return response;
        }).toList();
    }

    /**
     * 撤销分享
     *
     * @param shareId 分享ID
     */
    public void revoke(Long shareId) {
        SysFileShareEntity share = getValidById(shareId);
        if (!Objects.equals(share.getCreatorId(), currentUserId()) && !SecurityUtils.isAdmin()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        share.setStatus(0);
        sysFileShareDao.updateById(share);
    }

    /**
     * 重置提取码
     *
     * @param shareId 分享ID
     * @param code    自定义提取码，空则随机
     * @return 新提取码
     */
    public String resetCode(Long shareId, String code) {
        SysFileShareEntity share = getValidById(shareId);
        if (!Objects.equals(share.getCreatorId(), currentUserId()) && !SecurityUtils.isAdmin()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        if (!Objects.equals(share.getRequireCode(), 1)) {
            throw new BusinessException(BusinessErrorCode.PARAM_ERROR, "该分享未启用提取码");
        }
        String plainCode;
        if (StringUtils.hasText(code)) {
            String error = AccessCodeUtils.validate(code);
            if (error != null) {
                throw new BusinessException(BusinessErrorCode.PARAM_ERROR, error);
            }
            plainCode = AccessCodeUtils.normalize(code);
        } else {
            plainCode = AccessCodeUtils.random(sysFileSettingService.getInt("share.code.random-length", 6));
        }
        share.setAccessCodeHash(AccessCodeUtils.encode(plainCode));
        share.setCodeVersion((share.getCodeVersion() == null ? 0 : share.getCodeVersion()) + 1);
        sysFileShareDao.updateById(share);
        return plainCode;
    }

    /**
     * 未读数量
     *
     * @return 未读数
     */
    public long unreadCount() {
        return sysFileShareReceiverDao.countUnread(currentUserId());
    }

    /**
     * 标记已读
     *
     * @param shareId 分享ID
     */
    public void markRead(Long shareId) {
        sysFileShareReceiverDao.markRead(shareId, currentUserId());
    }

    /**
     * 从列表移除
     *
     * @param shareId 分享ID
     */
    public void hide(Long shareId) {
        SysFileShareReceiverEntity receiver = requireReceiver(shareId, currentUserId());
        receiver.setHidden(1);
        sysFileShareReceiverDao.updateById(receiver);
    }

    /**
     * 根据 token 获取有效分享
     */
    private SysFileShareEntity getValidByToken(String token) {
        AssertUtils.hasText(token, BusinessErrorCode.PARAM_ERROR);
        SysFileShareEntity share = sysFileShareDao.findByToken(token)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.SHARE_NOT_EXIST_OR_EXPIRED));
        return assertValid(share);
    }

    /**
     * 根据ID获取有效分享
     */
    private SysFileShareEntity getValidById(Long shareId) {
        AssertUtils.notNull(shareId, BusinessErrorCode.PARAM_ERROR);
        SysFileShareEntity share = sysFileShareDao.findById(shareId);
        return assertValid(share);
    }

    /**
     * 校验分享有效
     */
    private SysFileShareEntity assertValid(SysFileShareEntity share) {
        if (share == null || !Objects.equals(share.getStatus(), 1)
                || share.getExpireTime() == null || share.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException(BusinessErrorCode.SHARE_NOT_EXIST_OR_EXPIRED);
        }
        return share;
    }

    /**
     * 校验当前用户是接收人
     */
    private SysFileShareReceiverEntity requireReceiver(Long shareId, Long userId) {
        return sysFileShareReceiverDao.findByShareAndReceiver(shareId, userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.SHARE_RECEIVER_NOT_FOUND));
    }

    /**
     * 校验提取码
     */
    private void verifyCode(SysFileShareEntity share, String inputCode, String failKeySuffix) {
        if (!Objects.equals(share.getRequireCode(), 1)) {
            return;
        }
        int limit = sysFileSettingService.getInt("share.code.fail-limit", 5);
        int lockMinutes = sysFileSettingService.getInt("share.code.lock-minutes", 10);
        String key = com.xht.framework.cache.utils.Keys.createKeyTemplate(FAIL_KEY_TOKEN, failKeySuffix, "count");
        Object countObj = redisRepository.get(key);
        int count = 0;
        if (countObj instanceof Number number) {
            count = number.intValue();
        } else if (countObj instanceof String text) {
            try {
                count = Integer.parseInt(text);
            } catch (NumberFormatException ignored) {
            }
        }
        if (count >= limit) {
            throw new BusinessException(BusinessErrorCode.SHARE_CODE_LOCKED);
        }
        if (!AccessCodeUtils.matches(inputCode, share.getAccessCodeHash())) {
            redisRepository.set(key, count + 1, lockMinutes, TimeUnit.MINUTES);
            throw new BusinessException(BusinessErrorCode.SHARE_CODE_ERROR);
        }
        redisRepository.delete(key);
    }

    /**
     * 构建分享元信息
     */
    private FileShareMetaResponse buildMeta(SysFileShareEntity share) {
        SysFileEntity file = sysFileDao.findById(share.getFileId());
        AssertUtils.notNull(file, BusinessErrorCode.FILE_NOT_EXIST);
        FileShareMetaResponse response = new FileShareMetaResponse();
        response.setShareId(share.getId());
        response.setShareMode(share.getShareMode());
        response.setFileName(file.getFileOriginalName());
        response.setFileSizeInfo(file.getFileSizeInfo());
        response.setFileSuffix(file.getFileSuffix());
        response.setCreatorName(share.getCreatorName());
        response.setPermission(share.getPermission());
        response.setRequireCode(Objects.equals(share.getRequireCode(), 1));
        response.setExpireAt(share.getExpireTime());
        response.setStatus(share.getStatus());
        return response;
    }

    /**
     * 构建打开响应
     */
    private FileShareOpenResponse buildOpenResponse(ShareTicket ticket, SysFileShareEntity share) {
        FileShareOpenResponse response = new FileShareOpenResponse();
        response.setShareTicket(ticket.getTicket());
        response.setShareId(share.getId());
        response.setPermission(share.getPermission());
        response.setRequireCode(Objects.equals(share.getRequireCode(), 1));
        response.setExpireAt(ticket.getExpireAt());
        return response;
    }

    /**
     * 构建分享列表响应
     */
    private FileShareResponse buildShareResponse(SysFileShareEntity share, SysFileEntity file) {
        FileShareResponse response = new FileShareResponse();
        response.setShareId(share.getId());
        response.setShareMode(share.getShareMode());
        response.setPermission(share.getPermission());
        response.setRequireCode(Objects.equals(share.getRequireCode(), 1));
        response.setExpireAt(share.getExpireTime());
        response.setMaxAccessCount(share.getMaxAccessCount());
        response.setAccessCount(share.getAccessCount());
        response.setReceiverCount(share.getReceiverCount());
        response.setStatus(share.getStatus());
        response.setCreateTime(share.getCreateTime());
        if (file != null) {
            response.setFileName(file.getFileOriginalName());
            response.setFileSizeInfo(file.getFileSizeInfo());
            response.setFileSuffix(file.getFileSuffix());
        }
        if (ShareModeEnum.LINK == share.getShareMode()) {
            response.setShareToken(share.getShareToken());
            response.setShareUrl("/s/" + share.getShareToken());
        }
        return response;
    }

    /**
     * 构建接收列表响应
     */
    private FileShareReceivedResponse buildReceivedResponse(SysFileShareReceiverEntity receiver,
                                                            SysFileShareEntity share, SysFileEntity file) {
        if (share == null || file == null) {
            return null;
        }
        FileShareReceivedResponse response = new FileShareReceivedResponse();
        response.setReceiverId(receiver.getId());
        response.setShareId(share.getId());
        response.setFileName(file.getFileOriginalName());
        response.setFileSizeInfo(file.getFileSizeInfo());
        response.setFileSuffix(file.getFileSuffix());
        response.setCreatorName(share.getCreatorName());
        response.setPermission(share.getPermission());
        response.setReadStatus(receiver.getReadStatus());
        response.setFirstViewTime(receiver.getFirstViewTime());
        response.setExpireAt(share.getExpireTime());
        response.setStatus(share.getStatus());
        return response;
    }

    /**
     * 批量加载文件
     */
    private Map<Long, SysFileEntity> loadFiles(Collection<Long> fileIds) {
        List<Long> ids = fileIds.stream().filter(Objects::nonNull).distinct().toList();
        if (ids.isEmpty()) {
            return Map.of();
        }
        Map<Long, SysFileEntity> map = new LinkedHashMap<>();
        for (SysFileEntity file : sysFileDao.findList(SysFileEntity::getId, ids)) {
            map.put(file.getId(), file);
        }
        return map;
    }

    /**
     * 批量加载分享
     */
    private Map<Long, SysFileShareEntity> loadShares(Collection<Long> shareIds) {
        List<Long> ids = shareIds.stream().filter(Objects::nonNull).distinct().toList();
        if (ids.isEmpty()) {
            return Map.of();
        }
        Map<Long, SysFileShareEntity> map = new LinkedHashMap<>();
        for (SysFileShareEntity share : sysFileShareDao.findList(SysFileShareEntity::getId, ids)) {
            map.put(share.getId(), share);
        }
        return map;
    }

    /**
     * 构建分页响应
     */
    private <T> PageResponse<T> toPageResponse(Page<?> page, List<T> list) {
        PageResponse<T> response = new PageResponse<>();
        response.setCurrent(page.getCurrent());
        response.setSize(page.getSize());
        response.setPages(page.getPages());
        response.setTotal(page.getTotal());
        response.setRecords(list);
        return response;
    }

    private Long currentUserId() {
        return SecurityUtils.getUserId();
    }

    private String clientIp(HttpServletRequest request) {
        return request == null ? "" : request.getRemoteAddr();
    }

}
