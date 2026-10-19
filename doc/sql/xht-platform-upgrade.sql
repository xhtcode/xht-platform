-- ----------------------------
-- 增量升级脚本
-- 适用场景：数据库已按旧版 xht-platform.sql 初始化过，不希望重建表时执行
-- 全新初始化的库请直接执行 xht-platform.sql，无需执行本文件
-- ----------------------------

-- 2026-10-17：b_log 补齐 title / log_desc 与常用查询索引
-- 背景：BLogEntity 已映射 title、log_desc 两列，但旧 DDL 缺失，
--       落库时报 Unknown column 'title' in 'field list'，且异常被切面 catch 吞掉，
--       表现为「操作日志一条都不入库」。
ALTER TABLE `b_log`
    ADD COLUMN `title` varchar(128) DEFAULT NULL COMMENT '日志名称（@BLog.value）' AFTER `id`,
    ADD COLUMN `log_desc` varchar(255) DEFAULT NULL COMMENT '日志描述（@BLog.description）' AFTER `title`,
    ADD KEY `idx_b_log_execute_time` (`execute_time`),
    ADD KEY `idx_b_log_trace_id` (`trace_id`),
    ADD KEY `idx_b_log_service_name` (`service_name`);
