package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.file.XhtFileUtils;
import com.xht.platform.common.file.bo.ChunkMergeBO;
import com.xht.platform.common.file.bo.ChunkUploadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.dao.SysFileShareReceiverDao;
import com.xht.platform.file.domain.access.FileAccessDecision;
import com.xht.platform.file.domain.access.FileAccessSubject;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.domain.form.FilePreviewInitForm;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.*;
import com.xht.platform.file.domain.session.FilePreviewSession;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.entity.SysFileShareReceiverEntity;
import com.xht.platform.file.enums.*;
import com.xht.platform.file.permission.FileAccessEvaluator;
import com.xht.platform.file.policy.FilePolicyResolver;
import com.xht.platform.file.preview.FilePreview;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 描述： 系统文件服务实现
 * <p>文件元数据 CRUD、预览会话初始化、状态查询、内容输出与下载</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl implements ISysFileService {

    /**
     * 登录用户会话滑动 TTL：30 分钟
     */
    private static final long USER_SESSION_TTL_SECONDS = 1800L;
    /**
     * 登录用户会话绝对上限：2 小时
     */
    private static final long USER_SESSION_ABSOLUTE_SECONDS = 7200L;
    /**
     * 匿名会话滑动 TTL：10 分钟
     */
    private static final long ANON_SESSION_TTL_SECONDS = 600L;
    /**
     * 匿名会话绝对上限：30 分钟
     */
    private static final long ANON_SESSION_ABSOLUTE_SECONDS = 1800L;

    private final SysFileDao sysFileDao;

    private final SysFileShareReceiverDao sysFileShareReceiverDao;

    private final SysFileConverter sysFileConverter;

    private final FileStorageDao fileStorageDao;

    private final List<FilePreview> filePreviewList;

    private final FilePolicyResolver filePolicyResolver;

    private final FileAccessEvaluator fileAccessEvaluator;

    private final PreviewSessionService previewSessionService;

    private final ISysFileShareService fileShareService;

    /**
     * 根据文件ID删除文件记录（逻辑删除），并同步删除物理文件
     *
     * @param fileId 文件ID
     */
    @Override
    public void removeById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        // 已关联业务的文件不允许删除
        AssertUtils.isTrue(!Objects.equals(FileStatusEnum.ASSOCIATED, sysFileEntity.getFileStatus()), BusinessErrorCode.FILE_ASSOCIATED, "文件已关联业务，无法删除");
        sysFileDao.updateFileStatusById(fileId, FileStatusEnum.DELETED);
    }

    /**
     * 根据文件ID查询文件详情
     *
     * @param fileId 文件ID
     * @return 文件详情
     */
    @Override
    public SysFileResponse findById(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity sysFileEntity = sysFileDao.findById(fileId);
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 分页查询文件列表
     *
     * @param query 分页查询参数
     * @return 分页结果
     */
    @Override
    public PageResponse<SysFileResponse> findPageList(SysFileQuery query) {
        Page<SysFileEntity> page = sysFileDao.findPageList(PageTool.getPage(query), query);
        return sysFileConverter.toResponse(page);
    }

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        SysFileEntity sysFileEntity = fileStorageDao.uploadMultipartFile(uploadBo);
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 查询已上传分片序号列表（断点续传）
     *
     * @param uploadId 分片上传标识
     * @return 已上传分片序号列表（升序）
     */
    @Override
    public List<Integer> findUploadedChunkIndexes(String uploadId) {
        return fileStorageDao.findUploadedChunkIndexes(uploadId);
    }

    /**
     * 分片上传（单分片）
     *
     * @param chunkUploadBo 分片上传参数
     */
    @Override
    public void uploadChunk(ChunkUploadBO<MultipartFile> chunkUploadBo) {
        fileStorageDao.uploadChunk(chunkUploadBo);
    }

    /**
     * 合并文件分片：校验分片完整性，合并为完整文件并生成文件记录
     *
     * @param chunkMergeBo 分片合并参数
     * @return 文件信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFileResponse mergeChunks(ChunkMergeBO chunkMergeBo) {
        SysFileEntity sysFileEntity = fileStorageDao.mergeChunks(chunkMergeBo);
        sysFileDao.updateById(sysFileEntity);
        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 初始化文件预览信息
     *
     * @param form     初始化请求
     * @param request  请求
     * @param response 响应
     * @return 文件预览初始化信息
     */
    @Override
    public SysFilePreviewResponse initPreviewInfo(FilePreviewInitForm form, HttpServletRequest request, HttpServletResponse response) {
        SysFilePreviewResponse previewResponse = new SysFilePreviewResponse();
        SysFileEntity sysFileEntity = sysFileDao.findByFileStorageKey(form.getFileStorageKey());
        AssertUtils.notNull(sysFileEntity, BusinessErrorCode.FILE_NOT_EXIST);
        previewResponse.setFileName(sysFileEntity.getFileStorageName());
        previewResponse.setFileOriginalName(sysFileEntity.getFileOriginalName());
        previewResponse.setFileSize(sysFileEntity.getFileSize());
        previewResponse.setFileSizeInfo(sysFileEntity.getFileSizeInfo());
        previewResponse.setFileSuffix(sysFileEntity.getFileSuffix());
        previewResponse.setFileType(sysFileEntity.getFileType());
        SubjectContext subjectContext = resolveSubject();
        FileAccessDecision decision = fileAccessEvaluator.decide(sysFileEntity, subjectContext.subject());
        FilePolicy policy = filePolicyResolver.resolve(sysFileEntity);
        boolean downloadOnly = isDownloadOnly(subjectContext.subject())
                || (!decision.isView() && decision.isDownload());
        FileInitStatusEnum initStatus = resolveInitStatus(policy, downloadOnly);
        long ttl = ttlSeconds(subjectContext.subject());
        long absolute = absoluteSeconds(subjectContext.subject());
        FilePreviewSession session = FilePreviewSession.builder()
                .previewId(previewSessionService.newPreviewId())
                .subjectType(subjectContext.subject().getType())
                .subjectId(subjectContext.subjectId())
                .userId(subjectContext.subject().getUserId())
                .userName(subjectContext.subject().getUserName())
                .admin(subjectContext.subject().isAdmin())
                .shareId(subjectContext.subject().getShareId())
                .receiverId(subjectContext.subject().getReceiverId())
                .sharePermission(subjectContext.subject().getSharePermission())
                .fileId(sysFileEntity.getId())
                .previewMode(policy.getPreviewType())
                .ttlSeconds(ttl)
                .absoluteExpireAt(LocalDateTime.now().plusSeconds(absolute))
                .build();
        previewSessionService.create(session);
        previewResponse.setPreviewId(session.getPreviewId());
        previewResponse.setInitStatus(initStatus);
        previewResponse.setPreviewMode(policy.getPreviewType());
        previewResponse.setCapabilities(buildCapabilities(decision, initStatus));
        return previewResponse;
    }

    /**
     * 查询预览状态
     *
     * @param previewId 预览ID
     * @return 预览状态
     */
    @Override
    public SysFilePreviewStatusResponse previewStatus(String previewId) {
        FilePreviewSession session = previewSessionService.getRequired(previewId);
        previewSessionService.refresh(session);
        fileShareService.validateSession(session);
        SysFileEntity file = findFile(session.getFileId());
        FileAccessSubject subject = toSubject(session);
        FileAccessDecision decision = fileAccessEvaluator.decide(file, subject);
        if (!decision.hasAny()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        FilePolicy policy = filePolicyResolver.resolve(file);
        boolean downloadOnly = isDownloadOnly(subject) || (!decision.isView() && decision.isDownload());
        FileInitStatusEnum initStatus = resolveInitStatus(policy, downloadOnly);
        return buildStatusResponse(session, policy, decision, initStatus);
    }

    /**
     * 文件下载
     *
     * @param response  响应
     * @param previewId 预览ID
     */
    @Override
    public void downloadFile(HttpServletResponse response, String previewId) {
        FilePreviewSession session = previewSessionService.getRequired(previewId);
        previewSessionService.refresh(session);
        fileShareService.validateSession(session);
        SysFileEntity file = findFile(session.getFileId());
        FileAccessSubject subject = toSubject(session);
        fileAccessEvaluator.check(file, subject, FileActionEnum.DOWNLOAD);
        incrementReceiverDownload(session);
        try (InputStream download = fileStorageDao.download(file)) {
            String downloadName = StringUtils.hasText(file.getFileOriginalName())
                    ? file.getFileOriginalName() : file.getFileStorageName();
            XhtFileUtils.writeFile(response, downloadName, file.getFileType(), download);
        } catch (Exception e) {
            throw new BusinessException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 文件预览
     *
     * @param request   请求
     * @param response  响应
     * @param previewId 预览ID
     */
    @Override
    public void filePreview(HttpServletRequest request, HttpServletResponse response, String previewId) {
        FilePreviewSession session = previewSessionService.getRequired(previewId);
        previewSessionService.refresh(session);
        fileShareService.validateSession(session);
        SysFileEntity file = findFile(session.getFileId());
        FileAccessSubject subject = toSubject(session);
        if (isDownloadOnly(subject)) {
            throw new BusinessException(BusinessErrorCode.SHARE_PERMISSION_DENIED);
        }
        fileAccessEvaluator.check(file, subject, FileActionEnum.PREVIEW);

        FilePolicy policy = filePolicyResolver.resolve(file);
        if (!policy.isPreviewable()) {
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_NOT_SUPPORT);
        }
        for (FilePreview filePreview : filePreviewList) {
            if (filePreview.supports(policy.getPreviewType())) {
                filePreview.filePreview(request, response, file);
                return;
            }
        }
        throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_NOT_SUPPORT);
    }

    /**
     * 累加接收人下载次数
     *
     * @param session 会话
     */
    private void incrementReceiverDownload(FilePreviewSession session) {
        if (session.getReceiverId() == null) {
            return;
        }
        SysFileShareReceiverEntity receiver = sysFileShareReceiverDao.findById(session.getReceiverId());
        if (receiver != null) {
            receiver.setDownloadCount((receiver.getDownloadCount() == null ? 0 : receiver.getDownloadCount()) + 1);
            sysFileShareReceiverDao.updateById(receiver);
        }
    }

    /**
     * 按ID查询文件
     *
     * @param fileId 文件ID
     * @return 文件实体
     */
    private SysFileEntity findFile(Long fileId) {
        AssertUtils.notNull(fileId, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity file = sysFileDao.findById(fileId);
        AssertUtils.notNull(file, BusinessErrorCode.FILE_NOT_EXIST);
        return file;
    }

    /**
     * 解析访问主体
     *
     * @return 主体与主体标识
     */
    private SubjectContext resolveSubject() {
        if (SecurityUtils.isLogin()) {
            FileAccessSubject subject = FileAccessSubject.user(
                    SecurityUtils.getUserId(), SecurityUtils.getUserName(), SecurityUtils.isAdmin());
            return new SubjectContext(subject, String.valueOf(subject.getUserId()));
        }
        return new SubjectContext(FileAccessSubject.anonymous(), IdUtils.simpleUUID());
    }

    /**
     * 会话还原为访问主体
     *
     * @param session 会话
     * @return 访问主体
     */
    private FileAccessSubject toSubject(FilePreviewSession session) {
        return switch (session.getSubjectType()) {
            case USER -> FileAccessSubject.user(session.getUserId(), session.getUserName(), session.isAdmin());
            case ANON -> FileAccessSubject.anonymous();
            case SHARE_LINK -> FileAccessSubject.shareLink(session.getShareId(), session.getSharePermission());
            case SHARE_USER ->
                    FileAccessSubject.shareUser(session.getShareId(), session.getReceiverId(), session.getSharePermission());
        };
    }

    /**
     * 是否仅下载分享
     *
     * @param subject 主体
     * @return true：仅下载
     */
    private boolean isDownloadOnly(FileAccessSubject subject) {
        return subject != null
                && subject.isShare()
                && SharePermissionEnum.DOWNLOAD == subject.getSharePermission();
    }

    /**
     * 会话滑动 TTL
     *
     * @param subject 主体
     * @return 秒
     */
    private long ttlSeconds(FileAccessSubject subject) {
        return subject != null && FileAccessSubjectTypeEnum.ANON == subject.getType()
                ? ANON_SESSION_TTL_SECONDS : USER_SESSION_TTL_SECONDS;
    }

    /**
     * 会话绝对 TTL
     *
     * @param subject 主体
     * @return 秒
     */
    private long absoluteSeconds(FileAccessSubject subject) {
        return subject != null && FileAccessSubjectTypeEnum.ANON == subject.getType()
                ? ANON_SESSION_ABSOLUTE_SECONDS : USER_SESSION_ABSOLUTE_SECONDS;
    }

    /**
     * 解析初始化状态
     *
     * @param policy       策略
     * @param downloadOnly 是否仅下载
     * @return 初始化状态
     */
    private FileInitStatusEnum resolveInitStatus(FilePolicy policy, boolean downloadOnly) {
        if (downloadOnly) {
            return FileInitStatusEnum.DOWNLOAD_ONLY;
        }
        return policy.isPreviewable() ? FileInitStatusEnum.SUCCESS : FileInitStatusEnum.NOT_SUPPORTED;
    }

    /**
     * 构建状态响应
     *
     * @param session    会话
     * @param policy     策略
     * @param decision   决策
     * @param initStatus 初始化状态
     * @return 状态响应
     */
    private SysFilePreviewStatusResponse buildStatusResponse(FilePreviewSession session, FilePolicy policy,
                                                             FileAccessDecision decision, FileInitStatusEnum initStatus) {
        SysFilePreviewStatusResponse response = new SysFilePreviewStatusResponse();
        response.setPreviewId(session.getPreviewId());
        response.setInitStatus(initStatus);
        response.setPreviewMode(policy.getPreviewType());
        response.setCapabilities(buildCapabilities(decision, initStatus));
        response.setExpireAt(session.getAbsoluteExpireAt());
        return response;
    }

    /**
     * 构建能力清单
     *
     * @param decision   决策
     * @param initStatus 初始化状态
     * @return 能力清单
     */
    private FileCapabilityResponse buildCapabilities(FileAccessDecision decision, FileInitStatusEnum initStatus) {
        FileCapabilityResponse capabilities = new FileCapabilityResponse();
        capabilities.setPreview(true);
        capabilities.setDownload(true);
        return capabilities;
    }

    /**
     * 主体上下文
     *
     * @param subject   主体
     * @param subjectId 主体标识
     */
    private record SubjectContext(FileAccessSubject subject, String subjectId) {
    }

}
