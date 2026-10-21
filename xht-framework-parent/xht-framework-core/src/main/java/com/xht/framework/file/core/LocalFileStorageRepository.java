package com.xht.framework.file.core;

import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.core.BusinessErrorCode;
import com.xht.framework.file.enums.StorageType;
import com.xht.framework.file.exception.FileStorageException;
import com.xht.framework.file.configurers.StorageProperties;
import com.xht.framework.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

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
    public String upload(InputStream inputStream, String filePath) {
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
        } catch (Exception e) {
            log.error("本地文件存储上传失败，文件路径：{}", targetPath, e);
            throw new FileStorageException(BusinessErrorCode.FILE_UPLOAD_FAIL, e);
        }
        return targetPath.toString();
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
                "文件不存在:" + filePath);
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
                "存储路径不合法:" + filePath);
        return targetPath;
    }

    /**
     * 获取文件存储类型
     *
     * @return 文件存储类型
     */
    @Override
    public StorageType storageType() {
        return StorageType.LOCAL;
    }

    /**
     * 生成文件预览地址
     *
     * @param fileStoragePath 文件存储路径
     * @return 文件预览地址
     */
    @Override
    public String generatePreviewUrl(String fileStoragePath) {
        AssertUtils.hasText(fileStoragePath, BusinessErrorCode.PARAM_ERROR);
        String viewPrefix = storageProperties.getViewPrefix();
        return viewPrefix + fileStoragePath;
    }
}
