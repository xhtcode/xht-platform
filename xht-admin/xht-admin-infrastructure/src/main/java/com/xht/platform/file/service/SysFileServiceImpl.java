package com.xht.platform.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.common.domain.response.PageResponse;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.mybatis.utils.PageTool;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.framework.utils.AssertUtils;
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
import com.xht.platform.file.domain.access.ShareAccessContext;
import com.xht.platform.file.domain.form.FilePreviewInitForm;
import com.xht.platform.file.domain.query.SysFileQuery;
import com.xht.platform.file.domain.response.FileCapabilityResponse;
import com.xht.platform.file.domain.response.FileConversionResponse;
import com.xht.platform.file.domain.response.FilePreviewUrlsResponse;
import com.xht.platform.file.domain.response.FileWatermarkResponse;
import com.xht.platform.file.domain.response.SysFilePreviewResponse;
import com.xht.platform.file.domain.response.SysFilePreviewStatusResponse;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.domain.session.FilePreviewSession;
import com.xht.platform.file.entity.SysFileConvertEntity;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.entity.SysFileShareReceiverEntity;
import com.xht.platform.file.enums.FileAccessScopeEnum;
import com.xht.platform.file.enums.FileAccessSubjectTypeEnum;
import com.xht.platform.file.enums.FileActionEnum;
import com.xht.platform.file.enums.FileInitStatusEnum;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.enums.PreviewSourceEnum;
import com.xht.platform.file.enums.PreviewTypeEnum;
import com.xht.platform.file.enums.SharePermissionEnum;
import com.xht.platform.file.enums.WatermarkLevelEnum;
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
import java.util.Optional;

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

    private final AnonymousSessionService anonymousSessionService;

    private final FileConvertService fileConvertService;

    private final SysFileSettingService sysFileSettingService;

    private final ISysFileShareService fileShareService;

    private final WatermarkService watermarkService;

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
        AssertUtils.notNull(form, BusinessErrorCode.PARAM_ERROR);
        SysFileEntity file;
        SubjectContext subjectContext;
        if (StringUtils.hasText(form.getShareTicket())) {
            ShareAccessContext shareContext = fileShareService.resolveTicket(form.getShareTicket());
            file = findFile(shareContext.getFileId());
            String subjectId = shareContext.getReceiverId() != null
                    ? String.valueOf(shareContext.getReceiverId())
                    : String.valueOf(shareContext.getShareId());
            subjectContext = new SubjectContext(shareContext.getSubject(), subjectId);
        } else {
            file = resolveFile(form);
            subjectContext = resolveSubject(request, response);
        }
        FileAccessDecision decision = fileAccessEvaluator.decide(file, subjectContext.subject());
        if (!decision.isView() && !decision.isDownload()) {
            throw new BusinessException(BusinessErrorCode.FILE_ACCESS_DENIED);
        }
        FilePolicy policy = filePolicyResolver.resolve(file);
        boolean downloadOnly = isDownloadOnly(subjectContext.subject())
                || (!decision.isView() && decision.isDownload());
        ConversionResult conversion = downloadOnly
                ? new ConversionResult(FileInitStatusEnum.DOWNLOAD_ONLY, null,
                conversion(false, "NONE", null, null, null))
                : resolveConversion(file, policy, true);

        FileInitStatusEnum initStatus = conversion.initStatus();
        if (downloadOnly) {
            initStatus = FileInitStatusEnum.DOWNLOAD_ONLY;
        }

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
                .fileId(file.getId())
                .previewMode(policy.getPreviewType())
                .previewSource(policy.getPreviewSource())
                .convertTaskId(conversion.taskId())
                .ttlSeconds(ttl)
                .absoluteExpireAt(LocalDateTime.now().plusSeconds(absolute))
                .build();
        previewSessionService.create(session);
        return buildPreviewResponse(session, file, policy, decision, conversion);
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
        ConversionResult conversion = downloadOnly
                ? new ConversionResult(FileInitStatusEnum.DOWNLOAD_ONLY, null,
                conversion(false, "NONE", null, null, null))
                : resolveConversion(file, policy, false);
        return buildStatusResponse(session, policy, decision, conversion);
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
        SysFileEntity source = file;
        if (policy.isNeedConvert()) {
            SysFileConvertEntity task = fileConvertService
                    .find(file.getId(), policy.getConvertTarget())
                    .orElse(null);
            if (fileConvertService.isProcessing(task)) {
                throw new BusinessException(BusinessErrorCode.FILE_CONVERTING);
            }
            if (!fileConvertService.isSuccess(task)) {
                throw new BusinessException(BusinessErrorCode.FILE_CONVERT_FAIL,
                        task == null ? "转换任务不存在" : task.getErrorMsg());
            }
            source = buildConvertedFile(file, task);
        }
        if (WatermarkLevelEnum.L2 == policy.getWatermarkLevel()
                && (PreviewTypeEnum.PDF == policy.getPreviewType() || PreviewTypeEnum.IMAGE == policy.getPreviewType())) {
            writeWatermarked(response, source, policy, session);
            return;
        }
        for (FilePreview filePreview : filePreviewList) {
            if (filePreview.supports(policy.getPreviewType())) {
                filePreview.filePreview(request, response, source);
                return;
            }
        }
        throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_NOT_SUPPORT);
    }

    /**
     * 输出带服务端水印的预览内容
     *
     * @param response 响应
     * @param source   源文件或转换产物
     * @param policy   策略
     * @param session  会话
     */
    private void writeWatermarked(HttpServletResponse response, SysFileEntity source,
                                  FilePolicy policy, FilePreviewSession session) {
        String text = watermarkText(session, true);
        try (InputStream input = fileStorageDao.download(source)) {
            byte[] data = PreviewTypeEnum.PDF == policy.getPreviewType()
                    ? watermarkService.watermarkPdf(input, text)
                    : watermarkService.watermarkImage(input, text);
            String contentType = PreviewTypeEnum.PDF == policy.getPreviewType()
                    ? "application/pdf" : "image/png";
            String fileName = StringUtils.hasText(source.getFileOriginalName())
                    ? source.getFileOriginalName() : source.getFileStorageName();
            response.setContentType(contentType);
            response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                    org.springframework.http.ContentDisposition.inline()
                            .filename(fileName, java.nio.charset.StandardCharsets.UTF_8)
                            .build().toString());
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader(org.springframework.http.HttpHeaders.CACHE_CONTROL, "private, no-store");
            response.setContentLengthLong(data.length);
            response.getOutputStream().write(data);
            response.flushBuffer();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("服务端水印预览失败，fileId={}", source.getId(), e);
            throw new BusinessException(BusinessErrorCode.FILE_PREVIEW_FAIL, e);
        }
    }

    /**
     * 定位文件
     *
     * @param form 初始化请求
     * @return 文件实体
     */
    private SysFileEntity resolveFile(FilePreviewInitForm form) {
        AssertUtils.notNull(form, BusinessErrorCode.PARAM_ERROR);
        if (form.getFileId() != null) {
            return findFile(form.getFileId());
        }
        AssertUtils.hasText(form.getFileStorageKey(), BusinessErrorCode.PARAM_ERROR);
        return sysFileDao.findOneOptional(SysFileEntity::getFileStorageKey, form.getFileStorageKey())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
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
     * 构建转换产物文件视图
     *
     * @param source 源文件
     * @param task   转换任务
     * @return 转换产物文件视图
     */
    private SysFileEntity buildConvertedFile(SysFileEntity source, SysFileConvertEntity task) {
        if (task == null || !StringUtils.hasText(task.getConvertedStorageKey())) {
            throw new BusinessException(BusinessErrorCode.FILE_CONVERT_FAIL, "转换产物不存在");
        }
        String path = task.getConvertedStorageKey();
        String extension = "pdf";
        int dot = path.lastIndexOf('.');
        if (dot > -1 && dot < path.length() - 1) {
            extension = path.substring(dot + 1).toLowerCase();
        }
        String baseName = StringUtils.hasText(source.getFileOriginalName())
                ? source.getFileOriginalName() : source.getFileStorageName();
        if (StringUtils.hasText(baseName)) {
            int nameDot = baseName.lastIndexOf('.');
            if (nameDot > 0) {
                baseName = baseName.substring(0, nameDot);
            }
        }
        String convertedName = (baseName == null ? "preview" : baseName) + "." + extension;
        SysFileEntity converted = new SysFileEntity();
        converted.setId(source.getId());
        converted.setFileStoragePath(path);
        converted.setFileStorageName(convertedName);
        converted.setFileOriginalName(convertedName);
        converted.setFileSuffix(extension);
        converted.setFileType("pdf".equals(extension) ? "application/pdf" : "image/png");
        converted.setFileSize(task.getConvertedSize());
        converted.setFileStatus(source.getFileStatus());
        return converted;
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
     * @param request  请求
     * @param response 响应
     * @return 主体与主体标识
     */
    private SubjectContext resolveSubject(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityUtils.isLogin()) {
            FileAccessSubject subject = FileAccessSubject.user(
                    SecurityUtils.getUserId(), SecurityUtils.getUserName(), SecurityUtils.isAdmin());
            return new SubjectContext(subject, String.valueOf(subject.getUserId()));
        }
        String anonId = anonymousSessionService.getOrCreate(request, response);
        return new SubjectContext(FileAccessSubject.anonymous(), anonId);
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
            case SHARE_USER -> FileAccessSubject.shareUser(session.getShareId(), session.getReceiverId(), session.getSharePermission());
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
     * 解析转换状态
     *
     * @param file     文件
     * @param policy   策略
     * @param allowSubmit 是否允许提交任务
     * @return 转换结果
     */
    private ConversionResult resolveConversion(SysFileEntity file, FilePolicy policy, boolean allowSubmit) {
        if (!policy.isPreviewable()) {
            return new ConversionResult(FileInitStatusEnum.NOT_SUPPORTED, null,
                    conversion(false, "NONE", null, "当前文件类型不支持在线预览", null));
        }
        if (!policy.isNeedConvert()) {
            return new ConversionResult(FileInitStatusEnum.SUCCESS, null,
                    conversion(false, "NONE", null, null, null));
        }
        Optional<SysFileConvertEntity> existing = fileConvertService.find(file.getId(), policy.getConvertTarget());
        SysFileConvertEntity task = existing.orElse(null);
        if (Objects.isNull(task) && allowSubmit) {
            task = fileConvertService.submit(file, policy);
        }
        if (Objects.isNull(task)) {
            return new ConversionResult(FileInitStatusEnum.CONVERTING, null,
                    conversion(true, "PENDING", 0, "等待转换", 1000L));
        }
        if (allowSubmit && Objects.equals(task.getStatus(), FileConvertService.STATUS_FAILED)) {
            task = fileConvertService.retry(task);
        }
        if (fileConvertService.isSuccess(task)) {
            return new ConversionResult(FileInitStatusEnum.SUCCESS, task.getId(),
                    conversion(true, "SUCCESS", 100, null, null));
        }
        if (fileConvertService.isProcessing(task)) {
            String status = Objects.equals(task.getStatus(), FileConvertService.STATUS_PENDING) ? "PENDING" : "PROCESSING";
            return new ConversionResult(FileInitStatusEnum.CONVERTING, task.getId(),
                    conversion(true, status, 50, "正在转换为 PDF", 1000L));
        }
        return new ConversionResult(FileInitStatusEnum.FAILED, task.getId(),
                conversion(true, "FAILED", 0, task.getErrorMsg(), null));
    }

    /**
     * 构建转换响应
     *
     * @param required     是否需要转换
     * @param status       状态
     * @param progress     进度
     * @param message      提示
     * @param retryAfterMs 重试间隔
     * @return 转换响应
     */
    private FileConversionResponse conversion(boolean required, String status, Integer progress, String message, Long retryAfterMs) {
        FileConversionResponse response = new FileConversionResponse();
        response.setRequired(required);
        response.setStatus(status);
        response.setProgress(progress);
        response.setMessage(message);
        response.setRetryAfterMs(retryAfterMs);
        return response;
    }

    /**
     * 构建初始化响应
     *
     * @param session    会话
     * @param file       文件
     * @param policy     策略
     * @param decision   决策
     * @param conversion 转换结果
     * @return 初始化响应
     */
    private SysFilePreviewResponse buildPreviewResponse(FilePreviewSession session, SysFileEntity file,
                                                        FilePolicy policy, FileAccessDecision decision,
                                                        ConversionResult conversion) {
        SysFilePreviewResponse response = new SysFilePreviewResponse();
        response.setPreviewId(session.getPreviewId());
        response.setInitStatus(conversion.initStatus());
        response.setFileName(StringUtils.hasText(file.getFileOriginalName())
                ? file.getFileOriginalName() : file.getFileStorageName());
        response.setFileOriginalName(file.getFileOriginalName());
        response.setFileSize(file.getFileSize());
        response.setFileSizeInfo(file.getFileSizeInfo());
        response.setFileSuffix(file.getFileSuffix());
        response.setFileType(file.getFileType());
        response.setAccessScope(policy.getAccessScope());
        response.setPreviewMode(policy.getPreviewType());
        response.setPreviewSource(policy.getPreviewSource());
        response.setCapabilities(buildCapabilities(decision, conversion.initStatus()));
        response.setConversion(conversion.response());
        response.setWatermark(buildWatermark(policy, session));
        response.setUrls(buildUrls(session.getPreviewId()));
        response.setExpireAt(session.getAbsoluteExpireAt());
        return response;
    }

    /**
     * 构建状态响应
     *
     * @param session    会话
     * @param policy     策略
     * @param decision   决策
     * @param conversion 转换结果
     * @return 状态响应
     */
    private SysFilePreviewStatusResponse buildStatusResponse(FilePreviewSession session, FilePolicy policy,
                                                             FileAccessDecision decision, ConversionResult conversion) {
        SysFilePreviewStatusResponse response = new SysFilePreviewStatusResponse();
        response.setPreviewId(session.getPreviewId());
        response.setInitStatus(conversion.initStatus());
        response.setPreviewMode(policy.getPreviewType());
        response.setPreviewSource(policy.getPreviewSource());
        response.setCapabilities(buildCapabilities(decision, conversion.initStatus()));
        response.setConversion(conversion.response());
        response.setWatermark(buildWatermark(policy, session));
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
        boolean previewable = initStatus != FileInitStatusEnum.NOT_SUPPORTED
                && initStatus != FileInitStatusEnum.DOWNLOAD_ONLY
                && initStatus != FileInitStatusEnum.FAILED;
        FileCapabilityResponse capabilities = new FileCapabilityResponse();
        capabilities.setPreview(decision.isView() && previewable);
        capabilities.setDownload(decision.isDownload());
        capabilities.setPrint(decision.isPrint());
        capabilities.setShare(decision.isShare());
        return capabilities;
    }

    /**
     * 构建水印信息
     *
     * @param policy  策略
     * @param session 会话
     * @return 水印信息
     */
    private FileWatermarkResponse buildWatermark(FilePolicy policy, FilePreviewSession session) {
        FileWatermarkResponse response = new FileWatermarkResponse();
        WatermarkLevelEnum level = policy.getWatermarkLevel();
        response.setLevel(level == null ? WatermarkLevelEnum.OFF.getValue() : level.getValue());
        response.setEnabled(level != null && level != WatermarkLevelEnum.OFF && policy.isPreviewable());
        response.setOpacity(sysFileSettingService.getDecimal("watermark.opacity", java.math.BigDecimal.valueOf(0.10)).doubleValue());
        response.setText(watermarkText(session, response.isEnabled()));
        return response;
    }

    /**
     * 生成水印文案
     *
     * @param session 会话
     * @param enabled 是否启用水印
     * @return 文案
     */
    private String watermarkText(FilePreviewSession session, boolean enabled) {
        if (!enabled) {
            return null;
        }
        String template = sysFileSettingService.getString("watermark.text-template",
                "{userName}({userId}) {time} 禁止外传");
        String userName = StringUtils.hasText(session.getUserName()) ? session.getUserName() : "访客";
        String userId = session.getUserId() == null ? session.getSubjectId() : String.valueOf(session.getUserId());
        return template
                .replace("{userName}", userName)
                .replace("{userId}", userId == null ? "" : userId)
                .replace("{time}", LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    /**
     * 构建访问地址
     *
     * @param previewId 预览ID
     * @return 地址
     */
    private FilePreviewUrlsResponse buildUrls(String previewId) {
        FilePreviewUrlsResponse urls = new FilePreviewUrlsResponse();
        urls.setPreviewUrl("/sys/files/preview/content?previewId=" + previewId);
        urls.setDownloadUrl("/sys/files/preview/download?previewId=" + previewId);
        urls.setStatusUrl("/sys/files/preview/status?previewId=" + previewId);
        return urls;
    }

    /**
     * 转换结果
     *
     * @param initStatus 初始化状态
     * @param taskId     任务ID
     * @param response   转换响应
     */
    private record ConversionResult(FileInitStatusEnum initStatus, Long taskId, FileConversionResponse response) {
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
