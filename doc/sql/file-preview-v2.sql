-- ============================================================
-- 文件预览 v2.3 数据库脚本
-- 内容：表结构变更 + 初始化数据 + 历史能力清理
-- 适用：MySQL 8.0+
-- 执行：mysql -u root -p xht_platform < file-preview-v2.sql
--
-- 重要：
--   1. 执行前请备份 sys_file、sys_file_type。
--   2. 脚本可重复执行：ALTER 使用 information_schema 判重，
--      种子数据使用 INSERT IGNORE，不会覆盖已修改的配置。
--   3. 分享 / 转换 / 水印三项能力已从代码中移除，第 8 节负责清理
--      真实库中的残留表与列（幂等，重复执行无副作用）。
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 0. 备份（按需取消注释）
-- ============================================================
-- CREATE TABLE `sys_file_bak_20261109` AS SELECT * FROM `sys_file`;
-- CREATE TABLE `sys_file_type_bak_20261109` AS SELECT * FROM `sys_file_type`;

-- ============================================================
-- 1. sys_file 变更
-- ============================================================

-- 1.1 access_scope
SET @sql = (SELECT IF(
    COUNT(*) = 0,
    'ALTER TABLE `sys_file` ADD COLUMN `access_scope` varchar(20) NOT NULL DEFAULT ''private'' COMMENT ''访问范围：private/public'' AFTER `file_used`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file'
   AND COLUMN_NAME = 'access_scope');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 1.2 file_config
SET @sql = (SELECT IF(
    COUNT(*) = 0,
    'ALTER TABLE `sys_file` ADD COLUMN `file_config` json NULL COMMENT ''文件级配置覆盖'' AFTER `access_scope`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file'
   AND COLUMN_NAME = 'file_config');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 1.3 索引
SET @sql = (SELECT IF(
    COUNT(*) = 0,
    'CREATE INDEX `idx_file_access_scope` ON `sys_file`(`access_scope`)',
    'SET @noop = 1'
) FROM information_schema.STATISTICS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file'
   AND INDEX_NAME = 'idx_file_access_scope');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 1.4 存量公开用途回填
UPDATE `sys_file`
SET `access_scope` = 'public'
WHERE `file_used` IN ('avatar', 'notice')
  AND `access_scope` = 'private';

-- ============================================================
-- 2. sys_file_type 变更（只承载技术配置）
-- ============================================================

-- 2.1 sort
SET @sql = (SELECT IF(
    COUNT(*) = 0,
    'ALTER TABLE `sys_file_type` ADD COLUMN `sort` int NOT NULL DEFAULT 0 COMMENT ''排序'' AFTER `file_suffix`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_type'
   AND COLUMN_NAME = 'sort');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 2.2 存量 Office 后缀改为仅下载（已移除文件转换能力，不再在线预览）
UPDATE `sys_file_type`
SET `preview_type` = 'other',
    `preview_name` = '其他'
WHERE `file_suffix` IN ('doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'rtf', 'odt', 'ods', 'odp')
  AND `del_flag` = 0;

-- ============================================================
-- 3. 新建表
-- ============================================================

-- 3.1 文件用途配置
CREATE TABLE IF NOT EXISTS `sys_file_used`
(
    `id`                     bigint       NOT NULL COMMENT '主键ID',
    `used_code`              varchar(32)  NOT NULL COMMENT '用途编码，创建后不可改',
    `used_name`              varchar(64)  NOT NULL COMMENT '用途名称',
    `description`            varchar(500) NULL DEFAULT NULL COMMENT '说明',
    `built_in`               tinyint      NOT NULL DEFAULT 0 COMMENT '1=内置，不可删除',
    `enabled`                tinyint      NOT NULL DEFAULT 1 COMMENT '是否启用',
    `sort`                   int          NOT NULL DEFAULT 0 COMMENT '排序',
    `access_scope`           varchar(20)  NOT NULL DEFAULT 'private' COMMENT '默认访问范围 private/public',
    `public_allowed`         tinyint      NOT NULL DEFAULT 0 COMMENT '是否允许设为 public',
    `download_allowed`       tinyint      NOT NULL DEFAULT 1 COMMENT '下载闸门',
    `print_allowed`          tinyint      NOT NULL DEFAULT 1 COMMENT '打印闸门',
    `config_version`         int          NOT NULL DEFAULT 0 COMMENT '配置版本',
    `create_by`              varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`            datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`              varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`            datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`               tinyint      NOT NULL DEFAULT 0 COMMENT '删除标识 0未删除 1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_used_code` (`used_code`, `del_flag`) USING BTREE,
    KEY `idx_used_enabled` (`enabled`, `sort`) USING BTREE
) ENGINE = InnoDB COMMENT = '文件用途配置' ROW_FORMAT = Dynamic;

-- 3.2 全局运行时配置
CREATE TABLE IF NOT EXISTS `sys_file_setting`
(
    `id`                  bigint       NOT NULL COMMENT '主键ID',
    `setting_key`         varchar(100) NOT NULL COMMENT '配置键',
    `setting_value`       varchar(500) NULL DEFAULT NULL COMMENT '当前值',
    `value_type`          varchar(20)  NOT NULL DEFAULT 'string' COMMENT 'string/int/bool/json',
    `setting_group`       varchar(50)  NOT NULL COMMENT 'file/download/print/session',
    `setting_name`        varchar(100) NOT NULL COMMENT '配置名称',
    `description`         varchar(500) NULL DEFAULT NULL COMMENT '说明',
    `default_value`       varchar(500) NULL DEFAULT NULL COMMENT '默认值',
    `min_value`           varchar(50)  NULL DEFAULT NULL COMMENT '最小值',
    `max_value`           varchar(50)  NULL DEFAULT NULL COMMENT '最大值',
    `user_overridable`    tinyint      NOT NULL DEFAULT 0 COMMENT '是否允许用户文件级覆盖',
    `override_direction`  varchar(20)  NOT NULL DEFAULT 'LOCKED' COMMENT 'LOCKED/TIGHTEN_ONLY/FREE',
    `override_min`        varchar(50)  NULL DEFAULT NULL COMMENT '用户覆盖最小值',
    `override_max`        varchar(50)  NULL DEFAULT NULL COMMENT '用户覆盖最大值',
    `override_options`    varchar(500) NULL DEFAULT NULL COMMENT '用户覆盖枚举，逗号分隔',
    `override_hint`       varchar(200) NULL DEFAULT NULL COMMENT '覆盖提示',
    `editable`            tinyint      NOT NULL DEFAULT 1 COMMENT '是否可在管理端编辑',
    `version`             int          NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
    `create_by`           varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`         datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`         datetime     NULL DEFAULT NULL COMMENT '更新时间',
    `del_flag`            tinyint      NOT NULL DEFAULT 0 COMMENT '删除标识 0未删除 1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_setting_key` (`setting_key`, `del_flag`) USING BTREE,
    KEY `idx_setting_group` (`setting_group`) USING BTREE
) ENGINE = InnoDB COMMENT = '文件模块运行时配置' ROW_FORMAT = Dynamic;

-- 3.3 配置变更日志
CREATE TABLE IF NOT EXISTS `sys_file_config_log`
(
    `id`           bigint       NOT NULL COMMENT '主键ID',
    `scope`        varchar(20)  NOT NULL COMMENT 'GLOBAL/TYPE/USED/FILE',
    `target_id`    varchar(64)  NULL DEFAULT NULL COMMENT '文件ID或用途/后缀',
    `setting_key`  varchar(100) NOT NULL COMMENT '配置键',
    `old_value`    varchar(500) NULL DEFAULT NULL COMMENT '旧值',
    `new_value`    varchar(500) NULL DEFAULT NULL COMMENT '新值',
    `operator`     varchar(64)  NULL DEFAULT NULL COMMENT '操作人',
    `operator_ip`  varchar(64)  NULL DEFAULT NULL COMMENT '操作IP',
    `create_by`    varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime     NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_config_target` (`scope`, `target_id`) USING BTREE,
    KEY `idx_config_create_time` (`create_time`) USING BTREE
) ENGINE = InnoDB COMMENT = '文件配置变更日志' ROW_FORMAT = Dynamic;

-- ============================================================
-- 4. 初始化数据：文件用途
-- ============================================================
INSERT IGNORE INTO `sys_file_used`
(`id`, `used_code`, `used_name`, `description`, `built_in`, `enabled`, `sort`,
 `access_scope`, `public_allowed`, `download_allowed`, `print_allowed`,
 `config_version`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`)
VALUES
(1, 'temp',     '临时文件', '上传后未关联业务的临时文件', 1, 1, 10,
 'private', 0, 1, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(2, 'avatar',   '头像',     '用户头像等公开图片',         1, 1, 20,
 'public',  1, 1, 0, 0, 'system', NOW(), 'system', NOW(), 0),
(3, 'notice',   '通知附件', '通知公告附件',               1, 1, 30,
 'public',  1, 1, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(4, 'contract', '合同附件', '合同、协议等敏感文件',       1, 1, 40,
 'private', 0, 0, 0, 0, 'system', NOW(), 'system', NOW(), 0),
(5, 'document', '文档资料', '内部文档、资料',             1, 1, 50,
 'private', 0, 1, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(6, 'other',    '其他',     '未归类的业务附件',           1, 1, 99,
 'private', 0, 1, 1, 0, 'system', NOW(), 'system', NOW(), 0);

-- ============================================================
-- 5. 初始化数据：全局配置
-- 说明：
--   - user_overridable=1 的项允许上传人做文件级覆盖；
--   - TIGHTEN_ONLY 表示用户只能收紧，不能放宽；
--   - override_max 为空时，运行时以对应全局值为上限。
-- ============================================================
INSERT IGNORE INTO `sys_file_setting`
(`id`, `setting_key`, `setting_value`, `value_type`, `setting_group`, `setting_name`, `description`,
 `default_value`, `min_value`, `max_value`, `user_overridable`, `override_direction`,
 `override_min`, `override_max`, `override_options`, `override_hint`,
 `editable`, `version`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`)
VALUES
(1001, 'file.default-used', 'temp', 'string', 'file', '默认文件用途', '上传未指定用途时的默认值',
 'temp', NULL, NULL, 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),

(1010, 'download.default-allow', 'true', 'bool', 'download', '默认允许下载', '下载闸门兜底',
 'true', NULL, NULL, 1, 'TIGHTEN_ONLY', NULL, NULL, 'true,false', '只能关闭，不能打开管理员禁下的文件', 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1011, 'download.owner-override', 'true', 'bool', 'download', '上传人/管理员绕过下载闸门', '是否允许上传人/管理员绕过下载闸门',
 'true', NULL, NULL, 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1012, 'download.anonymous-public', 'false', 'bool', 'download', '匿名访问公开文件允许下载', '匿名用户访问公开文件时是否允许下载',
 'false', NULL, NULL, 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1013, 'download.public-logged-in', 'true', 'bool', 'download', '登录用户访问公开文件允许下载', '登录用户访问公开文件时是否允许下载',
 'true', NULL, NULL, 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1014, 'print.default-allow', 'true', 'bool', 'print', '默认允许打印', '打印闸门兜底',
 'true', NULL, NULL, 1, 'TIGHTEN_ONLY', NULL, NULL, 'true,false', '只能关闭打印，不能打开管理员禁打的文件', 1, 0, 'system', NOW(), 'system', NOW(), 0),

(1070, 'preview.session.ttl-minutes', '30', 'int', 'session', '登录预览会话 TTL', '登录用户会话滑动过期分钟数',
 '30', '5', '120', 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1071, 'preview.session.max-hours', '2', 'int', 'session', '预览会话绝对上限', '会话最长存活小时数',
 '2', '1', '24', 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0),
(1072, 'preview.anonymous.ttl-minutes', '10', 'int', 'session', '匿名会话 TTL', '匿名公开访问会话分钟数',
 '10', '5', '60', 0, 'LOCKED', NULL, NULL, NULL, NULL, 1, 0, 'system', NOW(), 'system', NOW(), 0);

-- ============================================================
-- 6. 初始化数据：文件后缀预览类型
-- ============================================================
INSERT IGNORE INTO `sys_file_type`
(`preview_type`, `preview_name`, `file_suffix`,
 `sort`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
('image', '图片', 'jpg',  10, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'jpeg', 11, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'png',  12, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'gif',  13, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'webp', 14, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'bmp',  15, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'tif',  16, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'tiff', 17, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'heic', 18, 0, 'system', NOW(), 'system', NOW()),
('image', '图片', 'svg',  19, 0, 'system', NOW(), 'system', NOW()),

('pdf', 'PDF', 'pdf', 30, 0, 'system', NOW(), 'system', NOW()),

('text', '文本', 'txt',  60, 0, 'system', NOW(), 'system', NOW()),
('text', '文本', 'md',   61, 0, 'system', NOW(), 'system', NOW()),
('text', '文本', 'log',  62, 0, 'system', NOW(), 'system', NOW()),
('text', '文本', 'csv',  63, 0, 'system', NOW(), 'system', NOW()),

('json', 'JSON', 'json', 70, 0, 'system', NOW(), 'system', NOW()),
('json', 'JSON', 'xml',  71, 0, 'system', NOW(), 'system', NOW()),
('json', 'JSON', 'yaml', 72, 0, 'system', NOW(), 'system', NOW()),
('json', 'JSON', 'yml',  73, 0, 'system', NOW(), 'system', NOW()),

('video', '视频', 'mp4',  80, 0, 'system', NOW(), 'system', NOW()),
('video', '视频', 'webm', 81, 0, 'system', NOW(), 'system', NOW()),
('video', '视频', 'mov',  82, 0, 'system', NOW(), 'system', NOW()),

('audio', '音频', 'mp3',  90, 0, 'system', NOW(), 'system', NOW()),
('audio', '音频', 'wav',  91, 0, 'system', NOW(), 'system', NOW()),
('audio', '音频', 'aac',  92, 0, 'system', NOW(), 'system', NOW()),
('audio', '音频', 'flac', 93, 0, 'system', NOW(), 'system', NOW()),
('audio', '音频', 'm4a',  94, 0, 'system', NOW(), 'system', NOW()),

('other', '其他', 'doc',  100, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'docx', 101, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'xls',  102, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'xlsx', 103, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'ppt',  104, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'pptx', 105, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'rtf',  106, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'odt',  107, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'ods',  108, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'odp',  109, 0, 'system', NOW(), 'system', NOW()),

('other', '其他', 'zip', 200, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', 'rar', 201, 0, 'system', NOW(), 'system', NOW()),
('other', '其他', '7z',  202, 0, 'system', NOW(), 'system', NOW());

-- 兜底默认行（file_suffix 为空）
INSERT IGNORE INTO `sys_file_type`
(`preview_type`, `preview_name`, `file_suffix`,
 `sort`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('other', '其他', NULL, 999, 0, 'system', NOW(), 'system', NOW());

-- ============================================================
-- 7. 验证查询
-- ============================================================
-- 7.1 用途
SELECT `used_code`, `used_name`, `access_scope`, `public_allowed`,
       `download_allowed`, `print_allowed`
FROM `sys_file_used`
WHERE `del_flag` = 0
ORDER BY `sort`;

-- 7.2 全局配置
SELECT `setting_group`, `setting_key`, `setting_value`, `default_value`, `user_overridable`, `override_direction`
FROM `sys_file_setting`
WHERE `del_flag` = 0
ORDER BY `setting_group`, `setting_key`;

-- 7.3 后缀配置
SELECT `file_suffix`, `preview_type`
FROM `sys_file_type`
WHERE `del_flag` = 0
ORDER BY `sort`
LIMIT 50;

-- ============================================================
-- 8. 历史能力清理：分享 / 转换 / 水印
-- 说明：
--   - 三项能力已从代码与文档中整体移除，本节清理真实库残留；
--   - 全部为幂等操作，重复执行无副作用；
--   - 如需保留历史数据，请先备份对应表。
-- ============================================================

-- 8.1 分享
DROP TABLE IF EXISTS `sys_file_share_receiver`;
DROP TABLE IF EXISTS `sys_file_share`;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_used` DROP COLUMN `share_allowed`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_used'
   AND COLUMN_NAME = 'share_allowed');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_used` DROP COLUMN `share_expire_days`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_used'
   AND COLUMN_NAME = 'share_expire_days');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_used` DROP COLUMN `share_max_access_count`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_used'
   AND COLUMN_NAME = 'share_max_access_count');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 8.2 转换
DROP TABLE IF EXISTS `sys_file_convert`;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_type` DROP COLUMN `convert_target`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_type'
   AND COLUMN_NAME = 'convert_target');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_used` DROP COLUMN `image_mode`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_used'
   AND COLUMN_NAME = 'image_mode');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_status` DROP COLUMN `convert_target`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_status'
   AND COLUMN_NAME = 'convert_target');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 8.3 水印
SET @sql = (SELECT IF(
    COUNT(*) > 0,
    'ALTER TABLE `sys_file_used` DROP COLUMN `watermark_level`',
    'SET @noop = 1'
) FROM information_schema.COLUMNS
 WHERE TABLE_SCHEMA = DATABASE()
   AND TABLE_NAME = 'sys_file_used'
   AND COLUMN_NAME = 'watermark_level');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 8.4 配置项（物理删除，不可恢复）
DELETE FROM `sys_file_setting`
WHERE `setting_group` IN ('share', 'convert', 'image', 'watermark');

-- 8.5 清理后校验：以下查询应返回 0 行
SELECT `setting_key` FROM `sys_file_setting`
WHERE `setting_key` LIKE 'share.%'
   OR `setting_key` LIKE 'convert.%'
   OR `setting_key` LIKE 'image.%'
   OR `setting_key` LIKE 'watermark.%';

-- ============================================================
-- 9. 回滚说明（仅开发环境）
-- ============================================================
-- 回滚会丢失新表数据，执行前务必备份。
-- DROP TABLE IF EXISTS `sys_file_config_log`;
-- DROP TABLE IF EXISTS `sys_file_setting`;
-- DROP TABLE IF EXISTS `sys_file_used`;
-- ALTER TABLE `sys_file_type`
--   DROP COLUMN `sort`;
-- ALTER TABLE `sys_file`
--   DROP COLUMN `file_config`,
--   DROP COLUMN `access_scope`;
-- 注意：第 8 节的删除不可回滚，需从备份恢复。

SET FOREIGN_KEY_CHECKS = 1;
