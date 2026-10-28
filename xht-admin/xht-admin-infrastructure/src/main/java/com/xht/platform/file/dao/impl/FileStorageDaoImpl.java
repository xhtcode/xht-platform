package com.xht.platform.file.dao.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.framework.file.enums.MimeTypeEnums;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.IdUtils;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.common.file.FileMetadataBuilder;
import com.xht.platform.common.file.bo.ChunkMergeBO;
import com.xht.platform.common.file.bo.ChunkUploadBO;
import com.xht.platform.common.file.bo.UploadBO;
import com.xht.platform.common.file.constant.SysFileConstant;
import com.xht.platform.common.file.utils.SysFileUtils;
import com.xht.platform.file.FileMetadataSupplier;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.mapper.SysFileMapper;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
@Repository
@RequiredArgsConstructor
public class FileStorageDaoImpl implements FileStorageDao {

    /**
     * 文件存储仓库，对接底层存储实现（本地/对象存储等）
     */
    private final FileStorageRepository fileStorageRepository;

    /**
     * 文件Mapper
     */
    private final SysFileMapper sysFileMapper;

    /**
     * 上传文件
     *
     * @param multipartFileUploadBO 上传入参（封装MultipartFile）
     * @param fileMetadataSupplier  文件元数据提供者
     * @return 文件上传响应信息
     * @throws FileStorageException 文件存储异常
     */
    @Override
    public SysFileEntity uploadMultipartFile(UploadBO<MultipartFile> multipartFileUploadBO, FileMetadataSupplier<MultipartFile> fileMetadataSupplier) {
        AssertUtils.notNull(multipartFileUploadBO, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        AssertUtils.notNull(fileMetadataSupplier, BusinessErrorCode.PARAM_ERROR);
        MultipartFile requestFile = multipartFileUploadBO.getRequestFile();
        String fileUsed = multipartFileUploadBO.getFileUsed();
        String filePathPrefix = multipartFileUploadBO.getFilePathPrefix();
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        String originalFilename = requestFile.getOriginalFilename();
        long fileSize = requestFile.getSize();
        String fileSuffix = FileUtil.getSuffix(originalFilename);
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(SysFileUtils.getFileName(originalFilename, fileSuffix));
        sysFileEntity.setFileSize(fileSize);
        sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(fileSize));
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(fileUsed);
        sysFileEntity.setFileUploadUser(multipartFileUploadBO.getFileUploadUser());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileType(MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType());
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = filePathPrefix + File.separator + fileStorageName;
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileDesc(multipartFileUploadBO.getFileDesc());
        sysFileEntity.setFileMetadata(fileMetadataSupplier.get(multipartFileUploadBO));
        try (InputStream digestIn = requestFile.getInputStream()) {
            UploadResponse upload = fileStorageRepository.upload(digestIn, fileStoragePath);
            sysFileEntity.setFileStorageType(upload.getStorageType());
            sysFileEntity.setFileHash(upload.getFileMd5());
            sysFileEntity.setFileStoragePath(upload.getFileStoragePath());
        } catch (BusinessException e) {
            fileStorageRepository.remove(fileStoragePath);
            throw e;
        } catch (Exception e) {
            log.error("文件上传异常:fileStoragePath:{} 信息:{}", fileStoragePath, e.getMessage(), e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        try {
            sysFileMapper.insert(sysFileEntity);
        } catch (Exception e) {
            log.error("文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        return sysFileEntity;
    }

    /**
     * 查询已上传分片序号列表（断点续传）
     * <p>分片按约定路径 {chunks}/{uploadId}/{chunkIndex}.part 存放，列举目录后按文件名解析序号；
     * 目录不存在（尚未上传任何分片）时返回空集合</p>
     *
     * @param uploadId 分片上传标识
     * @return 已上传分片序号列表（升序），无已上传分片时返回空集合
     * @throws FileStorageException 目录列举失败等存储异常
     */
    @Override
    public List<Integer> findUploadedChunkIndexes(String uploadId) {
        AssertUtils.hasText(uploadId, BusinessErrorCode.PARAM_ERROR);
        List<String> fileNames = fileStorageRepository.listFileNames(buildChunkDir(uploadId));
        return fileNames.stream()
                .filter(fileName -> fileName.endsWith(SysFileConstant.CHUNK_PART_SUFFIX))
                .map(fileName -> fileName.substring(0, fileName.length() - SysFileConstant.CHUNK_PART_SUFFIX.length()))
                .filter(indexName -> indexName.chars().allMatch(Character::isDigit))
                .map(Integer::parseInt)
                .sorted()
                .toList();
    }

    /**
     * 上传单个文件分片
     * <p>分片重复上传时直接覆盖（幂等），支持断点续传场景下的失败重传；
     * 写入失败时同步清理残留分片文件</p>
     *
     * @param chunkUploadBO 分片上传入参（封装分片文件）
     * @throws FileStorageException 分片写入失败等存储异常
     */
    @Override
    public void uploadChunk(ChunkUploadBO<MultipartFile> chunkUploadBO) {
        AssertUtils.notNull(chunkUploadBO, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        MultipartFile requestFile = chunkUploadBO.getRequestFile();
        AssertUtils.isTrue(!requestFile.isEmpty(), BusinessErrorCode.FILE_CONTENT_EMPTY);
        String chunkPath = buildChunkPath(chunkUploadBO.getUploadId(), chunkUploadBO.getChunkIndex());
        try (InputStream inputStream = requestFile.getInputStream()) {
            fileStorageRepository.upload(inputStream, chunkPath);
        } catch (BusinessException e) {
            fileStorageRepository.remove(chunkPath);
            throw e;
        } catch (Exception e) {
            log.error("分片上传异常:chunkPath:{} 信息:{}", chunkPath, e.getMessage(), e);
            fileStorageRepository.remove(chunkPath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
    }

    /**
     * 合并文件分片：校验分片完整性（0 ~ chunkTotal-1 全部存在），按分片序号顺序合并为完整文件并生成文件记录（sys_file），
     * 合并成功后清理分片临时文件
     * <p>
     * 行为说明：
     * <ul>
     *     <li>分片缺失（序号不连续或数量不足）时，抛出携带 FILE_CHUNK_MISSING 错误码的 {@link BusinessException}</li>
     *     <li>合并参数中的文件MD5非空时，合并完成后校验一致性，不一致抛 FILE_CHUNK_VERIFY_FAIL 并清理合并产物</li>
     *     <li>合并失败或入库失败时，同步清理已合并的存储文件；分片临时文件保留，便于失败重试</li>
     * </ul>
     *
     * @param chunkMergeBO 分片合并入参
     * @return 文件实体
     * @throws FileStorageException 分片缺失、合并失败等存储异常
     */
    @Override
    public SysFileEntity mergeChunks(ChunkMergeBO chunkMergeBO) {
        AssertUtils.notNull(chunkMergeBO, BusinessErrorCode.PARAM_ERROR);
        String uploadId = chunkMergeBO.getUploadId();
        List<Integer> chunkIndexes = findUploadedChunkIndexes(uploadId);
        checkChunkComplete(chunkIndexes, chunkMergeBO.getChunkTotal(), uploadId);
        String fileSuffix = FileUtil.getSuffix(chunkMergeBO.getFileName());
        String fileStorageKey = IdUtils.simpleUUID();
        String fileStorageName = fileStorageKey + StringConstant.POINT + fileSuffix;
        String fileStoragePath = chunkMergeBO.getFilePathPrefix() + File.separator + fileStorageName;
        SysFileEntity sysFileEntity = buildChunkFileEntity(chunkMergeBO, fileStorageKey, fileStorageName, fileSuffix);
        try (InputStream mergedStream = openChunkStreams(uploadId, chunkIndexes);
             CountingInputStream countingStream = new CountingInputStream(mergedStream)) {
            UploadResponse upload = fileStorageRepository.upload(countingStream, fileStoragePath);
            sysFileEntity.setFileSize(countingStream.getCount());
            sysFileEntity.setFileSizeInfo(FileUtil.readableFileSize(countingStream.getCount()));
            sysFileEntity.setFileStorageType(upload.getStorageType());
            sysFileEntity.setFileHash(upload.getFileMd5());
            sysFileEntity.setFileStoragePath(upload.getFileStoragePath());
            // 文件整体MD5非空时校验合并结果一致性
            AssertUtils.isTrue(StringUtils.isEmpty(chunkMergeBO.getFileHash())
                            || chunkMergeBO.getFileHash().equalsIgnoreCase(upload.getFileMd5()),
                    BusinessErrorCode.FILE_CHUNK_VERIFY_FAIL);
        } catch (BusinessException e) {
            fileStorageRepository.remove(fileStoragePath);
            throw e;
        } catch (Exception e) {
            log.error("分片合并异常:uploadId:{} fileStoragePath:{} 信息:{}", uploadId, fileStoragePath, e.getMessage(), e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        try {
            sysFileMapper.insert(sysFileEntity);
        } catch (Exception e) {
            log.error("分片合并后文件记录入库失败，已清理存储文件：{}", fileStoragePath, e);
            fileStorageRepository.remove(fileStoragePath);
            throw new BusinessException(BusinessErrorCode.FILE_UPLOAD_FAIL, e.getMessage(), e);
        }
        // 合并与入库均成功后清理分片临时文件，清理失败仅记录日志不影响主流程
        cleanChunks(uploadId, chunkIndexes);
        return sysFileEntity;
    }

    /**
     * 校验分片完整性：已上传分片数量与序号必须与 0 ~ chunkTotal-1 完全一致
     *
     * @param chunkIndexes 已上传分片序号列表（升序、去重）
     * @param chunkTotal   分片总数
     * @param uploadId     分片上传标识（用于异常提示定位）
     */
    private void checkChunkComplete(List<Integer> chunkIndexes, Integer chunkTotal, String uploadId) {
        AssertUtils.isTrue(chunkIndexes.size() == chunkTotal,
                BusinessErrorCode.FILE_CHUNK_MISSING,
                String.format("分片不完整：期望%d片，已上传%d片", chunkTotal, chunkIndexes.size()));
        for (int i = 0; i < chunkTotal; i++) {
            AssertUtils.isTrue(Objects.equals(chunkIndexes.get(i), i),
                    BusinessErrorCode.FILE_CHUNK_MISSING,
                    String.format("分片缺失：缺少第%d片", i));
        }
    }

    /**
     * 按分片序号顺序打开全部分片输入流，并串联为单一输入流
     * <p>【契约说明】返回的输入流由调用方负责关闭，关闭即关闭全部分片流</p>
     *
     * @param uploadId     分片上传标识
     * @param chunkIndexes 已上传分片序号列表（升序）
     * @return 串联后的合并输入流
     */
    private InputStream openChunkStreams(String uploadId, List<Integer> chunkIndexes) {
        List<InputStream> chunkStreams = new ArrayList<>(chunkIndexes.size());
        try {
            for (Integer chunkIndex : chunkIndexes) {
                chunkStreams.add(fileStorageRepository.download(buildChunkPath(uploadId, chunkIndex)));
            }
        } catch (Exception e) {
            // 部分流打开失败时，关闭已打开的流，防止文件句柄泄漏
            chunkStreams.forEach(IOUtils::closeQuietly);
            throw e;
        }
        return new SequenceInputStream(Collections.enumeration(chunkStreams));
    }

    /**
     * 构建分片合并产生的文件实体（元数据部分），文件大小、hash、存储类型与路径由合并结果回填
     *
     * @param chunkMergeBO    分片合并入参
     * @param fileStorageKey  文件存储key
     * @param fileStorageName 文件存储名称
     * @param fileSuffix      文件后缀
     * @return 文件实体
     */
    private SysFileEntity buildChunkFileEntity(ChunkMergeBO chunkMergeBO, String fileStorageKey, String fileStorageName, String fileSuffix) {
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setFileOriginalName(SysFileUtils.getFileName(chunkMergeBO.getFileName(), fileSuffix));
        sysFileEntity.setFileSuffix(fileSuffix);
        sysFileEntity.setFileStatus(FileStatusEnum.UPLOADED);
        sysFileEntity.setFileUsed(chunkMergeBO.getFileUsed());
        sysFileEntity.setFileUploadUser(chunkMergeBO.getFileUploadUser());
        sysFileEntity.setFileUploadTime(LocalDateTime.now());
        sysFileEntity.setFileType(MimeTypeEnums.getByFileSuffix(fileSuffix).getContentType());
        sysFileEntity.setFileStorageKey(fileStorageKey);
        sysFileEntity.setFileStorageName(fileStorageName);
        sysFileEntity.setFileDesc(chunkMergeBO.getFileDesc());
        sysFileEntity.setFileMetadata(FileMetadataBuilder
                .builder()
                .fullOriginalFilename(chunkMergeBO.getFileName())
                .build());
        return sysFileEntity;
    }

    /**
     * 清理分片临时文件与分片目录，清理失败仅记录日志（清理类操作不应阻断主流程）
     *
     * @param uploadId     分片上传标识
     * @param chunkIndexes 已上传分片序号列表
     */
    private void cleanChunks(String uploadId, List<Integer> chunkIndexes) {
        for (Integer chunkIndex : chunkIndexes) {
            fileStorageRepository.remove(buildChunkPath(uploadId, chunkIndex));
        }
        // 分片文件清理后目录为空，一并移除
        fileStorageRepository.remove(buildChunkDir(uploadId));
    }

    /**
     * 构建分片目录相对路径（相对于存储根路径）：{chunks}/{uploadId}
     *
     * @param uploadId 分片上传标识
     * @return 分片目录路径
     */
    private String buildChunkDir(String uploadId) {
        return SysFileConstant.CHUNK_DIR + File.separator + uploadId;
    }

    /**
     * 构建分片文件相对路径（相对于存储根路径）：{chunks}/{uploadId}/{chunkIndex}.part
     *
     * @param uploadId   分片上传标识
     * @param chunkIndex 分片序号
     * @return 分片文件路径
     */
    private String buildChunkPath(String uploadId, Integer chunkIndex) {
        return buildChunkDir(uploadId) + File.separator + chunkIndex + SysFileConstant.CHUNK_PART_SUFFIX;
    }

    /**
     * 统计读取字节数的输入流包装（合并分片时用于回填文件大小）
     */
    private static class CountingInputStream extends FilterInputStream {

        /**
         * 已读取字节数
         */
        private long count;

        /**
         * 构造器
         *
         * @param in 被包装的输入流
         */
        CountingInputStream(InputStream in) {
            super(in);
        }

        @Override
        public int read() throws java.io.IOException {
            int data = super.read();
            if (data >= 0) {
                count++;
            }
            return data;
        }

        @Override
        public int read(byte[] buffer, int offset, int length) throws java.io.IOException {
            int readCount = super.read(buffer, offset, length);
            if (readCount > 0) {
                count += readCount;
            }
            return readCount;
        }

        /**
         * 获取已读取字节数
         *
         * @return 已读取字节数
         */
        long getCount() {
            return count;
        }
    }

    /**
     * 下载文件
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     *
     * @param fileStoragePath 文件存储路径
     * @return 文件输入流
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    @Override
    public InputStream download(String fileStoragePath) {
        AssertUtils.notNull(fileStoragePath, BusinessErrorCode.FILE_NOT_EXIST);
        return fileStorageRepository.download(fileStoragePath);
    }

    /**
     * 按偏移、长度读取文件片段（断点续传、视频分片播放等 Range 场景）
     * <p>【契约说明】返回的输入流由调用方负责关闭，推荐 try-with-resources，防止文件句柄泄漏</p>
     * <p>偏移量、长度参数在此处统一校验，具体的定位读取能力由底层存储实现（{@link FileStorageRepository}）提供：
     * 偏移量或长度非法抛 PARAM_ERROR；文件不存在抛 FILE_NOT_EXIST；读取失败抛 FILE_DOWNLOAD_FAIL</p>
     *
     * @param fileStoragePath 文件存储路径
     * @param offset          起始字节偏移，不能小于 0
     * @param length          需要读取多少字节，必须大于 0；超出文件剩余大小时按实际长度返回
     * @return 片段输入流，由调用方负责关闭
     * @throws FileStorageException 文件不存在、读取失败等存储异常
     */
    @Override
    public InputStream downloadRange(String fileStoragePath, long offset, long length) {
        AssertUtils.hasText(fileStoragePath, BusinessErrorCode.FILE_NOT_EXIST);
        AssertUtils.isTrue(offset >= 0, BusinessErrorCode.PARAM_ERROR, "起始偏移量不能小于0");
        AssertUtils.isTrue(length > 0, BusinessErrorCode.PARAM_ERROR, "读取长度必须大于0");
        return fileStorageRepository.downloadRange(fileStoragePath, offset, length);
    }

    /**
     * 关联文件业务（单文件）：标记为已关联（ASSOCIATED）并记录关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     * @param fileUsed       文件用途
     * @param fileDesc       文件描述
     */
    @Override
    public void associateFile(String fileStorageKey, String fileUsed, String fileDesc) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileUsed, fileUsed);
        wrapper.set(SysFileEntity::getFileDesc, fileDesc);
        wrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        wrapper.set(SysFileEntity::getFileAssociateTime, LocalDateTime.now());
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        AssertUtils.isTrue(SqlHelper.retBool(sysFileMapper.update(wrapper)), BusinessErrorCode.FILE_ASSOCIATE_FAIL);
    }

    /**
     * 解关联文件业务（单文件）：标记为已解关联（DISASSOCIATED）并记录解关联时间
     * <p>
     * 禁止使用批处理：每次仅处理一个文件
     *
     * @param fileStorageKey 文件存储key
     */
    @Override
    public void disassociateFile(String fileStorageKey) {
        LambdaUpdateWrapper<SysFileEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(SysFileEntity::getFileStatus, FileStatusEnum.UPLOADED);
        wrapper.eq(SysFileEntity::getFileStorageKey, fileStorageKey);
        wrapper.eq(SysFileEntity::getFileStatus, FileStatusEnum.ASSOCIATED);
        AssertUtils.isTrue(SqlHelper.retBool(sysFileMapper.update(wrapper)), BusinessErrorCode.FILE_DISASSOCIATE_FAIL);
    }

}
