package com.xht.platform.file.job;

import com.xht.platform.file.dao.FileStorageDao;
import com.xht.platform.file.dao.SysFileConvertDao;
import com.xht.platform.file.dao.SysFileShareDao;
import com.xht.platform.file.entity.SysFileConvertEntity;
import com.xht.platform.file.service.SysFileSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述： 转换产物清理任务
 * <p>每日清理过期产物；若文件仍有有效分享，则按分享到期时间顺延，不删除</p>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class FileConvertCleanupJob {

    private static final int BATCH_SIZE = 200;

    private final SysFileConvertDao sysFileConvertDao;

    private final SysFileShareDao sysFileShareDao;

    private final FileStorageDao fileStorageDao;

    private final SysFileSettingService sysFileSettingService;

    /**
     * 清理过期转换产物
     */
    @Scheduled(cron = "${xht.file.convert.cleanup-cron:0 0 3 * * ?}")
    public void cleanup() {
        LocalDateTime now = LocalDateTime.now();
        int bufferDays = sysFileSettingService.getInt("convert.share-buffer-days", 1);
        int maxRetentionDays = sysFileSettingService.getInt("convert.max-retention-days", 31);
        int deleted = 0;
        int extended = 0;
        while (true) {
            List<SysFileConvertEntity> list = sysFileConvertDao.findExpiredSuccess(now, BATCH_SIZE);
            if (list.isEmpty()) {
                break;
            }
            for (SysFileConvertEntity task : list) {
                try {
                    LocalDateTime maxShareExpire = sysFileShareDao.findMaxActiveExpire(task.getFileId(), now);
                    if (maxShareExpire != null) {
                        LocalDateTime newExpire = maxShareExpire.plusDays(bufferDays);
                        LocalDateTime hardLimit = now.plusDays(maxRetentionDays);
                        task.setExpireTime(newExpire.isAfter(hardLimit) ? hardLimit : newExpire);
                        sysFileConvertDao.updateById(task);
                        extended++;
                        continue;
                    }
                    fileStorageDao.remove(task.getConvertedStorageKey());
                    sysFileConvertDao.removeById(task.getId());
                    deleted++;
                } catch (Exception e) {
                    log.error("清理转换产物失败，taskId={}, fileId={}", task.getId(), task.getFileId(), e);
                }
            }
            if (list.size() < BATCH_SIZE) {
                break;
            }
        }
        if (deleted > 0 || extended > 0) {
            log.info("转换产物清理完成，删除 {} 条，顺延 {} 条", deleted, extended);
        }
    }

}
