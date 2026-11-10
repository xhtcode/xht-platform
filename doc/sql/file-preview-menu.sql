-- =====================================================================
-- 文件模块菜单脚本（文件用途 / 全局配置 / 分享中心 / 预览类型按钮）
-- 适用：xht-platform（MySQL 8.0+）
-- 执行前请确认已执行 file-preview-v2.sql（表结构与配置项已就绪）
-- 特点：可重复执行（ON DUPLICATE KEY UPDATE），不会产生重复菜单
-- =====================================================================

-- ---------------------------------------------------------------------
-- 0. 父级菜单：基础工具（/basic）
--    已存在则复用；不存在时落到根节点（parent_id = 0），执行后请手动调整
-- ---------------------------------------------------------------------
SET @parent_id := (SELECT id FROM `sys_menu`
                   WHERE `menu_path` = '/basic' AND `parent_id` = 0 AND `del_flag` = 0
                   LIMIT 1);
SET @parent_id := IFNULL(@parent_id, 0);

SELECT @parent_id AS `父级菜单ID（基础工具）`;


-- ---------------------------------------------------------------------
-- 1. 文件用途管理（C）
-- ---------------------------------------------------------------------
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000001, @parent_id, 'C', '文件用途管理', 'i-menu-tag', '/basic/file-used',
        1, 1, 0, NULL, 2,
        'FileUsedViewIndex', '/views/system/file-used/index.vue', '/basic/file-used', 0, 0,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_icon` = VALUES(`menu_icon`), `menu_path` = VALUES(`menu_path`),
                        `menu_hidden` = VALUES(`menu_hidden`), `menu_cache` = VALUES(`menu_cache`),
                        `menu_status` = VALUES(`menu_status`), `menu_sort` = VALUES(`menu_sort`),
                        `view_name` = VALUES(`view_name`), `view_path` = VALUES(`view_path`),
                        `active_menu_path` = VALUES(`active_menu_path`), `del_flag` = 0,
                        `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000002, 2120000000000000001, 'B', '用途增加', NULL, NULL,
        NULL, NULL, 0, 'sys:file:used:create', 1,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000003, 2120000000000000001, 'B', '用途修改', NULL, NULL,
        NULL, NULL, 0, 'sys:file:used:update', 2,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000004, 2120000000000000001, 'B', '用途删除', NULL, NULL,
        NULL, NULL, 0, 'sys:file:used:remove', 3,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000005, 2120000000000000001, 'B', '用途停用', NULL, NULL,
        NULL, NULL, 0, 'sys:file:used:disable', 4,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();


-- ---------------------------------------------------------------------
-- 2. 文件全局配置（C）
-- ---------------------------------------------------------------------
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000011, @parent_id, 'C', '文件全局配置', 'i-menu-setting', '/basic/file-setting',
        1, 1, 0, NULL, 3,
        'FileSettingViewIndex', '/views/system/file-setting/index.vue', '/basic/file-setting', 0, 0,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_icon` = VALUES(`menu_icon`), `menu_path` = VALUES(`menu_path`),
                        `menu_hidden` = VALUES(`menu_hidden`), `menu_cache` = VALUES(`menu_cache`),
                        `menu_status` = VALUES(`menu_status`), `menu_sort` = VALUES(`menu_sort`),
                        `view_name` = VALUES(`view_name`), `view_path` = VALUES(`view_path`),
                        `active_menu_path` = VALUES(`active_menu_path`), `del_flag` = 0,
                        `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000012, 2120000000000000011, 'B', '配置修改', NULL, NULL,
        NULL, NULL, 0, 'sys:file:setting:update', 1,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000013, 2120000000000000011, 'B', '配置重置', NULL, NULL,
        NULL, NULL, 0, 'sys:file:setting:reset', 2,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();


-- ---------------------------------------------------------------------
-- 3. 文件分享中心（C）：我分享的 / 分享给我的
--    面向全部登录用户，需要分配给「普通用户」角色
-- ---------------------------------------------------------------------
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000021, @parent_id, 'C', '文件分享中心', 'i-menu-share', '/basic/file-share',
        1, 1, 0, NULL, 4,
        'FileShareViewIndex', '/views/system/file-share/index.vue', '/basic/file-share', 0, 0,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_icon` = VALUES(`menu_icon`), `menu_path` = VALUES(`menu_path`),
                        `menu_hidden` = VALUES(`menu_hidden`), `menu_cache` = VALUES(`menu_cache`),
                        `menu_status` = VALUES(`menu_status`), `menu_sort` = VALUES(`menu_sort`),
                        `view_name` = VALUES(`view_name`), `view_path` = VALUES(`view_path`),
                        `active_menu_path` = VALUES(`active_menu_path`), `del_flag` = 0,
                        `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000022, 2120000000000000021, 'B', '分享撤销', NULL, NULL,
        NULL, NULL, 0, 'sys:file:share:remove', 1,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000023, 2120000000000000021, 'B', '提取码重置', NULL, NULL,
        NULL, NULL, 0, 'sys:file:share:reset-code', 2,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();


-- ---------------------------------------------------------------------
-- 4. 文件预览类型（已存在的菜单，修正路由 + 补按钮）
--    原 menu_path 误填为视图路径 /system/file-type/index.vue
-- ---------------------------------------------------------------------
UPDATE `sys_menu`
SET `menu_path`        = '/basic/file-type',
    `active_menu_path` = '/basic/file-type',
    `menu_icon`        = 'i-menu-model',
    `menu_sort`        = 5,
    `del_flag`         = 0,
    `update_by`        = '系统生成',
    `update_time`      = NOW()
WHERE `id` = 2117856162048143361;

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000032, 2117856162048143361, 'B', '预览类型增加', NULL, NULL,
        NULL, NULL, 0, 'sys:file:type:create', 1,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000033, 2117856162048143361, 'B', '预览类型修改', NULL, NULL,
        NULL, NULL, 0, 'sys:file:type:update', 2,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();

INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`,
                        `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`,
                        `view_name`, `view_path`, `active_menu_path`, `affix_status`, `frame_flag`,
                        `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES (2120000000000000034, 2117856162048143361, 'B', '预览类型删除', NULL, NULL,
        NULL, NULL, 0, 'sys:file:type:remove', 3,
        NULL, NULL, NULL, NULL, NULL,
        0, '系统生成', NOW(), '系统生成', NOW())
ON DUPLICATE KEY UPDATE `parent_id` = VALUES(`parent_id`), `menu_name` = VALUES(`menu_name`),
                        `menu_authority` = VALUES(`menu_authority`), `menu_sort` = VALUES(`menu_sort`),
                        `del_flag` = 0, `update_by` = '系统生成', `update_time` = NOW();


-- ---------------------------------------------------------------------
-- 5. 角色授权（可选）
--    系统管理员（role_id=1）：全部新增菜单
--    普通用户（role_id=2）：仅文件分享中心（含其按钮）
--    说明：管理员若走超管逻辑可跳过本段；按需执行
-- ---------------------------------------------------------------------
INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`, `create_by`, `create_time`)
SELECT 1, `id`, '系统生成', NOW()
FROM `sys_menu`
WHERE `id` IN (2120000000000000001, 2120000000000000002, 2120000000000000003,
               2120000000000000004, 2120000000000000005,
               2120000000000000011, 2120000000000000012, 2120000000000000013,
               2120000000000000021, 2120000000000000022, 2120000000000000023,
               2120000000000000032, 2120000000000000033, 2120000000000000034);

INSERT IGNORE INTO `sys_role_menu` (`role_id`, `menu_id`, `create_by`, `create_time`)
SELECT 2, `id`, '系统生成', NOW()
FROM `sys_menu`
WHERE `id` IN (2120000000000000021, 2120000000000000022, 2120000000000000023);


-- ---------------------------------------------------------------------
-- 6. 执行结果校验
-- ---------------------------------------------------------------------
SELECT m.`id`, m.`parent_id`, m.`menu_type`, m.`menu_name`, m.`menu_path`,
       m.`view_name`, m.`view_path`, m.`menu_authority`, m.`menu_sort`
FROM `sys_menu` m
WHERE m.`id` IN (2120000000000000001, 2120000000000000002, 2120000000000000003,
                 2120000000000000004, 2120000000000000005,
                 2120000000000000011, 2120000000000000012, 2120000000000000013,
                 2120000000000000021, 2120000000000000022, 2120000000000000023,
                 2117856162048143361, 2120000000000000032, 2120000000000000033,
                 2120000000000000034)
ORDER BY m.`parent_id`, m.`menu_sort`;

-- ---------------------------------------------------------------------
-- 7. 回滚（按需执行）
-- ---------------------------------------------------------------------
-- DELETE FROM `sys_role_menu` WHERE `menu_id` IN (2120000000000000001, 2120000000000000002,
--   2120000000000000003, 2120000000000000004, 2120000000000000005, 2120000000000000011,
--   2120000000000000012, 2120000000000000013, 2120000000000000021, 2120000000000000022,
--   2120000000000000023, 2120000000000000032, 2120000000000000033, 2120000000000000034);
-- DELETE FROM `sys_menu` WHERE `id` IN (2120000000000000001, 2120000000000000002,
--   2120000000000000003, 2120000000000000004, 2120000000000000005, 2120000000000000011,
--   2120000000000000012, 2120000000000000013, 2120000000000000021, 2120000000000000022,
--   2120000000000000023, 2120000000000000032, 2120000000000000033, 2120000000000000034);
-- UPDATE `sys_menu` SET `menu_path` = '/system/file-type/index.vue',
--        `active_menu_path` = '/system/file-type/index.vue'
-- WHERE `id` = 2117856162048143361;
