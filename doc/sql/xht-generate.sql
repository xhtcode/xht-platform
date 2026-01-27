SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;
USE
`xht_platform`;

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_source`;
CREATE TABLE `gen_data_source`
(
    `id`             bigint       NOT NULL COMMENT '数据源ID',
    `name`           varchar(100) NOT NULL COMMENT '数据源名称',
    `db_type`        varchar(20)  NOT NULL COMMENT '数据库类型(MySQL/Oracle)',
    `url`            varchar(200) NOT NULL COMMENT '数据库地址',
    `username`       varchar(100) NULL DEFAULT NULL COMMENT '数据库用户名',
    `password`       varchar(100) NULL DEFAULT NULL COMMENT '数据库密码',
    `test_result`    varchar(20) NULL DEFAULT NULL COMMENT '连接测试结果(success/fail)',
    `last_test_time` datetime NULL DEFAULT NULL COMMENT '最后测试时间',
    `del_flag`       tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '代码生成器-数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `id`                bigint      NOT NULL COMMENT '表ID',
    `group_id`          bigint      NOT NULL COMMENT '分组id',
    `data_source_id`    bigint      NOT NULL COMMENT '数据源ID',
    `data_base_type`    varchar(10) NULL DEFAULT NULL COMMENT '数据库类型',
    `engine_name`       varchar(100) NULL DEFAULT NULL COMMENT '引擎名称',
    `table_name`        varchar(50) NOT NULL COMMENT '数据库表名',
    `table_comment`     varchar(500) NULL DEFAULT NULL COMMENT '表注释',
    `module_name`       varchar(10) NULL DEFAULT NULL COMMENT '模块名称',
    `service_name`      varchar(10) NOT NULL COMMENT '业务名称',
    `code_name`         varchar(50) NULL DEFAULT NULL COMMENT '代码名称',
    `code_comment`      varchar(500) NULL DEFAULT NULL COMMENT '代码注释',
    `back_end_author`   varchar(64) NULL DEFAULT NULL COMMENT '后端作者',
    `front_end_author`  varchar(64) NULL DEFAULT NULL COMMENT '前端作者',
    `url_prefix`        varchar(100) NULL DEFAULT NULL COMMENT '请求前缀',
    `permission_prefix` varchar(100) NULL DEFAULT NULL COMMENT '权限前缀',
    `parent_menu_id`    bigint NULL DEFAULT NULL COMMENT '上级菜单',
    `menu_icon`         varchar(50)  DEFAULT NULL COMMENT '菜单图标',
    `menu_path`         varchar(100) DEFAULT NULL COMMENT '菜单地址',
    `page_style`        varchar(50) NULL DEFAULT NULL COMMENT '页面风格',
    `page_style_width`  varchar(20) NULL DEFAULT NULL COMMENT '页面宽度',
    `from_number`       int NULL DEFAULT 0 COMMENT '每行数量',
    `table_create_time` datetime    NOT NULL COMMENT '表创建时间',
    `table_update_time` datetime NULL DEFAULT NULL COMMENT '表更新时间',
    `create_by`         varchar(65) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(65) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '代码生成器-表结构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `id`                bigint       NOT NULL COMMENT '主键',
    `table_id`          bigint       NOT NULL COMMENT '表id',
    `table_name`        varchar(100) NOT NULL COMMENT '表名称(冗余字段)',
    `db_name`           varchar(100) NOT NULL COMMENT '字段名',
    `db_type`           varchar(255) NULL DEFAULT NULL COMMENT '字段类型',
    `db_primary`        tinyint      NOT NULL DEFAULT 0 COMMENT '字段主键：0-非主键，1-主键',
    `db_required`       tinyint      NOT NULL DEFAULT 0 COMMENT '字段必填：0-非必填，1-必填',
    `db_comment`        varchar(500) NULL DEFAULT NULL COMMENT '字段注释',
    `db_length`         int NULL DEFAULT NULL COMMENT '字段长度',
    `code_name`         varchar(120) NULL DEFAULT NULL COMMENT '代码名称',
    `code_comment`      varchar(200) NULL DEFAULT NULL COMMENT '代码注释',
    `from_insert`       tinyint      NOT NULL DEFAULT 1 COMMENT '表单新增：0-不显示，1-显示',
    `from_update`       tinyint      NOT NULL DEFAULT 1 COMMENT '表单更新：0-不显示，1-显示',
    `from_length`       int NULL DEFAULT NULL COMMENT '表单输入长度',
    `from_fill`         tinyint      NOT NULL DEFAULT 0 COMMENT '表单必填：0-非必填，1-必填',
    `from_component`    varchar(50) NULL DEFAULT NULL COMMENT '表单组件',
    `list_show`         tinyint      NOT NULL DEFAULT 1 COMMENT '列表显示：0-不显示，1-显示',
    `list_disabled`     tinyint      NOT NULL DEFAULT 0 COMMENT '显示切换禁用：0-不禁用，1-禁用',
    `list_hidden`       tinyint      NOT NULL DEFAULT 0 COMMENT '默认隐藏：0-不隐藏，1-隐藏',
    `list_sortable`     tinyint      NOT NULL COMMENT '字段排序',
    `code_java`         varchar(50) NULL DEFAULT NULL COMMENT 'java类型',
    `code_java_package` varchar(100) NULL DEFAULT NULL COMMENT 'java类型 包地址',
    `code_ts`           varchar(50) NULL DEFAULT NULL COMMENT 'ts类型',
    `dict_code`         varchar(100) NULL DEFAULT NULL COMMENT '字典编码',
    `sort_order`        int          NOT NULL DEFAULT 0 COMMENT '字段排序',
    `create_by`         varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '表结构元数据信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_column_query
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column_query`;
CREATE TABLE `gen_table_column_query`
(
    `id`              bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `table_id`        bigint       NOT NULL COMMENT '表id',
    `table_name`      varchar(100) NOT NULL COMMENT '表名称(冗余字段)',
    `column_id`       bigint NULL DEFAULT NULL COMMENT '字段id',
    `column_name`     varchar(100) NULL DEFAULT NULL COMMENT '字段名称',
    `from_length`     int NULL DEFAULT NULL COMMENT '表单输入长度',
    `query_type`      varchar(50) NULL DEFAULT NULL COMMENT '查询类型（如等于、不等于、大于、小于等）',
    `condition_label` varchar(100) NULL DEFAULT NULL COMMENT '条件标签（显示用的文本）',
    `condition_value` varchar(255) NULL DEFAULT NULL COMMENT '字段值（条件值）',
    `from_component`  varchar(50) NULL DEFAULT NULL COMMENT '表单组件',
    `dict_code`       varchar(100) NULL DEFAULT NULL COMMENT '字典编码',
    `sort_order`      int NULL DEFAULT 0 COMMENT '字段排序',
    `create_by`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 COMMENT = '代码生成器-查询条件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template`
(
    `id`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `group_id`              bigint NULL DEFAULT NULL COMMENT '分组id',
    `template_name`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '模板名称',
    `template_content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板内容',
    `template_file_path`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径模板',
    `template_file_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名模板',
    `template_file_type`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '文件类型',
    `template_sort`         int NULL DEFAULT NULL COMMENT '模板排序',
    `template_ignore_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '忽略的字段,逗号分割',
    `del_flag`              tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
    `create_time`           datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
    `update_time`           datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成器-代码生成模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_template_group
-- ----------------------------
DROP TABLE IF EXISTS `gen_template_group`;
CREATE TABLE `gen_template_group`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT '分组ID',
    `group_name`     varchar(100) NOT NULL COMMENT '分组名称',
    `template_count` int NULL DEFAULT NULL COMMENT '模板数量',
    `group_desc`     varchar(500) NULL DEFAULT NULL COMMENT '分组描述',
    `group_sort`     int NULL DEFAULT NULL COMMENT '模板排序',
    `del_flag`       tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '代码生成器-模板分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`
(
    `id`              bigint      NOT NULL COMMENT '映射ID',
    `db_type`         varchar(20) NOT NULL COMMENT '数据库类型',
    `db_data_type`    varchar(50) NOT NULL COMMENT '数据库数据类型',
    `target_language` varchar(50) NOT NULL COMMENT '目标编程语言',
    `java_type`       varchar(50) NOT NULL COMMENT 'java数据类型',
    `import_package`  varchar(255) NULL DEFAULT NULL COMMENT '导入包路径',
    `ts_type`         varchar(50) NULL DEFAULT NULL COMMENT 'ts数据类型',
    `del_flag`        tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '代码生成器-数据类型映射表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
