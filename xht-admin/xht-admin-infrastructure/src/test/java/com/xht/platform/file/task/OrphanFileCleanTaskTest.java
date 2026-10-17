package com.xht.platform.file.task;

import com.xht.framework.file.core.FileStorageRepository;
import com.xht.platform.file.dao.SysFileDao;
import com.xht.platform.file.entity.SysFileEntity;
import com.xht.platform.file.enums.FileStatusEnum;
import com.xht.platform.file.properties.FileCleanProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * OrphanFileCleanTask 单元测试
 */
class OrphanFileCleanTaskTest {

    private SysFileDao sysFileDao;

    private FileStorageRepository fileStorageRepository;

    private FileCleanProperties fileCleanProperties;

    private OrphanFileCleanTask orphanFileCleanTask;

    @BeforeEach
    void setUp() {
        sysFileDao = Mockito.mock(SysFileDao.class);
        fileStorageRepository = Mockito.mock(FileStorageRepository.class);
        fileCleanProperties = new FileCleanProperties();
        fileCleanProperties.setEnabled(true);
        fileCleanProperties.setExpireDays(3);
        fileCleanProperties.setBatchSize(500);
        orphanFileCleanTask = new OrphanFileCleanTask(sysFileDao, fileStorageRepository, fileCleanProperties);
    }

    @Test
    void isOrphan_shouldOnlyMatchExpiredUploaded() {
        LocalDateTime now = LocalDateTime.of(2026, 10, 17, 3, 0);

        SysFileEntity expiredUploaded = buildEntity(FileStatusEnum.UPLOADED, now.minusDays(5));
        assertTrue(OrphanFileCleanTask.isOrphan(expiredUploaded, now, 3));

        SysFileEntity associated = buildEntity(FileStatusEnum.ASSOCIATED, now.minusDays(5));
        assertFalse(OrphanFileCleanTask.isOrphan(associated, now, 3), "已关联业务不可清理");

        SysFileEntity freshUploaded = buildEntity(FileStatusEnum.UPLOADED, now.minusDays(1));
        assertFalse(OrphanFileCleanTask.isOrphan(freshUploaded, now, 3), "未过期不可清理");

        SysFileEntity noUploadTime = buildEntity(FileStatusEnum.UPLOADED, null);
        assertFalse(OrphanFileCleanTask.isOrphan(noUploadTime, now, 3));

        assertFalse(OrphanFileCleanTask.isOrphan(null, now, 3));
    }

    @Test
    void cleanOrphanFiles_shouldDeletePhysicalFilesAndMarkDeleted() {
        SysFileEntity entity = buildEntity(FileStatusEnum.UPLOADED, LocalDateTime.now().minusDays(5));
        entity.setId(100L);
        entity.setFileStoragePath("xht/2026/a.png");
        entity.setFileThumbPath("xht/2026/a_thumb.jpg");
        when(sysFileDao.findExpiredUploadedFiles(any(), any())).thenReturn(List.of(entity));

        orphanFileCleanTask.cleanOrphanFiles();

        // 物理文件与缩略图均已删除
        verify(fileStorageRepository).remove("xht/2026/a.png");
        verify(fileStorageRepository).remove("xht/2026/a_thumb.jpg");
        // 记录被标记为已删除
        verify(sysFileDao).updateFileStatusById(100L, FileStatusEnum.DELETED);
    }

    @Test
    void cleanOrphanFiles_shouldMarkDeletedEvenWhenPhysicalDeleteFails() {
        SysFileEntity entity = buildEntity(FileStatusEnum.UPLOADED, LocalDateTime.now().minusDays(5));
        entity.setId(101L);
        entity.setFileStoragePath("xht/2026/b.png");
        // 物理删除抛异常（remove 实现本身不抛，这里用 mock 模拟底层异常场景）
        Mockito.doThrow(new RuntimeException("disk error")).when(fileStorageRepository).remove("xht/2026/b.png");
        when(sysFileDao.findExpiredUploadedFiles(any(), any())).thenReturn(List.of(entity));

        orphanFileCleanTask.cleanOrphanFiles();

        // 删除失败也标记 DELETED，防止反复捞取同一记录
        verify(sysFileDao).updateFileStatusById(101L, FileStatusEnum.DELETED);
    }

    @Test
    void cleanOrphanFiles_shouldSkipWhenDisabled() {
        fileCleanProperties.setEnabled(false);

        orphanFileCleanTask.cleanOrphanFiles();

        verify(sysFileDao, never()).findExpiredUploadedFiles(any(), any());
        verify(sysFileDao, never()).updateFileStatusById(anyLong(), any());
    }

    @Test
    void cleanOrphanFiles_shouldDoNothingWhenNoExpiredFiles() {
        when(sysFileDao.findExpiredUploadedFiles(any(), any())).thenReturn(List.of());

        orphanFileCleanTask.cleanOrphanFiles();

        verify(fileStorageRepository, never()).remove(any());
        verify(sysFileDao, never()).updateFileStatusById(anyLong(), any());
    }

    @Test
    void cleanOrphanFiles_shouldUseExpireDaysFromProperties() {
        orphanFileCleanTask.cleanOrphanFiles();

        ArgumentCaptor<LocalDateTime> captor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(sysFileDao).findExpiredUploadedFiles(any(), captor.capture());
        LocalDateTime expected = LocalDateTime.now().minusDays(3);
        // 允许 60 秒执行误差
        long diffSeconds = Math.abs(java.time.Duration.between(captor.getValue(), expected).toSeconds());
        assertTrue(diffSeconds < 60, "过期时间应为 now-3天，实际偏差秒数：" + diffSeconds);
    }

    private SysFileEntity buildEntity(FileStatusEnum status, LocalDateTime uploadTime) {
        SysFileEntity entity = new SysFileEntity();
        entity.setFileStatus(status);
        entity.setFileUploadTime(uploadTime);
        return entity;
    }

}
