-- ============================================================
-- 文件用途 × 文件类型 授权关系表
--
-- 定位：
--   sys_file_used   承载业务策略（访问范围、公开能力、下载/打印闸门）
--   sys_file_type   承载技术策略（后缀 → 预览类型）
--   sys_file_used_type 承载两者的授权关系：某用途允许使用哪些文件类型（按后缀行逐条授权）
--
-- 说明：
--   1. 纯关系表，只有主键与创建审计字段，无 del_flag / update_by，关系变更直接物理增删；
--   2. 未配置（该用途在表中无记录）语义为「不限制」，与未引入本表时的行为一致；
--   3. 本期只做配置管理，上传链路不做运行时拦截；
--   4. 脚本幂等，可重复执行。
--
-- @author xht
-- ============================================================

CREATE TABLE IF NOT EXISTS `sys_file_used_type`
(
    `used_id`     bigint      NOT NULL COMMENT '用途id',
    `type_id`     bigint      NOT NULL COMMENT '文件类型id',
    `create_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `create_time` datetime                                                       DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`used_id`, `type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='系统管理-文件用途类型配置';

-- ============================================================
-- 验证查询
-- ============================================================

-- 1. 每个用途已授权的类型数量
SELECT u.`used_code`,
       u.`used_name`,
       COUNT(t.`type_id`) AS `type_count`
FROM `sys_file_used` u
         LEFT JOIN `sys_file_used_type` t ON t.`used_id` = u.`id`
GROUP BY u.`id`, u.`used_code`, u.`used_name`
ORDER BY u.`id`;

-- 2. 某用途的授权明细（按后缀展示）
-- SELECT t.`type_id`, ft.`file_suffix`, ft.`preview_type`
-- FROM `sys_file_used_type` t
--          JOIN `sys_file_type` ft ON ft.`id` = t.`type_id`
-- WHERE t.`used_id` = 1
-- ORDER BY ft.`sort`;

-- 3. 孤儿关系（类型已删除但授权残留）
SELECT t.`used_id`, t.`type_id`
FROM `sys_file_used_type` t
         LEFT JOIN `sys_file_type` ft ON ft.`id` = t.`type_id` AND ft.`del_flag` = 0
WHERE ft.`id` IS NULL;
