package com.xht.platform.file.dao.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * SysFileDaoImpl 排序白名单单元测试
 */
class SysFileDaoImplSortTest {

    @Test
    void sanitizeSortField_shouldOnlyAllowWhitelistedFields() {
        assertEquals("fileSize", SysFileDaoImpl.sanitizeSortField("fileSize"));
        assertEquals("fileUploadTime", SysFileDaoImpl.sanitizeSortField("fileUploadTime"));
        assertEquals("id", SysFileDaoImpl.sanitizeSortField("id"));
    }

    @Test
    void sanitizeSortField_shouldRejectIllegalFields() {
        // 不在白名单的字段
        assertNull(SysFileDaoImpl.sanitizeSortField("fileStoragePath"));
        assertNull(SysFileDaoImpl.sanitizeSortField("fileMetadata"));
        // 注入尝试
        assertNull(SysFileDaoImpl.sanitizeSortField("id; DROP TABLE sys_file"));
        assertNull(SysFileDaoImpl.sanitizeSortField("file_size UNION SELECT 1"));
        // 空值
        assertNull(SysFileDaoImpl.sanitizeSortField(null));
        assertNull(SysFileDaoImpl.sanitizeSortField(""));
        assertNull(SysFileDaoImpl.sanitizeSortField("   "));
    }

}
