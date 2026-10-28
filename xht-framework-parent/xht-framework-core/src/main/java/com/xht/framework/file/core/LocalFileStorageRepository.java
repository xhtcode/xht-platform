package com.xht.framework.file.core;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.configurers.StorageProperties;
import com.xht.framework.file.enums.StorageType;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.utils.AssertUtils;
import com.xht.framework.utils.crypto.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BoundedInputStream;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 描述： 本地文件存储
 *
 * @author xht
 **/
@Slf4j
@Component
public class LocalFileStorageRepository implements FileStorageRepository {

    /**
     * 文件存储配置（本地存储根路径来源）
     */
    private final StorageProperties storageProperties;

    /**
     * 构造器：注入文件存储配置
     *
     * @param storageProperties 文件存储配置，为 null 时抛出 {@link NullPointerException}
     */
    public LocalFileStorageRepository(StorageProperties storageProperties) {
        this.storageProperties = Objects.requireNonNull(storageProperties, "storageProperties 不能为 null");
    }

    /**
     * 上传文件到本地磁盘
     * <p>
     * 行为说明：
     * <ul>
     *     <li>输入流为 null 时，抛出携带 FILE_EMPTY 错误码的 {@link BusinessException}</li>
     *     <li>文件路径为空白时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>以配置的本地存储根路径为基准解析目标路径，目标路径越出根路径（如 ../ 路径穿越）时，抛出携带 ILLEGAL_OPERATION 错误码的 {@link BusinessException}</li>
     *     <li>目标目录不存在时自动逐级创建，目标文件已存在时直接覆盖</li>
     *     <li>写入过程发生 IO 异常时，包装为携带 FILE_UPLOAD_FAIL 错误码的 {@link FileStorageException} 抛出，保留原始堆栈</li>
     *     <li>输入流由调用方负责关闭，本方法不会主动关闭</li>
     * </ul>
     *
     * @param inputStream 文件输入流，不能为 null
     * @param filePath    文件相对路径（相对于本地存储根路径），不能为空白
     */
    @Override
    public UploadResponse upload(InputStream inputStream, String filePath) {
        AssertUtils.notNull(inputStream, BusinessErrorCode.FILE_UPLOAD_EMPTY);
        AssertUtils.hasText(filePath, BusinessErrorCode.PARAM_ERROR);
        // 目标文件路径
        Path targetPath = resolveStoragePath(filePath);
        try {
            Path parentPath = targetPath.getParent();
            if (parentPath != null) {
                Files.createDirectories(parentPath);
            }
            // 写入文件，允许覆盖；不需要覆盖就删掉 REPLACE_EXISTING
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.debug("本地文件存储上传成功，文件路径：{}", targetPath);
            return UploadResponse
                    .builder()
                    .storageType(StorageType.LOCAL)
                    .bucket(storageProperties.getLocal().getBasePath())
                    .fileStoragePath(filePath)
                    .fileMd5(MD5Utils.generateSignature(Files.readAllBytes(targetPath)))
                    .build();
        } catch (Exception e) {
            log.error("本地文件存储上传失败，文件路径：{}", targetPath, e);
            throw new FileStorageException(BusinessErrorCode.FILE_UPLOAD_FAIL, e);
        }
    }

    /**
     * 下载本地磁盘文件
     * <p>
     * 行为说明：
     * <ul>
     *     <li>文件路径为空白时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>以配置的本地存储根路径为基准解析目标路径，目标路径越出根路径（如 ../ 路径穿越）时，抛出携带 ILLEGAL_OPERATION 错误码的 {@link BusinessException}</li>
     *     <li>目标文件不存在时，抛出携带 FILE_NOT_EXIST 错误码的 {@link BusinessException}</li>
     *     <li>打开文件流发生 IO 异常时，包装为携带 FILE_DOWNLOAD_FAIL 错误码的 {@link FileStorageException} 抛出，保留原始堆栈</li>
     *     <li>返回的文件输入流由调用方负责关闭</li>
     * </ul>
     *
     * @param filePath 文件相对路径（相对于本地存储根路径），不能为空白
     * @return 文件输入流，由调用方负责关闭
     */
    @Override
    public InputStream download(String filePath) {
        AssertUtils.hasText(filePath, BusinessErrorCode.PARAM_ERROR);
        // 目标文件路径
        Path targetPath = resolveStoragePath(filePath);
        // 目标文件必须存在
        AssertUtils.isTrue(Files.isRegularFile(targetPath),
                BusinessErrorCode.FILE_NOT_EXIST,
                String.format("文件不存在:%s", filePath));
        try {
            InputStream inputStream = Files.newInputStream(targetPath);
            log.debug("本地文件存储下载成功，文件路径：{}", targetPath);
            return inputStream;
        } catch (Exception e) {
            log.error("本地文件存储下载失败，文件路径：{}", targetPath, e);
            throw new FileStorageException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 按偏移、长度读取本地文件片段
     * <p>
     * 行为说明：
     * <ul>
     *     <li>文件路径为空白、偏移量为负、读取长度非正时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>以配置的本地存储根路径为基准解析目标路径，目标路径越出根路径（如 ../ 路径穿越）时，抛出携带 ILLEGAL_OPERATION 错误码的 {@link BusinessException}</li>
     *     <li>目标文件不存在时，抛出携带 FILE_NOT_EXIST 错误码的 {@link BusinessException}</li>
     *     <li>偏移量不小于文件大小时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>读取长度超出文件剩余大小时，按实际可读长度返回（不抛异常），便于按 Range 顺序读到文件末尾</li>
     *     <li>基于 {@link FileChannel} 随机定位，不会读取偏移量之前的内容</li>
     *     <li>返回的文件输入流由调用方负责关闭，关闭流即关闭底层文件通道</li>
     * </ul>
     *
     * @param filePath 文件相对路径（相对于本地存储根路径），不能为空白
     * @param offset   起始字节偏移，不能小于 0
     * @param length   需要读取的字节数，必须大于 0
     * @return 片段输入流，由调用方负责关闭
     */
    @Override
    public InputStream downloadRange(String filePath, long offset, long length) {
        AssertUtils.hasText(filePath, BusinessErrorCode.PARAM_ERROR);
        AssertUtils.isTrue(offset >= 0, BusinessErrorCode.PARAM_ERROR, "起始偏移量不能小于0");
        AssertUtils.isTrue(length > 0, BusinessErrorCode.PARAM_ERROR, "读取长度必须大于0");
        // 目标文件路径
        Path targetPath = resolveStoragePath(filePath);
        // 目标文件必须存在
        AssertUtils.isTrue(Files.isRegularFile(targetPath),
                BusinessErrorCode.FILE_NOT_EXIST,
                String.format("文件不存在:%s", filePath));
        long fileSize;
        try {
            fileSize = Files.size(targetPath);
        } catch (Exception e) {
            log.error("本地文件存储读取文件大小失败，文件路径：{}", targetPath, e);
            throw new FileStorageException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
        AssertUtils.isTrue(offset < fileSize,
                BusinessErrorCode.PARAM_ERROR,
                String.format("起始偏移量超出文件大小:%d", offset));
        // 超出文件剩余大小时按实际可读长度截断
        long rangeLength = Math.min(length, fileSize - offset);
        FileChannel channel = null;
        try {
            channel = FileChannel.open(targetPath, StandardOpenOption.READ);
            channel.position(offset);
            // 关闭返回的流会同步关闭底层通道
            InputStream inputStream = BoundedInputStream.builder()
                    .setInputStream(Channels.newInputStream(channel))
                    .setMaxCount(rangeLength)
                    .get();
            log.debug("本地文件存储按范围读取成功，文件路径：{}，offset：{}，length：{}", targetPath, offset, rangeLength);
            return inputStream;
        } catch (Exception e) {
            closeQuietly(channel);
            log.error("本地文件存储按范围读取失败，文件路径：{}，offset：{}", targetPath, offset, e);
            throw new FileStorageException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 静默关闭文件通道，异常仅忽略
     *
     * @param channel 文件通道，允许为 null
     */
    private void closeQuietly(FileChannel channel) {
        if (channel == null) {
            return;
        }
        try {
            channel.close();
        } catch (Exception e) {
            log.warn("本地文件存储通道关闭失败", e);
        }
    }

    /**
     * 列举本地目录下的文件名
     * <p>
     * 行为说明：
     * <ul>
     *     <li>目录路径为空白时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>以配置的本地存储根路径为基准解析目标路径，目标路径越出根路径（如 ../ 路径穿越）时，抛出携带 ILLEGAL_OPERATION 错误码的 {@link BusinessException}</li>
     *     <li>目录不存在时返回空集合</li>
     *     <li>仅返回目录下的普通文件名（不含路径），不递归子目录，按文件名自然顺序排序</li>
     *     <li>列举过程发生 IO 异常时，包装为携带 FILE_DOWNLOAD_FAIL 错误码的 {@link FileStorageException} 抛出，保留原始堆栈</li>
     * </ul>
     *
     * @param dirPath 目录相对路径（相对于本地存储根路径），不能为空白
     * @return 文件名列表（按文件名排序），目录不存在时返回空集合
     */
    @Override
    public List<String> listFileNames(String dirPath) {
        AssertUtils.hasText(dirPath, BusinessErrorCode.PARAM_ERROR);
        // 目标目录路径（复用防路径穿越校验）
        Path targetPath = resolveStoragePath(dirPath);
        if (!Files.isDirectory(targetPath)) {
            return Collections.emptyList();
        }
        try (Stream<Path> stream = Files.list(targetPath)) {
            List<String> fileNames = stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .toList();
            log.debug("本地文件存储列举目录成功，目录路径：{}，文件数：{}", targetPath, fileNames.size());
            return fileNames;
        } catch (Exception e) {
            log.error("本地文件存储列举目录失败，目录路径：{}", targetPath, e);
            throw new FileStorageException(BusinessErrorCode.FILE_DOWNLOAD_FAIL, e);
        }
    }

    /**
     * 删除本地磁盘文件
     * <p>
     * 行为说明：
     * <ul>
     *     <li>文件路径为空白时，抛出携带 PARAM_ERROR 错误码的 {@link BusinessException}</li>
     *     <li>以配置的本地存储根路径为基准解析目标路径，目标路径越出根路径（如 ../ 路径穿越）时，抛出携带 ILLEGAL_OPERATION 错误码的 {@link BusinessException}</li>
     *     <li>目标文件不存在时静默返回（幂等删除）</li>
     *     <li>删除过程发生 IO 异常时仅记录错误日志，不向上抛出（清理类调用方不应因单个文件失败而中断）</li>
     * </ul>
     *
     * @param filePath 相对路径（相对于本地存储根路径），不能为空白
     */
    @Override
    public void remove(String filePath) {
        AssertUtils.hasText(filePath, BusinessErrorCode.PARAM_ERROR);
        // 目标文件路径（复用防路径穿越校验）
        Path targetPath = resolveStoragePath(filePath);
        try {
            boolean deleted = Files.deleteIfExists(targetPath);
            if (deleted) {
                log.debug("本地文件存储删除成功，文件路径：{}", targetPath);
            }
        } catch (Exception e) {
            log.error("本地文件存储删除失败，文件路径：{}", targetPath, e);
        }
    }

    /**
     * 以配置的本地存储根路径为基准解析目标文件路径，并做防路径穿越校验
     *
     * @param filePath 文件相对路径（相对于本地存储根路径）
     * @return 目标文件绝对路径
     */
    private Path resolveStoragePath(String filePath) {
        // 存储根目录
        Path basePath = Paths.get(storageProperties.getLocal().getBasePath())
                .toAbsolutePath()
                .normalize();
        // 目标文件路径
        Path targetPath = basePath.resolve(filePath).normalize();
        // 防路径穿越校验
        AssertUtils.isTrue(targetPath.startsWith(basePath),
                BusinessErrorCode.ILLEGAL_OPERATION,
                String.format("存储路径不合法:%s", filePath));
        return targetPath;
    }

    /**
     * 生成文件预览地址
     *
     * @param fileStorageKey 文件存储key
     * @return 文件预览地址
     */
    @Override
    public String generatePreviewUrl(String fileStorageKey) {
        AssertUtils.hasText(fileStorageKey, BusinessErrorCode.PARAM_ERROR);
        String viewPrefix = storageProperties.getViewPrefix();
        return viewPrefix + fileStorageKey;
    }
}
