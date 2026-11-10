package com.xht.platform.file.service;

import com.xht.platform.file.dao.SysFileConvertDao;
import com.xht.platform.file.domain.access.FilePolicy;
import com.xht.platform.file.entity.SysFileConvertEntity;
import com.xht.platform.file.entity.SysFileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述： 文件转换服务
 * <p>负责转换任务的查询与提交，实际转换由 {@link FileConvertExecutor} 异步执行</p>
 *
 * @author xht
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class FileConvertService {

    /**
     * 转换状态：待处理
     */
    public static final int STATUS_PENDING = 0;
    /**
     * 转换状态：处理中
     */
    public static final int STATUS_PROCESSING = 1;
    /**
     * 转换状态：成功
     */
    public static final int STATUS_SUCCESS = 2;
    /**
     * 转换状态：失败
     */
    public static final int STATUS_FAILED = 3;

    private final SysFileConvertDao sysFileConvertDao;

    private final FileConvertExecutor fileConvertExecutor;

    private final SysFileSettingService sysFileSettingService;

    /**
     * 查询转换任务
     *
     * @param fileId       文件ID
     * @param targetFormat 目标格式
     * @return 任务
     */
    public Optional<SysFileConvertEntity> find(Long fileId, String targetFormat) {
        if (fileId == null || targetFormat == null) {
            return Optional.empty();
        }
        return sysFileConvertDao.findByFileAndTarget(fileId, targetFormat);
    }

    /**
     * 提交转换任务
     *
     * @param file   文件
     * @param policy 策略
     * @return 任务
     */
    public SysFileConvertEntity submit(SysFileEntity file, FilePolicy policy) {
        Optional<SysFileConvertEntity> existing = find(file.getId(), policy.getConvertTarget());
        if (existing.isPresent()) {
            return existing.get();
        }
        SysFileConvertEntity task = new SysFileConvertEntity();
        task.setFileId(file.getId());
        task.setSourceSuffix(file.getFileSuffix());
        task.setTargetFormat(policy.getConvertTarget());
        task.setStatus(STATUS_PENDING);
        task.setRetryCount(0);
        sysFileConvertDao.save(task);
        try {
            fileConvertExecutor.execute(task.getId());
        } catch (TaskRejectedException e) {
            log.warn("转换队列已满，taskId={}", task.getId());
            task.setStatus(STATUS_FAILED);
            task.setErrorMsg("转换任务繁忙，请稍后重试");
            task.setEndTime(LocalDateTime.now());
            sysFileConvertDao.updateById(task);
        }
        return task;
    }

    /**
     * 重试失败任务
     *
     * @param task 失败任务
     * @return 任务
     */
    public SysFileConvertEntity retry(SysFileConvertEntity task) {
        int maxRetry = sysFileSettingService.getInt("convert.max-retry", 3);
        if (task == null || !Objects.equals(task.getStatus(), STATUS_FAILED)
                || (task.getRetryCount() == null ? 0 : task.getRetryCount()) >= maxRetry) {
            return task;
        }
        task.setStatus(STATUS_PENDING);
        task.setErrorMsg(null);
        task.setStartTime(null);
        task.setEndTime(null);
        sysFileConvertDao.updateById(task);
        try {
            fileConvertExecutor.execute(task.getId());
        } catch (TaskRejectedException e) {
            log.warn("转换队列已满，taskId={}", task.getId());
            task.setStatus(STATUS_FAILED);
            task.setErrorMsg("转换任务繁忙，请稍后重试");
            task.setEndTime(LocalDateTime.now());
            sysFileConvertDao.updateById(task);
        }
        return task;
    }

    /**
     * 判断任务是否成功
     *
     * @param task 任务
     * @return true：成功
     */
    public boolean isSuccess(SysFileConvertEntity task) {
        return Objects.nonNull(task) && Objects.equals(task.getStatus(), STATUS_SUCCESS);
    }

    /**
     * 判断任务是否处理中
     *
     * @param task 任务
     * @return true：待处理或处理中
     */
    public boolean isProcessing(SysFileConvertEntity task) {
        return Objects.nonNull(task)
                && (Objects.equals(task.getStatus(), STATUS_PENDING)
                || Objects.equals(task.getStatus(), STATUS_PROCESSING));
    }

}
