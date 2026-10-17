package com.xht.platform.file.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.oauth2.utils.SecurityUtils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.DownloadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.common.file.core.FileMetadata;
import com.xht.platform.common.file.utils.FileTypeDetectUtils;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.converter.SysFileConverter;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.domain.response.SysFileResponse;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.enums.FileUsedEnum;
import com.xht.platform.file.properties.FileUploadProperties;
import com.xht.platform.file.thumbnail.ThumbnailGenerator;
import com.xht.platform.file.thumbnail.ThumbnailProperties;
import com.xht.platform.file.utils.FileAccessChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
/**
 * 描述： 文件存储服务实现类
 * 负责文件上传、存储、元数据入库等核心逻辑，对接底层文件存储仓库
 * <p>
 * 上传安全策略：服务端权威校验大小与后缀白名单、文件头魔术字节探测 MIME 并与后缀交叉校验、
 * 文件名清洗（防路径穿越与非法字符）、单流双工（边落盘边计算 SHA-256，不全量读入内存）、
 * 落盘/入库失败时补偿删除已写入文件
 *
 * @author xht
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {

    /**
     * 默认内容类型（探测与推断均失败时兜底）
     */
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    /**
     * 文件存储仓库，对接底层存储实现（本地/对象存储等）
     */
    private final FileStorageRepository fileStorageRepository;

    /**
     * 文件DAO，负责文件元数据数据库持久化
     */
    private final SysFileDao sysFileDao;

    /**
     * 文件转换器：实体 <-> Response DTO
     */
    private final SysFileConverter sysFileConverter;

    /**
     * 文件上传配置（大小限制与后缀白名单）
     */
    private final FileUploadProperties fileUploadProperties;

    /**
     * 文件访问权限校验器
     */
    private final FileAccessChecker fileAccessChecker;

    /**
     * 缩略图生成器
     */
    private final ThumbnailGenerator thumbnailGenerator;

    /**
     * 缩略图配置
     */
    private final ThumbnailProperties thumbnailProperties;

    /**
     * 文件上传
     *
     * @param uploadBo 文件上传参数
     * @return 文件信息
     */
    @Override
    public SysFileResponse uploadMultipartFile(UploadBO<MultipartFile> uploadBo) {
        MultipartFile requestFile = uploadBo.getRequestFile();
        String fileUsed = uploadBo.getFileUsed();
        String filePathPrefix = uploadBo.getFilePathPrefix();
        // 参数校验：文件对象非空、文件内容不为空
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        // 文件名清洗：防路径穿越与非法字符
        String originalFilename = requestFile.getOriginalFilename();
        String safeFilename = SysFileUtils.sanitizeFileName(originalFilename);
        long fileSize = requestFile.getSize();
        // 后缀白名单校验（服务端权威）
        String fileSuffix = FileUtil.getSuffix(safeFilename).toLowerCase(Locale.ROOT);
        AssertUtils.isTrue(fileUploadProperties.isAllowedSuffix(fileSuffix),
                BusinessErrorCode.FILE_TYPE_NOT_SUPPORT, "不允许上传的文件类型：" + fileSuffix);
        // 大小校验（服务端权威）
        AssertUtils.isTrue(fileSize <= fileUploadProperties.getMaxSize().toBytes(),
                BusinessErrorCode.FILE_SIZE_EXCEED, "文件大小超出限制：" + fileUploadProperties.getMaxSize());
        // 文件用途校验：非法用途直接拒绝
        String normalizedFileUsed = FileUsedEnum.normalize(fileUsed);
        AssertUtils.notNull(normalizedFileUsed, BusinessErrorCode.PARAM_ERROR, "文件用途不合法：" + fileUsed);

        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(safeFilename);
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(normalizedFileUsed);
        sysFileEntity.setFileUploadUser(SecurityUtils.getUserName());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());

        // 文件头探测 MIME，无歧义类型与后缀交叉校验（防改名绕过）
        String fileType = resolveAndCheckContentType(requestFile, fileSuffix);
        sysFileEntity.setFileType(fileType);

        // 拼装存储信息：统一 / 分隔符，兼容跨平台
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = SysFileUtils.normalizeStoragePath(filePathPrefix)
                + StringConstant.SEPARATOR_SLASH + fileStorageName;
        sysFileEntity.setFileStorageType(fileStorageRepository.storageType().getValue());
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileStoragePath(fileStoragePath);

        // 单流双工：探测用的头部字节与原流拼接后包 DigestInputStream，边落盘边计算哈希，不再二次读盘
        try {
            byte[] headBytes = FileTypeDetectUtils.readHeadBytes(requestFile.getInputStream());
            InputStream fullStream = new SequenceInputStream(new ByteArrayInputStream(headBytes), requestFile.getInputStream());
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            try (InputStream digestIn = new DigestInputStream(fullStream, messageDigest)) {
                fileStorageRepository.upload(digestIn, fileStoragePath);
            }
            sysFileEntity.setFileHash(HexUtil.encodeHexStr(messageDigest.digest()));
        } catch (BusinessException e) {
            // 业务异常（如路径不合法）保留原始错误码，仅补偿已写入文件
            fileStorageRepository.remove(fileStoragePath);
            throw e;
        } catch (Exception e) {
            log.error("文件上传异常，fileStoragePath：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }

        // 长文件名兜底：原始文件名被截断时，完整名称记录到元数据
        sysFileEntity.setFileMetadata(FileMetadataBuilder
                .builder()
                .fullOriginalFilename(originalFilename)
                .build());

        // 缩略图生成（仅图片类）：小图同步生成（失败不影响上传），大图入库后异步生成
        boolean needThumbnail = thumbnailProperties.isEnabled() && thumbnailGenerator.supports(fileSuffix);
        String thumbnailPath = needThumbnail ? SysFileUtils.resolveThumbnailPath(fileStoragePath) : null;
        String thumbnailKey = needThumbnail ? fileStorageKey + SysFileConstant.THUMBNAIL_NAME_FLAG : null;
        if (needThumbnail && fileSize <= thumbnailProperties.getSyncThreshold().toBytes()) {
            try {
                thumbnailGenerator.generate(fileStoragePath, thumbnailPath);
                sysFileEntity.setFileThumbKey(thumbnailKey);
                sysFileEntity.setFileThumbPath(thumbnailPath);
            } catch (Exception e) {
                log.error("缩略图同步生成失败，不影响上传主流程，fileStoragePath：{}", fileStoragePath, e);
            }
        }

        // 元数据入库：失败时补偿删除已落盘文件，避免产生孤儿文件
        try {
            sysFileDao.save(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            if (needThumbnail) {
                fileStorageRepository.remove(thumbnailPath);
            }
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }

        // 大图异步生成缩略图（需先入库拿到文件ID）
        if (needThumbnail && fileSize > thumbnailProperties.getSyncThreshold().toBytes()) {
            thumbnailGenerator.generateAsync(sysFileEntity.getId(), fileStoragePath, thumbnailPath);
        }

        return sysFileConverter.toResponse(sysFileEntity);
    }

    /**
     * 文件下载
     *
     * @param fileStorageKey 文件存储key
     * @return 文件下载信息（文件流、原始文件名、内容类型、文件大小、hash、上传时间）
     */
    @Override
    public DownloadBO downloadFile(String fileStorageKey) {
        // 参数校验：文件存储key非空
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        // 文件记录必须存在且未被删除，并通过访问权限校验
        SysFileEntity sysFileEntity = findActiveByStorageKey(fileStorageKey);
        fileAccessChecker.check(sysFileEntity);
        // 从底层存储仓库读取文件流，流由调用方（控制层）负责关闭
        InputStream inputStream = fileStorageRepository.download(sysFileEntity.getFileStoragePath());
        return DownloadBO.of(inputStream,
                resolveDownloadFilename(sysFileEntity),
                sysFileEntity.getFileType(),
                sysFileEntity.getFileSize(),
                sysFileEntity.getFileHash(),
                sysFileEntity.getFileUploadTime());
    }

    /**
     * 缩略图下载（按原图存储key查询）
     *
     * @param fileStorageKey 原图文件存储key
     * @return 缩略图下载信息（文件流、原始文件名、内容类型、文件大小、hash、上传时间）
     */
    @Override
    public DownloadBO downloadThumbnail(String fileStorageKey) {
        // 参数校验：文件存储key非空
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        // 文件记录必须存在且未被删除，并通过访问权限校验
        SysFileEntity sysFileEntity = findActiveByStorageKey(fileStorageKey);
        fileAccessChecker.check(sysFileEntity);
        // 缩略图必须已生成（大图异步生成中/非图片文件时为空白）
        AssertUtils.hasText(sysFileEntity.getFileThumbPath(), BusinessErrorCode.FILE_NOT_EXIST, "文件不存在缩略图");
        InputStream inputStream = fileStorageRepository.download(sysFileEntity.getFileThumbPath());
        return DownloadBO.of(inputStream,
                resolveDownloadFilename(sysFileEntity) + SysFileConstant.THUMBNAIL_NAME_FLAG,
                "image/jpeg",
                null,
                sysFileEntity.getFileHash(),
                sysFileEntity.getFileUploadTime());
    }

    /**
     * 根据存储key查找未删除的文件记录
     *
     * @param fileStorageKey 文件存储key
     * @return 文件实体
     */
    private SysFileEntity findActiveByStorageKey(String fileStorageKey) {        SysFileEntity sysFileEntity = sysFileDao.findOneOptional(SysFileEntity::getFileStorageKey, fileStorageKey)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.FILE_NOT_EXIST));
        // 已删除的文件不允许下载
        AssertUtils.isTrue(!FileStatusEnum.DELETED.equals(sysFileEntity.getFileStatus()),
                BusinessErrorCode.FILE_NOT_EXIST,
                "文件已删除，无法下载");
        return sysFileEntity;
    }

    /**
     * 解析并校验内容类型：优先文件头探测；探测成功且为无歧义类型时与后缀交叉校验，不符则拒绝；
     * 探测失败回退按后缀推断；均失败兜底 application/octet-stream
     *
     * @param requestFile 上传文件
     * @param fileSuffix  文件后缀（已小写）
     * @return 入库的内容类型
     */
    private String resolveAndCheckContentType(MultipartFile requestFile, String fileSuffix) {
        byte[] headBytes;
        try {
            headBytes = FileTypeDetectUtils.readHeadBytes(requestFile.getInputStream());
        } catch (Exception e) {
            log.error("读取上传文件头失败，无法探测内容类型", e);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        String detectedMime = FileTypeDetectUtils.detectMimeType(headBytes);
        if (StringUtils.hasText(detectedMime) && FileTypeDetectUtils.isUnambiguousMime(detectedMime)) {
            String suffixMime = FileTypeDetectUtils.resolveMimeType(fileSuffix);
            // 后缀可推断且与探测结果不一致，判定为改名绕过
            AssertUtils.isTrue(!StringUtils.hasText(suffixMime) || Objects.equals(detectedMime, suffixMime),
                    BusinessErrorCode.FILE_TYPE_NOT_SUPPORT,
                    "文件内容与后缀不匹配， detected：" + detectedMime + "，suffix：" + fileSuffix);
            return detectedMime;
        }
        String suffixMime = FileTypeDetectUtils.resolveMimeType(fileSuffix);
        return StringUtils.hasText(suffixMime) ? suffixMime : DEFAULT_CONTENT_TYPE;
    }

    /**
     * 解析下载文件名：优先取元数据中的完整原始文件名（长文件名截断场景的兜底），其次取文件原始名称，最后兜底取文件存储名称
     *
     * @param sysFileEntity 系统文件实体
     * @return 下载文件名
     */
    private String resolveDownloadFilename(SysFileEntity sysFileEntity) {
        FileMetadata fileMetadata = sysFileEntity.getFileMetadata();
        if (Objects.nonNull(fileMetadata)) {
            String fullOriginalFilename = fileMetadata.getFullOriginalFilename();
            if (StringUtils.hasText(fullOriginalFilename)) {
                return fullOriginalFilename;
            }
        }
        return StringUtils.emptyToDefault(sysFileEntity.getFileOriginalName(), sysFileEntity.getFileStorageName());
    }
}
