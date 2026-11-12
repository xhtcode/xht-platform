package com.xht.platform.file.service;

import cn.hutool.core.date.DateUtil;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.file.core.UploadResponse;
import com.xht.platform.file.convert.FileConverter;
import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileConvertDao;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.entity.SysFileConvertEntity;
import com.xht.platform.file.entity.SysFileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 描述： 文件转换执行器
 * <p>异步执行转换任务，使用临时文件与独立线程池，失败落 FAILED 并保留错误信息</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileConvertExecutor {

    private final SysFileConvertDao sysFileConvertDao;

    private final SysFileDao sysFileDao;

    private final FileStorageDao fileStorageDao;

    private final FileStorageRepository fileStorageRepository;

    private final SysFileSettingService sysFileSettingService;

    private final List<FileConverter> fileConverters;

    /**
     * 异步执行转换
     *
     * @param taskId 任务ID
     */
    @Async("file-convert-executor")
    public void execute(Long taskId) {
        SysFileConvertEntity task = sysFileConvertDao.findById(taskId);
        if (Objects.isNull(task) || !Objects.equals(task.getStatus(), FileConvertService.STATUS_PENDING)) {
            return;
        }
        SysFileEntity file = sysFileDao.findById(task.getFileId());
        if (Objects.isNull(file)) {
            markFailed(task, "源文件不存在");
            return;
        }
        long maxSizeMb = sysFileSettingService.getInt("convert.max-file-size-mb", 100);
        if (file.getFileSize() != null && file.getFileSize() > maxSizeMb * 1024 * 1024) {
            markFailed(task, "文件超过转换大小上限");
            return;
        }
        FileConverter converter = fileConverters.stream()
                .filter(c -> c.supports(file.getFileSuffix(), task.getTargetFormat()))
                .findFirst()
                .orElse(null);
        if (converter == null) {
            markFailed(task, "不支持的转换类型：" + file.getFileSuffix() + " -> " + task.getTargetFormat());
            return;
        }

        task.setStatus(FileConvertService.STATUS_PROCESSING);
        task.setStartTime(LocalDateTime.now());
        task.setErrorMsg(null);
        sysFileConvertDao.updateById(task);

        Path workDir = null;
        try {
            workDir = Files.createTempDirectory("xht-convert-");
            String sourceSuffix = file.getFileSuffix() == null ? "bin" : file.getFileSuffix().toLowerCase();
            Path source = workDir.resolve("source." + sourceSuffix);
            try (InputStream in = fileStorageDao.download(file)) {
                Files.copy(in, source, StandardCopyOption.REPLACE_EXISTING);
            }
            String targetExtension = converter.targetExtension(task.getTargetFormat());
            Path target = workDir.resolve("target." + targetExtension);
            converter.convert(source, sourceSuffix, target, task.getTargetFormat());
            if (!Files.exists(target) || Files.size(target) <= 0) {
                throw new IllegalStateException("转换产物为空");
            }

            String storagePath = "convert/" + DateUtil.now() + "/"
                    + task.getId() + "." + targetExtension;
            UploadResponse upload;
            try (InputStream in = Files.newInputStream(target)) {
                upload = fileStorageRepository.upload(in, storagePath);
            }
            task.setConvertedStorageKey(upload.getFileStoragePath());
            task.setConvertedSize(Files.size(target));
            task.setStatus(FileConvertService.STATUS_SUCCESS);
            task.setEndTime(LocalDateTime.now());
            int retentionDays = sysFileSettingService.getInt("convert.retention-days", 7);
            task.setExpireTime(LocalDateTime.now().plusDays(retentionDays));
            sysFileConvertDao.updateById(task);
            log.info("文件转换完成，taskId={}, fileId={}, target={}", task.getId(), file.getId(), task.getTargetFormat());
        } catch (Exception e) {
            log.error("文件转换失败，taskId={}, fileId={}", task.getId(), file.getId(), e);
            markFailed(task, e.getMessage());
        } finally {
            cleanup(workDir);
        }
    }

    /**
     * 标记失败
     *
     * @param task    任务
     * @param message 失败信息
     */
    private void markFailed(SysFileConvertEntity task, String message) {
        task.setStatus(FileConvertService.STATUS_FAILED);
        task.setEndTime(LocalDateTime.now());
        String error = message == null ? "转换失败" : message;
        task.setErrorMsg(error.length() > 500 ? error.substring(0, 500) : error);
        task.setRetryCount((task.getRetryCount() == null ? 0 : task.getRetryCount()) + 1);
        sysFileConvertDao.updateById(task);
    }

    /**
     * 清理临时目录
     *
     * @param workDir 目录
     */
    private void cleanup(Path workDir) {
        if (workDir == null) {
            return;
        }
        try (var stream = Files.walk(workDir)) {
            stream.sorted((a, b) -> b.getNameCount() - a.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (Exception ignored) {
                            // 临时文件清理失败不影响主流程
                        }
                    });
        } catch (Exception e) {
            log.warn("清理转换临时目录失败：{}", workDir, e);
        }
    }

}
