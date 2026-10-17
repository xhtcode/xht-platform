package com.xht.platform.file.task;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xht.framework.file.core.FileStorageRepository;
import com.xht.framework.utils.StringUtils;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.properties.FileCleanProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 描述： 孤儿文件清理定时任务
 * <p>
 * 清理策略（防误删硬约束）：
 * <ul>
 *     <li>仅清理状态为 UPLOADED（已上传未关联）且上传时间明确超过 {@link FileCleanProperties#getExpireDays()} 的文件</li>
 *     <li>ASSOCIATED 等任何其他状态一律不动</li>
 *     <li>逻辑删除由 MyBatis-Plus @TableLogic 自动附加 del_flag = 0 条件</li>
 *     <li>物理文件删除失败也标记为 DELETED（防反复捞同一记录），并记录 error 日志供人工介入</li>
 * </ul>
 *
 * @author xht
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class OrphanFileCleanTask {

    private final SysFileDao sysFileDao;

    private final FileStorageRepository fileStorageRepository;

    private final FileCleanProperties fileCleanProperties;

    /**
     * 定时清理孤儿文件（默认每天凌晨 3 点，cron 可通过 xht.file.clean.cron 配置）
     */
    @Scheduled(cron = "${xht.file.clean.cron:0 0 3 * * ?}")
    public void cleanOrphanFiles() {
        if (!fileCleanProperties.isEnabled()) {
            return;
        }
        LocalDateTime expireBefore = LocalDateTime.now().minusDays(fileCleanProperties.getExpireDays());
        Page<SysFileEntity> page = new Page<>(1, fileCleanProperties.getBatchSize());
        List<SysFileEntity> expiredFiles = sysFileDao.findExpiredUploadedFiles(page, expireBefore);
        if (expiredFiles.isEmpty()) {
            log.debug("孤儿文件清理：无待清理文件");
            return;
        }
        int successCount = 0;
        int failCount = 0;
        for (SysFileEntity entity : expiredFiles) {
            // 物理文件删除失败仅记录日志，不阻断状态标记（防止反复捞取同一记录）
            try {
                if (StringUtils.hasText(entity.getFileStoragePath())) {
                    fileStorageRepository.remove(entity.getFileStoragePath());
                }
                if (StringUtils.hasText(entity.getFileThumbPath())) {
                    fileStorageRepository.remove(entity.getFileThumbPath());
                }
            } catch (Exception e) {
                log.error("孤儿文件物理删除失败，fileId：{}，fileStoragePath：{}", entity.getId(), entity.getFileStoragePath(), e);
            }
            try {
                sysFileDao.updateFileStatusById(entity.getId(), FileStatusEnum.DELETED);
                successCount++;
            } catch (Exception e) {
                failCount++;
                log.error("孤儿文件状态标记失败，fileId：{}", entity.getId(), e);
            }
        }
        log.info("孤儿文件清理完成，扫描{}，清理{}，失败{}", expiredFiles.size(), successCount, failCount);
    }

    /**
     * 判断文件是否为孤儿文件（纯函数，便于单元测试）
     *
     * @param entity     文件实体
     * @param now        当前时间
     * @param expireDays 过期天数
     * @return true：孤儿文件
     */
    static boolean isOrphan(SysFileEntity entity, LocalDateTime now, int expireDays) {
        if (Objects.isNull(entity) || Objects.isNull(now) || expireDays < 0) {
            return false;
        }
        // 仅已上传未关联的文件可被清理
        if (!FileStatusEnum.UPLOADED.equals(entity.getFileStatus())) {
            return false;
        }
        LocalDateTime uploadTime = entity.getFileUploadTime();
        return Objects.nonNull(uploadTime) && uploadTime.isBefore(now.minusDays(expireDays));
    }

}
