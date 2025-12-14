/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80404 (8.4.4)
 Source Host           : localhost:3306
 Source Schema         : xht_platform

 Target Server Type    : MySQL
 Target Server Version : 80404 (8.4.4)
 File Encoding         : 65001

 Date: 11/12/2025 19:18:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`
(
    `id`               bigint                                                        NOT NULL COMMENT '映射ID',
    `db_type`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '数据库类型',
    `db_data_type`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '数据库数据类型',
    `target_language`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '目标编程语言',
    `target_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '目标语言数据类型',
    `import_package`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导入包路径',
    `del_flag`         tinyint                                                       NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-数据类型映射表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_type_mapping
-- ----------------------------
INSERT INTO `gen_type_mapping`
VALUES (1, 'MySql', 'tinyint', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (2, 'MySql', 'smallint', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (3, 'MySql', 'mediumint', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (4, 'MySql', 'int', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin', '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (5, 'MySql', 'bigint', 'Java', 'Long', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin', '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (6, 'MySql', 'decimal', 'Java', 'BigDecimal', 'java.math.BigDecimal', 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (7, 'MySql', 'numeric', 'Java', 'BigDecimal', 'java.math.BigDecimal', 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (8, 'MySql', 'float', 'Java', 'Float', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin', '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (9, 'MySql', 'double', 'Java', 'Double', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin',
        '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (10, 'MySql', 'bit', 'Java', 'Boolean', NULL, 0, 'admin', '2025-09-15 14:46:09', 'admin', '2025-09-15 14:46:09');
INSERT INTO `gen_type_mapping`
VALUES (11, 'MySql', 'date', 'Java', 'LocalDate', 'java.time.LocalDate', 0, 'admin', '2025-09-15 14:46:10', 'admin',
        '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (12, 'MySql', 'time', 'Java', 'LocalTime', 'java.time.LocalTime', 0, 'admin', '2025-09-15 14:46:10', 'admin',
        '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (13, 'MySql', 'datetime', 'Java', 'LocalDateTime', 'java.time.LocalDateTime', 0, 'admin', '2025-09-15 14:46:10',
        'admin', '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (14, 'MySql', 'timestamp', 'Java', 'LocalDateTime', 'java.time.LocalDateTime', 0, 'admin', '2025-09-15 14:46:10',
        'admin', '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (15, 'MySql', 'year', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:10', 'admin',
        '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (16, 'MySql', 'char', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:10', 'admin', '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (17, 'MySql', 'varchar', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:10', 'admin',
        '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (18, 'MySql', 'tinytext', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:10', 'admin',
        '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (19, 'MySql', 'text', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:10', 'admin', '2025-09-15 14:46:10');
INSERT INTO `gen_type_mapping`
VALUES (20, 'MySql', 'mediumtext', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (21, 'MySql', 'longtext', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (22, 'MySql', 'binary', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (23, 'MySql', 'varbinary', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (24, 'MySql', 'tinyblob', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (25, 'MySql', 'blob', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin', '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (26, 'MySql', 'mediumblob', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (27, 'MySql', 'longblob', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (28, 'MySql', 'json', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin', '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (29, 'MySql', 'enum', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin', '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (30, 'MySql', 'set', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin', '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (31, 'MySql', 'tinyint', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (32, 'MySql', 'smallint', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (33, 'MySql', 'mediumint', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (34, 'MySql', 'int', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:11', 'admin',
        '2025-09-15 14:46:11');
INSERT INTO `gen_type_mapping`
VALUES (35, 'MySql', 'bigint', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (36, 'MySql', 'decimal', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (37, 'MySql', 'numeric', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (38, 'MySql', 'float', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (39, 'MySql', 'double', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (40, 'MySql', 'bit', 'TypeScript', 'boolean', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (41, 'MySql', 'date', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (42, 'MySql', 'time', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (43, 'MySql', 'datetime', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (44, 'MySql', 'timestamp', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (45, 'MySql', 'year', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (46, 'MySql', 'char', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (47, 'MySql', 'varchar', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (48, 'MySql', 'tinytext', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:12', 'admin',
        '2025-09-15 14:46:12');
INSERT INTO `gen_type_mapping`
VALUES (49, 'MySql', 'text', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (50, 'MySql', 'mediumtext', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (51, 'MySql', 'longtext', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (52, 'MySql', 'binary', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (53, 'MySql', 'varbinary', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (54, 'MySql', 'tinyblob', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (55, 'MySql', 'blob', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (56, 'MySql', 'mediumblob', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (57, 'MySql', 'longblob', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (58, 'MySql', 'json', 'TypeScript', 'object', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (59, 'MySql', 'enum', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (60, 'MySql', 'set', 'TypeScript', 'string[]', NULL, 0, 'admin', '2025-09-15 14:46:13', 'admin',
        '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (91, 'Oracle', 'NUMBER', 'Java', 'BigDecimal', 'java.math.BigDecimal', 0, 'admin', '2025-09-15 14:46:13',
        'admin', '2025-09-15 14:46:13');
INSERT INTO `gen_type_mapping`
VALUES (92, 'Oracle', 'BINARY_FLOAT', 'Java', 'Float', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (93, 'Oracle', 'BINARY_DOUBLE', 'Java', 'Double', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (94, 'Oracle', 'FLOAT', 'Java', 'Double', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (95, 'Oracle', 'INTEGER', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (96, 'Oracle', 'INT', 'Java', 'Integer', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (97, 'Oracle', 'SMALLINT', 'Java', 'Short', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (98, 'Oracle', 'DECIMAL', 'Java', 'BigDecimal', 'java.math.BigDecimal', 0, 'admin', '2025-09-15 14:46:14',
        'admin', '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (99, 'Oracle', 'NUMERIC', 'Java', 'BigDecimal', 'java.math.BigDecimal', 0, 'admin', '2025-09-15 14:46:14',
        'admin', '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (100, 'Oracle', 'REAL', 'Java', 'Float', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (101, 'Oracle', 'CHAR', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (102, 'Oracle', 'VARCHAR2', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (103, 'Oracle', 'VARCHAR', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (104, 'Oracle', 'NCHAR', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (105, 'Oracle', 'NVARCHAR2', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (106, 'Oracle', 'LONG', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:14', 'admin',
        '2025-09-15 14:46:14');
INSERT INTO `gen_type_mapping`
VALUES (107, 'Oracle', 'CLOB', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (108, 'Oracle', 'NCLOB', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (109, 'Oracle', 'BLOB', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (110, 'Oracle', 'BFILE', 'Java', 'File', 'java.io.File', 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (111, 'Oracle', 'RAW', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (112, 'Oracle', 'LONG RAW', 'Java', 'byte[]', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (113, 'Oracle', 'DATE', 'Java', 'LocalDate', 'java.time.LocalDate', 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (114, 'Oracle', 'TIMESTAMP', 'Java', 'LocalDateTime', 'java.time.LocalDateTime', 0, 'admin',
        '2025-09-15 14:46:15', 'admin', '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (115, 'Oracle', 'TIMESTAMP WITH TIME ZONE', 'Java', 'ZonedDateTime', 'java.time.ZonedDateTime', 0, 'admin',
        '2025-09-15 14:46:15', 'admin', '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (116, 'Oracle', 'TIMESTAMP WITH LOCAL TIME ZONE', 'Java', 'LocalDateTime', 'java.time.LocalDateTime', 0, 'admin',
        '2025-09-15 14:46:15', 'admin', '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (117, 'Oracle', 'INTERVAL YEAR TO MONTH', 'Java', 'Period', 'java.time.Period', 0, 'admin',
        '2025-09-15 14:46:15', 'admin', '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (118, 'Oracle', 'INTERVAL DAY TO SECOND', 'Java', 'Duration', 'java.time.Duration', 0, 'admin',
        '2025-09-15 14:46:15', 'admin', '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (119, 'Oracle', 'ROWID', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (120, 'Oracle', 'UROWID', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (121, 'Oracle', 'XMLType', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:15', 'admin',
        '2025-09-15 14:46:15');
INSERT INTO `gen_type_mapping`
VALUES (122, 'Oracle', 'SDO_GEOMETRY', 'Java', 'Geometry', 'org.locationtech.jts.geom.Geometry', 0, 'admin',
        '2025-09-15 14:46:16', 'admin', '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (123, 'Oracle', 'BOOLEAN', 'Java', 'Boolean', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (124, 'Oracle', 'JSON', 'Java', 'String', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (125, 'Oracle', 'NUMBER', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (126, 'Oracle', 'BINARY_FLOAT', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (127, 'Oracle', 'BINARY_DOUBLE', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (128, 'Oracle', 'FLOAT', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (129, 'Oracle', 'INTEGER', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (130, 'Oracle', 'INT', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (131, 'Oracle', 'SMALLINT', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (132, 'Oracle', 'DECIMAL', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (133, 'Oracle', 'NUMERIC', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (134, 'Oracle', 'REAL', 'TypeScript', 'number', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (135, 'Oracle', 'CHAR', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:16', 'admin',
        '2025-09-15 14:46:16');
INSERT INTO `gen_type_mapping`
VALUES (136, 'Oracle', 'VARCHAR2', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (137, 'Oracle', 'VARCHAR', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (138, 'Oracle', 'NCHAR', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (139, 'Oracle', 'NVARCHAR2', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (140, 'Oracle', 'LONG', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (141, 'Oracle', 'CLOB', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (142, 'Oracle', 'NCLOB', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (143, 'Oracle', 'BLOB', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (144, 'Oracle', 'BFILE', 'TypeScript', 'File', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (145, 'Oracle', 'RAW', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (146, 'Oracle', 'LONG RAW', 'TypeScript', 'Uint8Array', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (147, 'Oracle', 'DATE', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (148, 'Oracle', 'TIMESTAMP', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:17', 'admin',
        '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (149, 'Oracle', 'TIMESTAMP WITH TIME ZONE', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:17',
        'admin', '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (150, 'Oracle', 'TIMESTAMP WITH LOCAL TIME ZONE', 'TypeScript', 'Date', NULL, 0, 'admin', '2025-09-15 14:46:17',
        'admin', '2025-09-15 14:46:17');
INSERT INTO `gen_type_mapping`
VALUES (151, 'Oracle', 'INTERVAL YEAR TO MONTH', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:18',
        'admin', '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (152, 'Oracle', 'INTERVAL DAY TO SECOND', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:18',
        'admin', '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (153, 'Oracle', 'ROWID', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (154, 'Oracle', 'UROWID', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (155, 'Oracle', 'XMLType', 'TypeScript', 'string', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (156, 'Oracle', 'SDO_GEOMETRY', 'TypeScript', 'object', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (157, 'Oracle', 'BOOLEAN', 'TypeScript', 'boolean', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');
INSERT INTO `gen_type_mapping`
VALUES (158, 'Oracle', 'JSON', 'TypeScript', 'object', NULL, 0, 'admin', '2025-09-15 14:46:18', 'admin',
        '2025-09-15 14:46:18');

-- ----------------------------
-- Table structure for gen_template_group
-- ----------------------------
DROP TABLE IF EXISTS `gen_template_group`;
CREATE TABLE `gen_template_group`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '分组ID',
    `group_name`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称',
    `template_count` int                                                           NULL DEFAULT NULL COMMENT '模板数量',
    `group_desc`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组描述',
    `group_sort`     int                                                           NULL DEFAULT NULL COMMENT '模板排序',
    `del_flag`       tinyint                                                       NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-模板分组表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_template_group
-- ----------------------------
INSERT INTO `gen_template_group`
VALUES (1, '基础模板', 5, '最基础的功能单表', 1, 0, 'anonymity', '2025-11-12 15:36:21', 'anonymity',
        '2025-11-12 15:36:21');

-- ----------------------------
-- Table structure for gen_template
-- ----------------------------
DROP TABLE IF EXISTS `gen_template`;
CREATE TABLE `gen_template`
(
    `id`                    bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `group_id`              bigint                                                        NULL DEFAULT NULL COMMENT '分组id',
    `template_name`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '模板名称',
    `template_content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         NOT NULL COMMENT '模板内容',
    `template_file_path`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径模板',
    `template_file_name`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名模板',
    `template_file_type`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '文件类型',
    `template_sort`         int                                                           NULL DEFAULT NULL COMMENT '模板排序',
    `template_ignore_field` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '忽略的字段,逗号分割',
    `del_flag`              tinyint                                                       NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建人',
    `create_time`           datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新人',
    `update_time`           datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成器-代码生成模板表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_template
-- ----------------------------
INSERT INTO `gen_template`
VALUES (1, 1, 'Entity.java',
        'package ${packageName}.modules.${moduleName}.domain.entity;\r\n\r\nimport com.baomidou.mybatisplus.annotation.IdType;\r\nimport com.baomidou.mybatisplus.annotation.TableField;\r\nimport com.baomidou.mybatisplus.annotation.TableId;\r\nimport com.baomidou.mybatisplus.annotation.TableName;\r\nimport com.xht.framework.mybatis.domain.entity.BasicEntity;\r\nimport lombok.Data;\r\n\r\nimport java.io.Serializable;\r\n\r\n/**\r\n * ${codeComment}\r\n *\r\n * @author ${backEndAuthor}\r\n */\r\n@Data\r\n@TableName(value = \"sys_role\")\r\npublic class ${codeNameUpperFirst}Entity extends BasicEntity implements Serializable {\r\n\r\n    /**\r\n     * ${pkColumn.dbComment}\r\n     */\r\n    @TableId(value = \"${pkColumn.dbName}\", type = IdType.ASSIGN_ID)\r\n    private ${pkColumn.codeJava} ${pkColumn.codeName};\r\n\r\n#foreach ($column in ${columns})\r\n#if($column.dbPrimary==0)\r\n    /**\r\n     * ${column.dbComment}\r\n     */\r\n    @TableField(value = \"${column.dbName}\")\r\n    private ${column.codeJava} ${column.codeName};\r\n    \r\n#end\r\n#end\r\n}\r\n',
        '/java/${packageName}/${moduleName}/domain/entity/', '${codeNameUpperFirst}Entity', 'java', 1,
        '[\"create_by\",\"create_time\",\"update_by\",\"update_time\",\"del_flag\"]', 0, 'anonymity',
        '2025-11-12 16:38:31', 'anonymity', '2025-11-12 16:38:31');
INSERT INTO `gen_template`
VALUES (2, 1, 'Form.java',
        'package ${packageName}.modules.${moduleName}.domain.form;\r\n\r\nimport com.xht.framework.core.domain.form.BasicForm;\r\nimport com.xht.framework.web.validation.Groups;\r\nimport io.swagger.v3.oas.annotations.media.Schema;\r\nimport jakarta.validation.constraints.NotEmpty;\r\nimport jakarta.validation.constraints.NotNull;\r\nimport jakarta.validation.constraints.Null;\r\nimport jakarta.validation.constraints.Positive;\r\nimport lombok.Data;\r\n\r\n/**\r\n * ${codeComment}表单请求参数\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Data\r\n@Schema(description = \"${codeComment}表单请求参数\")\r\npublic class ${codeNameUpperFirst}Form extends BasicForm {\r\n\r\n    /**\r\n     * ${pkColumn.dbComment}\r\n     */\r\n    @Null(message = \"${pkColumn.codeComment}必须为空\", groups = {Groups.Create.class})\r\n    @NotNull(message = \"${pkColumn.codeComment}参数不合法\", groups = {Groups.Update.class})\r\n    @Positive(message = \"${pkColumn.codeComment}参数不合法\", groups = {Groups.Update.class})\r\n    @Schema(description = \"${pkColumn.codeComment}\")\r\n    private ${pkColumn.codeJava} ${pkColumn.codeName};\r\n\r\n#foreach ($column in ${formColumns})\r\n#if($column.dbPrimary==0)\r\n    /**\r\n     * ${column.dbComment}\r\n     */\r\n#if($column.codeJava==\'String\')   \r\n    @NotEmpty(message = \"${column.codeComment}参数不合法\", groups = {#if($column.fromInsert==1)Groups.Create.class#end#if($column.fromInsert==1 && $column.fromUpdate==1), #end#if($column.fromUpdate==1)Groups.Update.class#end})\r\n#end    \r\n#if($column.codeJava!=\'String\')       \r\n    @NotNull(message = \"${column.codeComment}参数不合法\", groups = {#if($column.fromInsert==1)Groups.Create.class#end#if($column.fromInsert==1 && $column.fromUpdate==1), #end#if($column.fromUpdate==1)Groups.Update.class#end})\r\n#end    \r\n    @Schema(description = \"${column.codeComment}\")\r\n    private ${column.codeJava} ${column.codeName};\r\n    \r\n#end\r\n#end    \r\n}\r\n',
        '/java/${packageName}/${moduleName}/domain/form/', '${codeNameUpperFirst}Form', 'java', 2,
        '[\"create_time\",\"create_by\",\"update_by\",\"update_time\",\"version\",\"del_flag\"]', 0, 'anonymity',
        '2025-11-12 16:49:31', 'anonymity', '2025-11-12 16:49:31');
INSERT INTO `gen_template`
VALUES (3, 1, 'Query.java',
        'package ${packageName}.modules.${moduleName}.domain.query;\r\n\r\nimport com.xht.framework.core.domain.query.PageBasicQuery;\r\nimport io.swagger.v3.oas.annotations.media.Schema;\r\nimport lombok.Data;\r\n\r\n/**\r\n * ${codeComment}查询请求参数\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Data\r\n@Schema(description = \"${codeComment}查询请求参数\")\r\npublic class ${codeNameUpperFirst}Query extends PageBasicQuery {\r\n\r\n#foreach ($column in ${queryColumns})\r\n    /**\r\n     * ${column.codeComment}\r\n     */  \r\n    @Schema(description = \"${column.conditionLabel}\")\r\n    private ${column.codeJava} ${column.codeName};\r\n    \r\n#end    \r\n}\r\n',
        '/java/${packageName}/${moduleName}/domain/query/', '${codeNameUpperFirst}Query', 'java', 3,
        '[\"update_by\",\"create_time\",\"create_by\",\"update_time\",\"del_flag\"]', 0, 'anonymity',
        '2025-11-12 16:50:19', 'anonymity', '2025-11-12 16:50:19');
INSERT INTO `gen_template`
VALUES (4, 1, 'Response.java',
        'package ${packageName}.modules.${moduleName}.domain.response;\r\n\r\nimport com.xht.framework.core.domain.response.MetaResponse;\r\nimport io.swagger.v3.oas.annotations.media.Schema;\r\nimport lombok.Data;\r\n\r\n/**\r\n * ${codeComment}响应信息\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Data\r\n@Schema(description = \"${codeComment}响应信息\")\r\npublic class ${codeNameUpperFirst}Response extends MetaResponse {\r\n\r\n    /**\r\n     * ${pkColumn.dbComment}\r\n     */\r\n    @Schema(name = \"${pkColumn.codeComment}\")\r\n    private ${pkColumn.codeJava} ${pkColumn.codeName};\r\n\r\n#foreach ($column in ${listColumns})\r\n#if($column.dbPrimary==0)\r\n    /**\r\n     * ${column.dbComment}\r\n     */  \r\n    @Schema(description = \"${column.codeComment}\")\r\n    private ${column.codeJava} ${column.codeName};\r\n    \r\n#end\r\n#end    \r\n}\r\n',
        '/java/${packageName}/${moduleName}/domain/response/', '${codeNameUpperFirst}Response', 'java', 4,
        '[\"create_time\",\"create_by\",\"update_by\",\"update_time\",\"del_flag\"]', 0, 'anonymity',
        '2025-11-12 16:51:17', 'anonymity', '2025-11-12 16:51:17');
INSERT INTO `gen_template`
VALUES (5, 1, 'Mapper.java',
        'package ${packageName}.modules.${moduleName}.dao.mapper;\r\n\r\nimport com.xht.framework.mybatis.mapper.BaseMapperX;\r\nimport ${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity;\r\nimport org.apache.ibatis.annotations.Mapper;\r\n\r\n/**\r\n * ${codeComment}Mapper接口\r\n *\r\n * @author ${backEndAuthor}\r\n */\r\n@Mapper\r\npublic interface ${codeNameUpperFirst}Mapper extends BaseMapperX<${codeNameUpperFirst}Entity> {\r\n\r\n}\r\n',
        '/java/${packageName}/${moduleName}/dao/mapper/', '${codeNameUpperFirst}Mapper', 'java', 5, '[]', 0,
        'anonymity', '2025-11-12 16:52:54', 'anonymity', '2025-11-12 16:52:54');
INSERT INTO `gen_template`
VALUES (6, 1, 'Dao.java',
        'package ${packageName}.modules.${moduleName}.dao;\r\n\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport com.xht.framework.mybatis.repository.MapperRepository;\r\nimport ${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.query.${codeNameUpperFirst}Query;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * ${codeComment} Dao\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\npublic interface ${codeNameUpperFirst}Dao extends MapperRepository<${codeNameUpperFirst}Entity> {\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    void updateFormRequest(${codeNameUpperFirst}Form form);\r\n\r\n    /**\r\n     * 分页查询${codeComment}\r\n     *\r\n     * @param page  分页信息\r\n     * @param query ${codeComment}查询请求参数\r\n     * @return ${codeComment}分页信息\r\n     */\r\n    Page<${codeNameUpperFirst}Entity> findPageList(Page<${codeNameUpperFirst}Entity> page, ${codeNameUpperFirst}Query query);\r\n\r\n}\r\n',
        '/java/${packageName}/${moduleName}/dao/', '${codeNameUpperFirst}Dao', 'java', 6, '[]', 0, 'anonymity',
        '2025-11-12 17:04:46', 'anonymity', '2025-11-12 17:04:46');
INSERT INTO `gen_template` VALUES (7, 1, 'DaoImpl.java', 'package ${packageName}.modules.${moduleName}.dao.impl;\r\n\r\nimport com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;\r\nimport com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;\r\nimport com.baomidou.mybatisplus.core.toolkit.support.SFunction;\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport com.baomidou.mybatisplus.extension.toolkit.SqlHelper;\r\nimport com.xht.framework.mybatis.repository.impl.MapperRepositoryImpl;\r\nimport ${packageName}.modules.${moduleName}.dao.${codeNameUpperFirst}Dao;\r\nimport ${packageName}.modules.${moduleName}.dao.mapper.${codeNameUpperFirst}Mapper;\r\nimport ${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.query.${codeNameUpperFirst}Query;\r\nimport lombok.extern.slf4j.Slf4j;\r\nimport org.springframework.stereotype.Repository;\r\nimport org.springframework.transaction.annotation.Transactional;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * ${codeComment} Dao实现类\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Slf4j\r\n@Repository\r\npublic class ${codeNameUpperFirst}DaoImpl extends MapperRepositoryImpl<${codeNameUpperFirst}Mapper, ${codeNameUpperFirst}Entity> implements ${codeNameUpperFirst}Dao {\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    @Override\r\n    @Transactional(rollbackFor = Exception.class)\r\n    public void updateFormRequest(${codeNameUpperFirst}Form form) {\r\n        LambdaUpdateWrapper<${codeNameUpperFirst}Entity> updateWrapper = new LambdaUpdateWrapper<>();\r\n#foreach ($column in ${formColumns})\r\n        updateWrapper.set(condition(form.get${column.codeNameUpperFirst}()), SysRoleEntity::get${column.codeNameUpperFirst}, form.get${column.codeNameUpperFirst}());\r\n#end  \r\n        updateWrapper.eq(${codeNameUpperFirst}Entity::get${pkColumn.codeNameUpperFirst}, form.get${pkColumn.codeNameUpperFirst}());\r\n        update(updateWrapper);\r\n    }\r\n\r\n    /**\r\n     * 分页查询${codeComment}\r\n     *\r\n     * @param page  分页信息\r\n     * @param query ${codeComment}查询请求参数\r\n     * @return ${codeComment}分页信息\r\n     */\r\n    @Override\r\n    public Page<${codeNameUpperFirst}Entity> findPageList(Page<${codeNameUpperFirst}Entity> page, SysRoleQuery query) {\r\n        LambdaQueryWrapper<${codeNameUpperFirst}Entity> queryWrapper = new LambdaQueryWrapper<>();\r\n        // @formatter:off\r\n#foreach ($column in ${queryColumns})                \r\n        queryWrapper.${column.queryType}(condition(query.get${column.codeNameUpperFirst}()), ${column.codeNameUpperFirst}Entity::get${column.codeNameUpperFirst}, query.get${column.codeNameUpperFirst}());\r\n#end        \r\n        // @formatter:on\r\n        return page(page, queryWrapper);\r\n    }\r\n\r\n    /**\r\n     * 获取主键字段名\r\n     *\r\n     * @return 主键字段名\r\n     */\r\n    @Override\r\n    protected SFunction<${codeNameUpperFirst}Entity, ?> getFieldId() {\r\n        return ${codeNameUpperFirst}Entity::get${pkColumn.codeNameUpperFirst};\r\n    }\r\n    \r\n}\r\n', '/java/${packageName}/${moduleName}/dao/impl/', '${codeNameUpperFirst}DaoImpl', 'java', 7, '[]', 0, 'anonymity', '2025-11-12 17:06:13', 'anonymity', '2025-11-12 17:06:13');
INSERT INTO `gen_template` VALUES (8, 1, 'Converter.java', 'package ${packageName}.modules.${moduleName}.converter;\r\n\r\nimport com.xht.framework.mybatis.converter.BasicConverter;\r\nimport ${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.response.${codeNameUpperFirst}Response;\r\nimport org.mapstruct.Mapper;\r\nimport org.mapstruct.MappingConstants;\r\nimport org.mapstruct.ReportingPolicy;\r\n\r\n/**\r\n * ${codeComment}转换器\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)\r\npublic interface ${codeNameUpperFirst}Converter extends BasicConverter<${codeNameUpperFirst}Entity, ${codeNameUpperFirst}Form, ${codeNameUpperFirst}Response> {\r\n\r\n}\r\n', '/java/${packageName}/${moduleName}/converter/', '${codeNameUpperFirst}Converter', 'java', 8, '[]', 0, 'anonymity', '2025-11-12 17:06:56', 'anonymity', '2025-11-12 17:06:56');
INSERT INTO `gen_template` VALUES (9, 1, 'IService.java', 'package ${packageName}.modules.${moduleName}.service;\r\n\r\nimport com.xht.framework.core.domain.response.PageResponse;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.query.${codeNameUpperFirst}Query;\r\nimport ${packageName}.modules.${moduleName}.domain.response.${codeNameUpperFirst}Response;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * ${codeComment} Service接口\r\n *\r\n * @author ${backEndAuthor}\r\n */\r\npublic interface I${codeNameUpperFirst}Service {\r\n\r\n    /**\r\n     * 创建${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    void create(${codeNameUpperFirst}Form form);\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} ${codeComment}主键\r\n     */\r\n    void remove(${pkColumn.codeJava} ${pkColumn.codeName});\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`批量删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName}s ${codeComment}主键集合\r\n     */\r\n    void remove(List<${pkColumn.codeJava}> ${pkColumn.codeName}s);\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    void updateById(${codeNameUpperFirst}Form form);\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`查询${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} ${codeComment}主键\r\n     * @return ${codeComment}信息\r\n     */\r\n    ${codeNameUpperFirst}Response findById(${pkColumn.codeJava} ${pkColumn.codeName});\r\n\r\n    /**\r\n     * 分页查询${codeComment}\r\n     *\r\n     * @param query ${codeComment}查询请求参数\r\n     * @return ${codeComment}分页信息\r\n     */\r\n    PageResponse<${codeNameUpperFirst}Response> findPageList(${codeNameUpperFirst}Query query);\r\n\r\n}\r\n', '/java/${packageName}/${moduleName}/service/', 'I${codeNameUpperFirst}Service', 'java', 9, '[]', 0, 'anonymity', '2025-11-12 17:08:16', 'anonymity', '2025-11-12 17:08:16');
INSERT INTO `gen_template` VALUES (10, 1, 'ServiceImpl.java', 'package ${packageName}.modules.${moduleName}.service.impl;\r\n\r\nimport com.baomidou.mybatisplus.extension.plugins.pagination.Page;\r\nimport com.xht.framework.core.domain.response.PageResponse;\r\nimport com.xht.framework.core.exception.code.BusinessErrorCode;\r\nimport com.xht.framework.core.exception.utils.ThrowUtils;\r\nimport com.xht.framework.mybatis.utils.PageTool;\r\nimport ${packageName}.modules.${moduleName}.converter.${codeNameUpperFirst}Converter;\r\nimport ${packageName}.modules.${moduleName}.dao.${codeNameUpperFirst}Dao;\r\nimport ${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.query.${codeNameUpperFirst}Query;\r\nimport ${packageName}.modules.${moduleName}.domain.response.${codeNameUpperFirst}Response;\r\nimport ${packageName}.modules.${moduleName}.service.I${codeNameUpperFirst}Service;\r\nimport lombok.RequiredArgsConstructor;\r\nimport lombok.extern.slf4j.Slf4j;\r\nimport org.springframework.stereotype.Service;\r\nimport org.springframework.transaction.annotation.Transactional;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * ${codeComment} Service实现类\r\n *\r\n * @author ${backEndAuthor}\r\n */\r\n@Slf4j\r\n@Service\r\n@RequiredArgsConstructor\r\npublic class ${codeNameUpperFirst}ServiceImpl implements I${codeNameUpperFirst}Service {\r\n\r\n    private final ${codeNameUpperFirst}Dao ${codeName}Dao;\r\n\r\n    private final ${codeNameUpperFirst}Converter ${codeName}Converter;\r\n\r\n    /**\r\n     * 创建${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    @Override\r\n    public void create(${codeNameUpperFirst}Form form) {\r\n        ${codeName}Dao.saveTransactional(entity);\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} ${codeComment}主键\r\n     */\r\n    @Override\r\n    @Transactional(rollbackFor = Exception.class)\r\n    public void remove(${pkColumn.codeJava} ${pkColumn.codeName}) {\r\n        ${codeName}Dao.removeById(id);\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`批量删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName}s ${codeComment}主键集合\r\n     */\r\n    @Override\r\n    @Transactional(rollbackFor = Exception.class)\r\n    public void remove(${pkColumn.codeJava} ${pkColumn.codeName}s) {\r\n        ${codeName}Dao.removeAllById(id);\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     */\r\n    @Override\r\n    public void updateById(${codeNameUpperFirst}Form form) {\r\n        ${codeName}Dao.updateFormRequest(form);\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`查询${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} ${codeComment}主键\r\n     * @return ${codeComment}信息\r\n     */\r\n    @Override\r\n    public ${codeNameUpperFirst}Response findById(${pkColumn.codeJava} ${pkColumn.codeName}) {\r\n        ${codeNameUpperFirst}Entity ${codeName}Entity = ${codeName}Dao.findOptionalById(id).orElse(null);\r\n        return ${codeName}Converter.toResponse(${codeName}Entity);\r\n    }\r\n\r\n    /**\r\n     * 分页查询${codeComment}\r\n     *\r\n     * @param query ${codeComment}查询请求参数\r\n     * @return ${codeComment}分页信息\r\n     */\r\n    @Override\r\n    public PageResponse<${codeNameUpperFirst}Response>findPageList(${codeNameUpperFirst}Query query) {\r\n        Page<${codeNameUpperFirst}Entity> page = ${codeName}Dao.findPageList(PageTool.getPage(query), query);\r\n        return ${codeName}Converter.toResponse(page);\r\n    }\r\n\r\n}\r\n', '/java/${packageName}/${moduleName}/service/impl/', '${codeNameUpperFirst}ServiceImpl', 'java', 10, '[]', 0, 'anonymity', '2025-11-12 17:09:15', 'anonymity', '2025-11-12 17:09:15');
INSERT INTO `gen_template` VALUES (11, 1, 'Controller.java', 'package ${packageName}.modules.${moduleName}.controller;\r\n\r\nimport com.xht.framework.core.domain.R;\r\nimport com.xht.framework.core.domain.response.PageResponse;\r\nimport com.xht.framework.oauth2.annotation.CheckMenu;\r\nimport com.xht.framework.web.validation.Groups;\r\nimport ${packageName}.modules.${moduleName}.domain.form.${codeNameUpperFirst}Form;\r\nimport ${packageName}.modules.${moduleName}.domain.query.${codeNameUpperFirst}Query;\r\nimport ${packageName}.modules.${moduleName}.domain.response.${codeNameUpperFirst}Response;\r\nimport ${packageName}.modules.${moduleName}.service.I${codeNameUpperFirst}Service;\r\nimport io.swagger.v3.oas.annotations.Operation;\r\nimport io.swagger.v3.oas.annotations.Parameter;\r\nimport io.swagger.v3.oas.annotations.tags.Tag;\r\nimport lombok.RequiredArgsConstructor;\r\nimport lombok.extern.slf4j.Slf4j;\r\nimport org.springframework.validation.annotation.Validated;\r\nimport org.springframework.web.bind.annotation.*;\r\n\r\nimport java.util.List;\r\n\r\n/**\r\n * ${codeComment}\r\n *\r\n * @author ${backEndAuthor}\r\n **/\r\n@Slf4j\r\n@Tag(name = \"${codeComment}\", description = \"${codeComment}相关的API\")\r\n@RestController\r\n@RequestMapping(\"${urlPrefix}\")\r\n@RequiredArgsConstructor\r\npublic class ${codeNameUpperFirst}Controller {\r\n\r\n    private final I${codeNameUpperFirst}Service ${codeName}Service;\r\n\r\n    /**\r\n     * 创建${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     * @return 统一响应结果\r\n     */\r\n    @CheckMenu(\"${permissionPrefix}:create\")\r\n    @PostMapping(\"/create\")\r\n    @Operation(summary = \"创建${codeComment}\")\r\n    public R<Void> create(@Validated(value = {Groups.Create.class}) @RequestBody ${codeNameUpperFirst}Form form) {\r\n        ${codeName}Service.create(form);\r\n        return R.ok();\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} 系统管理-字典表主键\r\n     * @return 统一响应结果\r\n     */\r\n    @CheckMenu(\"${permissionPrefix}:remove\")\r\n    @PostMapping(\"/remove\")\r\n    @Operation(summary = \"根据主键`${pkColumn.codeName}`删除${codeComment}\")\r\n    public R<Void> remove(@RequestBody Long ${pkColumn.codeName}) {\r\n        ${codeName}Service.remove(${pkColumn.codeName});\r\n        return R.ok();\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`批量删除${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName}s 系统管理-字典表主键集合\r\n     * @return 统一响应结果\r\n     */\r\n    @CheckMenu(\"${permissionPrefix}:remove\")\r\n    @PostMapping(\"/remove/batch\")\r\n    @Operation(summary = \"根据主键`${pkColumn.codeName}`批量删除${codeComment}\")\r\n    public R<Void> removeBatch(@RequestBody List<Long> ${pkColumn.codeName}s) {\r\n        ${codeName}Service.removeBatch(${pkColumn.codeName}s);\r\n        return R.ok();\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n     *\r\n     * @param form ${codeComment}表单请求参数\r\n     * @return 统一响应结果\r\n     */\r\n    @CheckMenu(\"${permissionPrefix}:update\")\r\n    @PostMapping(\"/update\")\r\n    @Operation(summary = \"根据ID更新${codeComment}\")\r\n    public R<Void> updateById(@Validated(value = {Groups.Update.class}) @RequestBody ${codeNameUpperFirst}Form form) {\r\n        ${codeName}Service.updateById(form);\r\n        return R.ok();\r\n    }\r\n\r\n    /**\r\n     * 根据主键`${pkColumn.codeName}`查询${codeComment}\r\n     *\r\n     * @param ${pkColumn.codeName} ${codeComment}主键\r\n     * @return ${codeComment}信息\r\n     */\r\n    @GetMapping(\"/get/{${pkColumn.codeName}}\")\r\n    @Operation(summary = \"根据ID查询${codeComment}\")\r\n    public R<${codeNameUpperFirst}Response> findById(@PathVariable(\"${pkColumn.codeName}\") ${pkColumn.codeJava} ${pkColumn.codeName}) {\r\n        return R.ok(${codeName}Service.findById(${pkColumn.codeName}));\r\n    }\r\n\r\n    /**\r\n     * 分页查询${codeComment}\r\n     *\r\n     * @param query ${codeComment}查询请求参数\r\n     * @return ${codeComment}分页信息\r\n     */\r\n    @GetMapping(\"/page\")\r\n    @Operation(summary = \"分页查询${codeComment}\")\r\n    public R<PageResponse<${codeNameUpperFirst}Response>>findPageList(${codeNameUpperFirst}Query query) {\r\n        return R.ok(${codeName}Service.findPageList(query));\r\n    }\r\n\r\n}\r\n\r\n', '/java/${packageName}/${moduleName}/controller/', '${codeNameUpperFirst}Controller', 'java', 11, '[]', 0, 'anonymity', '2025-11-12 17:11:36', 'anonymity', '2025-11-12 17:11:36');
INSERT INTO `gen_template` VALUES (12, 1, 'Mapper.xml', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE mapper\r\n        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n<mapper namespace=\"${packageName}.modules.${moduleName}.dao.mapper.${codeNameUpperFirst}Mapper\">\r\n\r\n    <resultMap id=\"BaseResultMap\" type=\"${packageName}.modules.${moduleName}.domain.entity.${codeNameUpperFirst}Entity\">\r\n        <id property=\"${pkColumn.codeName}\" column=\"${pkColumn.dbName}\" jdbcType=\"${pkColumn.dbType}\"/>\r\n#foreach ($column in ${columns})\r\n#if($column.dbPrimary==0)\r\n        <result property=\"${column.codeName}\" column=\"${column.dbName}\"/>\r\n#end\r\n#end\r\n    </resultMap>\r\n\r\n</mapper>\r\n', '/resources/mapper/', '${codeNameUpperFirst}Mapper', 'xml', 12, '[\"del_flag\"]', 0, 'anonymity', '2025-11-12 17:15:02', 'anonymity', '2025-11-12 17:15:02');
INSERT INTO `gen_template` VALUES (13, 1, 'model.ts', 'import type { BasicFormRequest, BasicResponse, ModeIdType, PageQueryRequest } from \'@/service/model/base.model\'\r\n\r\n/**\r\n * ${codeComment}查询请求参数类型\r\n */\r\nexport interface ${codeNameUpperFirst}QueryRequest extends PageQueryRequest {\r\n#foreach ($column in ${queryColumns})\r\n  ${column.codeName}: ${column.codeTs} // ${column.codeComment}\r\n#end\r\n}\r\n\r\n/**\r\n * ${codeComment}响应类型\r\n */\r\nexport interface ${codeNameUpperFirst}Response extends BasicResponse {\r\n  ${pkColumn.codeName}: ModeIdType // ${pkColumn.codeComment}\r\n#foreach ($column in ${listColumns})\r\n#if($column.dbPrimary==0)\r\n  ${column.codeName}: ${column.codeTs} // ${column.codeComment}\r\n#end\r\n#end\r\n}\r\n\r\n/**\r\n * ${codeComment}表单请求参数类型\r\n */\r\nexport interface ${codeNameUpperFirst}OperationRequest extends ${codeNameUpperFirst}Response, BasicFormRequest {\r\n#foreach ($column in ${formListDifferenceColumns})\r\n  ${column.codeName}: ${column.codeTs} // ${column.codeComment}\r\n#end	\r\n}\r\n', '/vue/service/model/${moduleName}/', '${serviceName}.model', 'ts', 13, '[]', 0, 'anonymity', '2025-12-01 12:21:37', 'anonymity', '2025-12-01 12:21:37');
INSERT INTO `gen_template` VALUES (14, 1, 'api.ts', 'import request from \'@/utils/request\'\r\nimport type { AxiosPromise } from \'axios\'\r\nimport type { ${codeNameUpperFirst}OperationRequest, ${codeNameUpperFirst}QueryRequest, ${codeNameUpperFirst}Response } from \'@/service/model/${moduleName}/${serviceName}.model\'\r\nimport type { ModeIdType, PageResponse } from \'@/service/model/base.model\'\r\n\r\n/**\r\n * 后台管理服务前缀\r\n */\r\nconst baseURL: string = import.meta.env.VITE_ADMIN_API_PREFIX\r\n\r\n/**\r\n * api定义\r\n */\r\nenum Api {\r\n  CREATE = \'${urlPrefix}/create\',\r\n  UPDATE = \'${urlPrefix}/update\',\r\n  REMOVE = \'${urlPrefix}/remove/\',\r\n  REMOVE_BATCH = \'${urlPrefix}/remove\',\r\n  QUERY_BYID = \'${urlPrefix}/get/\',\r\n  QUERY_PAGE = \'${urlPrefix}/page\'\r\n}\r\n\r\n/**\r\n * 创建${codeComment}\r\n *\r\n * @param form ${codeComment}表单请求参数\r\n */\r\nexport const save${codeNameUpperFirst} = (form: ${codeNameUpperFirst}OperationRequest): AxiosPromise<boolean> => {\r\n  return request({\r\n    url: Api.CREATE,\r\n    baseURL,\r\n    method: \'post\',\r\n    data: form,\r\n  })\r\n}\r\n\r\n/**\r\n * 根据主键`${pkColumn.codeName}`更新${codeComment}\r\n *\r\n * @param form ${codeComment}表单请求参数 \r\n */\r\nexport const update${codeNameUpperFirst} = (form: ${codeNameUpperFirst}OperationRequest): AxiosPromise<boolean> => {\r\n  return request({\r\n    url: Api.UPDATE,\r\n    baseURL,\r\n    method: \'post\',\r\n    data: form,\r\n  })\r\n}\r\n\r\n/**\r\n * 根据主键`${pkColumn.codeName}`删除${codeComment}\r\n *\r\n * @param ${pkColumn.codeName} ${codeComment}主键\r\n */\r\nexport const remove${codeNameUpperFirst}ById = (${pkColumn.codeName}: ModeIdType): AxiosPromise<boolean> => {\r\n  return request({\r\n    url: Api.REMOVE + `${id}`,\r\n    baseURL,\r\n    method: \'post\',\r\n  })\r\n}\r\n\r\n/**\r\n * 根据主键`${pkColumn.codeName}`删除${codeComment}\r\n *\r\n * @param ${pkColumn.codeName} ${codeComment}主键\r\n */\r\nexport const remove${codeNameUpperFirst}ByIdBatch = (${pkColumn.codeName}s: string): AxiosPromise<boolean> => {\r\n  return request({\r\n    url: Api.REMOVE_BATCH,\r\n    baseURL,\r\n    method: \'post\',\r\n	data: ${pkColumn.codeName}s\r\n  })\r\n}\r\n\r\n/**\r\n * 根据主键`${pkColumn.codeName}`查询${codeComment}\r\n *\r\n * @param id ${codeComment}主键\r\n */\r\nexport const query${codeNameUpperFirst}ById = (${pkColumn.codeName}: ModeIdType): AxiosPromise<${codeNameUpperFirst}Response> => {\r\n  return request({\r\n    url: Api.QUERY_BYID + `${${pkColumn.codeName}}`,\r\n    baseURL,\r\n    method: \'get\',\r\n  })\r\n}\r\n\r\n/**\r\n * 分页查询${codeComment}\r\n *\r\n * @param query ${codeComment}查询请求参数\r\n */\r\nexport const query${codeNameUpperFirst}Page = (query?: ${codeNameUpperFirst}QueryRequest): AxiosPromise<PageResponse<${codeNameUpperFirst}Response>> => {\r\n  return request({\r\n    url: Api.QUERY_PAGE,\r\n    baseURL,\r\n    method: \'get\',\r\n    params: query,\r\n  })\r\n}\r\n', '/vue/service/api/${moduleName}/', '${serviceName}.api', 'ts', 14, '[]', 0, 'anonymity', '2025-12-01 12:21:53', 'anonymity', '2025-12-01 12:21:53');
INSERT INTO `gen_template` VALUES (15, 1, 'index.vue', '<template>\r\n  <div class=\"xht-view-container\">\r\n    <div class=\"xht-view-main\">\r\n      <el-form ref=\"queryFormRef\" :disabled=\"state.loadingStatus\" :model=\"queryParams\" class=\"user-select-display\" label-width=\"100px\">\r\n        <el-row v-if=\"!state.searchStatus\">\r\n          <el-col :lg=\"6\" :md=\"8\" :sm=\"12\" :xl=\"4\" :xs=\"24\">\r\n            <el-form-item label=\"关键字\" prop=\"keyWord\">\r\n              <el-input v-model=\"queryParams.keyWord\" :maxlength=\"100\" placeholder=\"请输入关键字\" show-word-limit />\r\n            </el-form-item>\r\n          </el-col>\r\n          <el-col :xl=\"4\" :lg=\"6\" :md=\"8\" :sm=\"12\" :xs=\"24\" class=\"text-center\">\r\n            <el-button :icon=\"Search\" type=\"primary\" @click=\"handleQuery\">查询</el-button>\r\n            <el-button :icon=\"Refresh\" @click=\"resetQuery\">重置</el-button>\r\n          </el-col>\r\n        </el-row>\r\n        <el-row v-else>\r\n#foreach ($column in ${queryColumns})		\r\n          <el-col :xl=\"4\" :lg=\"6\" :md=\"8\" :sm=\"12\" :xs=\"24\">\r\n            <el-form-item label=\"${column.codeComment}\" prop=\"${column.codeName}\">\r\n              <el-input v-model=\"queryParams.${column.codeName}\" placeholder=\"请输入${column.codeComment}\" />\r\n            </el-form-item>\r\n          </el-col>\r\n#end		  \r\n          <el-col :xl=\"4\" :lg=\"6\" :md=\"8\" :sm=\"12\" :xs=\"24\" class=\"text-center\">\r\n            <el-button :icon=\"Search\" type=\"primary\" @click=\"handleQuery\">查询</el-button>\r\n            <el-button :icon=\"Refresh\" @click=\"resetQuery\">重置</el-button>\r\n          </el-col>\r\n        </el-row>\r\n      </el-form>\r\n      <table-tool-bar\r\n        v-model:column-data=\"columnOption\"\r\n        v-model:show-search=\"state.searchStatus\"\r\n        column-status\r\n        refresh-status\r\n        search-status\r\n        @refresh=\"handleQuery\"\r\n      >\r\n        <el-button :icon=\"Plus\" size=\"small\" type=\"primary\" @click=\"handleAdd\">新增</el-button>\r\n        <el-button :disabled=\"state.singleStatus\" :icon=\"Edit\" size=\"small\" type=\"success\" @click=\"handleEdit(state.selectedRows[0])\">修改</el-button>\r\n        <el-button :disabled=\"state.multipleStatus\" :icon=\"Delete\" size=\"small\" type=\"danger\" @click=\"handleBatchDelete\">批量删除</el-button>\r\n      </table-tool-bar>\r\n      <xht-table\r\n        v-loading=\"state.loadingStatus\"\r\n        :data=\"state.tableList\"\r\n        class=\"flex-1\"\r\n        empty-text=\"系统暂无${codeComment}！\"\r\n        @selection-change=\"handleSelectionChange\"\r\n      >\r\n        <el-table-column align=\"center\" type=\"selection\" width=\"55\" />\r\n        <xht-column-index :current=\"queryParams.current\" :size=\"queryParams.size\" />\r\n#foreach ($column in ${listColumns})\r\n#if($column.dbPrimary==0)\r\n        <el-table-column#if($column.listShow==0)v-if=\"columnOption.${column.codeName}?.visible\"#end label=\"${column.listComment}\" prop=\"${column.codeName}\" min-width=\"160\" />\r\n#end\r\n#end\r\n        <el-table-column fixed=\"right\" label=\"操作\" width=\"260px\">\r\n          <template #default=\"{ row }\">\r\n            <el-button icon=\"Edit\" link type=\"success\" @click=\"handleEdit(row)\">修改</el-button>\r\n            <el-button icon=\"Delete\" link type=\"danger\" @click=\"handleDelete(row)\">删除</el-button>\r\n          </template>\r\n        </el-table-column>\r\n      </xht-table>\r\n      <xht-pagination\r\n        v-model:current-page=\"state.queryParams.current\"\r\n        v-model:page-size=\"state.queryParams.size\"\r\n        :page-count=\"state.pages\"\r\n        :total=\"state.total\"\r\n        @pagination=\"handleQuery\"\r\n      />\r\n    </div>\r\n    <${serviceName}-from ref=\"${serviceName}FormRef\" @success=\"handleQuery\" />\r\n  </div>\r\n</template>\r\n\r\n<script lang=\"ts\" setup>\r\nimport type { FormInstance } from \'element-plus\'\r\nimport { useTableQueryPageHooks } from \'@/hooks/use-crud-hooks\'\r\nimport type { ${codeNameUpperFirst}QueryRequest, ${codeNameUpperFirst}Response } from \'@/service/model/${moduleName}/${serviceName}.model\'\r\nimport { query${codeNameUpperFirst}Page, remove${codeNameUpperFirst}ById, remove${codeNameUpperFirst}ByIds } from \'@/service/api/${moduleName}/${serviceName}.api\'\r\nimport { useMessage, useMessageBox } from \'@/hooks/use-message\'\r\nimport type { ColumnConfig } from \'@/components/table-tool-bar/types\'\r\nimport { ${codeNameUpperFirst}ColumnOption } from \'@/views/system/${moduleName}/${serviceName}.data\'\r\nimport { Delete, Edit, Plus, Refresh, Search } from \'@element-plus/icons-vue\'\r\n\r\ndefineOptions({ name: \'${codeNameUpperFirstViewIndex}\' })\r\n\r\nconst ${serviceName}From = defineAsyncComponent(() => import(\'@/views/${moduleName}/${serviceName}/components/${serviceName}-from.vue\'))\r\nconst ${serviceName}FormRef = useTemplateRef(\'${serviceName}FormRef\')\r\nconst queryFormRef = useTemplateRef<FormInstance>(\'queryFormRef\')\r\n\r\nconst state = reactive<TableQueryPageState<${codeNameUpperFirst}QueryRequest, ${codeNameUpperFirst}Response>>({\r\n  queryParams: {\r\n    current: 1,\r\n    size: 10,\r\n  },\r\n  loadingStatus: false,\r\n  total: 0,\r\n  pages: 0,\r\n  tableList: [],\r\n  selectedRows: [],\r\n  singleStatus: true, \r\n  multipleStatus: true\r\n})\r\nconst { handleQuery, handleSelectionChange } = useTableQueryPageHooks<${codeNameUpperFirst}QueryRequest, ${codeNameUpperFirst}Response>(state, query${codeNameUpperFirst}Page)\r\nconst { queryParams } = toRefs(state)\r\n\r\nconst columnOption = ref<ColumnConfig<${codeNameUpperFirst}Response>>({\r\n  ...${codeNameUpperFirst}ColumnOption,\r\n})\r\n\r\n/**\r\n * 重置查询表单\r\n */\r\nconst resetQuery = async () => {\r\n  queryFormRef.value?.resetFields()\r\n  await handleQuery()\r\n}\r\n\r\n/**\r\n * 处理新增${codeComment}\r\n */\r\nconst handleAdd = () => {\r\n  roleFormRef.value?.show(\'create\', null)\r\n}\r\n\r\n/**\r\n * 处理编辑${codeComment}\r\n */\r\nconst handleEdit = (row: ${codeNameUpperFirst}Response) => {\r\n  roleFormRef.value?.show(\'update\', row.id)\r\n}\r\n\r\n/**\r\n * 处理删除${codeComment}\r\n */\r\nconst handleDelete = (row: ${codeNameUpperFirst}Response) => {\r\n  state.loadingStatus = true\r\n  useMessageBox()\r\n    .confirm(\'此操作将永久删除${codeComment}, 是否继续?\')\r\n    .then(async () => {\r\n      await remove${codeNameUpperFirst}ById(row.id)\r\n      await handleQuery()\r\n      useMessage().success(\'删除${codeComment}成功!\')\r\n    })\r\n    .finally(() => {\r\n      state.loadingStatus = false\r\n    })\r\n}\r\n\r\n/**\r\n * 处理批量删除${codeComment}\r\n */\r\nconst handleBatchDelete = () => {\r\n  const ids = state.selectedRows.map((item) => item.id)\r\n  if (!ids || ids.length <= 0) {\r\n    useMessage().error(\'请选择${codeComment}数据\')\r\n  }\r\n  state.loadingStatus = true\r\n  useMessageBox()\r\n    .confirm(`此操作将批量删除${ids.length}个${codeComment}, 是否继续?`)\r\n    .then(async () => {\r\n      await remove${codeNameUpperFirst}ByIds(ids)\r\n      await handleQuery()\r\n      useMessage().success(\'批量删除${codeComment}成功!\')\r\n    })\r\n    .finally(() => {\r\n      state.loadingStatus = false\r\n    })\r\n}\r\n\r\nonMounted(async () => {\r\n  await handleQuery()\r\n})\r\n\r\n</script>\r\n\r\n<style lang=\"scss\" scoped></style>\r\n', '/vue/views/${moduleName}/${serviceName}/', 'index', 'java', 15, '[]', 0, 'anonymity', '2025-12-01 12:22:21', 'anonymity', '2025-12-01 12:22:21');
INSERT INTO `gen_template` VALUES (16, 1, 'data.ts', 'import type { ${codeNameUpperFirst}OperationRequest, ${codeNameUpperFirst}Response } from \'@/service/model/${moduleName}/${serviceName}.model\'\r\nimport type { FormRules } from \'element-plus\'\r\nimport type { ColumnConfig } from \'@/components/table-tool-bar/types\'\r\n\r\n/**\r\n * ${codeComment} 增改页面 表单类型 默认值\r\n */\r\nexport const ${codeNameUpperFirst}OperationForm: ${codeNameUpperFirst}OperationRequest = {\r\n  ${pkColumn.codeName}: null, // ${pkColumn.codeComment} \r\n#foreach ($column in ${formColumns})\r\n  ${column.codeName}: \'\', // ${column.codeComment}\r\n#end\r\n}\r\n\r\n/**\r\n * ${codeComment} 增改页面 表单类型 表单校验\r\n */\r\nexport const ${codeNameUpperFirst}OperationRules: FormRules<${codeNameUpperFirst}OperationRequest> = {\r\n#foreach ($column in ${formColumns})\r\n  ${column.codeName}: [{ required: true, message: \'请输入${column.codeComment}\', trigger: [\'blur\', \'change\'] }],\r\n#end\r\n}\r\n\r\n/**\r\n * ${codeComment} 列表显示配置\r\n */\r\nexport const ${codeNameUpperFirst}ColumnOption: ColumnConfig<${codeNameUpperFirst}Response> = {\r\n#foreach ($column in ${listColumns})\r\n  ${column.codeName}: { desc: \'${column.listComment}\', visible: ${column.listDisabled}, disabled: ${column.listHidden} },\r\n#end\r\n}\r\n', '/vue/views/${moduleName}/${serviceName}/', '${serviceName}-data', 'ts', 16, '[\"del_flag\",\"update_time\",\"update_by\",\"create_time\",\"create_by\"]', 0, 'anonymity', '2025-12-01 12:22:51', 'anonymity', '2025-12-01 12:22:51');
INSERT INTO `gen_template` VALUES (17, 1, 'from.vue', '<template>\r\n  <el-${pageStyle}\r\n    v-model=\"state.visibleStatus\"\r\n    :title=\"state.title\"\r\n    size=\"${pageStyleWidth}%\"\r\n    append-to-body\r\n    :close-on-click-modal=\"false\"\r\n    :show-close=\"!state.loadingStatus\"\r\n    :before-close=\"close\"\r\n  >\r\n    <el-form\r\n      ref=\"addUpdateFormRef\"\r\n      v-loading=\"state.loadingStatus\"\r\n      :model=\"addUpdateForm\"\r\n      :rules=\"rules\"\r\n      element-loading-text=\"拼命加载中\"\r\n      inline-message\r\n      label-width=\"100px\"\r\n      scroll-to-error\r\n    >\r\n      <el-row>\r\n#foreach ($column in ${formColumns})		  \r\n        <el-col :span=\"${fromNumber}\">\r\n          <el-form-item label=\"${column.codeComment}\" prop=\"${column.codeName}\">\r\n            <el-input v-model=\"addUpdateForm.${column.codeName}\" :maxlength=\"${column.fromLength}\" placeholder=\"请输入${column.codeComment}\" show-word-limit />\r\n          </el-form-item>\r\n        </el-col>\r\n#end		\r\n      </el-row>\r\n    </el-form>\r\n    <template #footer>\r\n      <el-button :disabled=\"state.loadingStatus\" @click=\"close\">取 消</el-button>\r\n      <el-button :disabled=\"state.loadingStatus\" type=\"primary\" @click=\"submitForm\">提交</el-button>\r\n    </template>\r\n  </el-${pageStyle}>\r\n</template>\r\n\r\n<script lang=\"ts\" setup>\r\nimport type { FormInstance, FormRules } from \'element-plus\'\r\nimport { query${codeNameUpperFirst}ById, save${codeNameUpperFirst}, update${codeNameUpperFirst} } from \'@/service/api/${moduleName}/${serviceName}.api\'\r\nimport type { ${codeNameUpperFirst}OperationRequest } from \'@/service/model/${moduleName}/${serviceName}.model\'\r\nimport { ${codeNameUpperFirst}OperationForm, ${codeNameUpperFirst}OperationRules } from \'@/views/${moduleName}/${serviceName}/${serviceName}.data\'\r\nimport { useMessage } from \'@/hooks/use-message\'\r\nimport type { ModeIdType } from \'@/service/model/base.model\'\r\n\r\ndefineOptions({ name: \'${codeNameUpperFirst}AddOrUpdate\' })\r\n\r\nconst state = reactive<AddUpdateOption<${codeNameUpperFirst}OperationRequest>>({\r\n  title: \'增加${codeComment}\',\r\n  visibleStatus: false,\r\n  operationStatus: \'create\',\r\n  loadingStatus: false,\r\n  addUpdateForm: { ...${codeNameUpperFirst}OperationForm },\r\n})\r\nconst addUpdateFormRef = ref<FormInstance>()\r\nconst { addUpdateForm } = toRefs(state)\r\nconst emits = defineEmits([\'success\'])\r\nconst rules: FormRules = ${codeNameUpperFirst}OperationRules\r\n\r\n/**\r\n * 打开显示\r\n */\r\nconst show = async (type: \'create\' | \'update\', id: ModeIdType) => {\r\n  try {\r\n    state.visibleStatus = true\r\n    state.operationStatus = type\r\n    state.loadingStatus = true\r\n    if (type === \'update\') {\r\n      state.title = \'修改${codeComment}\'\r\n      const { data } = await query${codeNameUpperFirst}ById(id)\r\n      addUpdateForm.value = data\r\n    }\r\n  } catch {\r\n    state.loadingStatus = false\r\n  }\r\n}\r\n\r\n/**\r\n * 提交表单\r\n */\r\nconst submitForm = () => {\r\n  state.loadingStatus = true\r\n  addUpdateFormRef.value?.validate(async (valid) => {\r\n    if (valid) {\r\n      try {\r\n        if (state.operationStatus === \'create\') {\r\n          await save${codeNameUpperFirst}(addUpdateForm.value)\r\n          useMessage().success(`新增${codeComment}成功`)\r\n        } else {\r\n          await update${codeNameUpperFirst}(addUpdateForm.value)\r\n          useMessage().success(`修改${codeComment}成功`)\r\n        }\r\n        emits(\'success\')\r\n        state.loadingStatus = false\r\n        close()\r\n      } catch {\r\n        state.loadingStatus = false\r\n      }\r\n    } else {\r\n      state.loadingStatus = false\r\n      useMessage().error(\'表单校验未通过，请重新检查提交内容\')\r\n    }\r\n  })\r\n}\r\n\r\n/**\r\n * 关闭\r\n */\r\nconst close = () => {\r\n  if (state.loadingStatus) return\r\n  state.visibleStatus = false\r\n  state.operationStatus = \'create\'\r\n  addUpdateFormRef.value?.resetFields()\r\n}\r\n\r\ndefineExpose({\r\n  show,\r\n})\r\n\r\n</script>\r\n\r\n<style lang=\"scss\" scoped></style>\r\n', '/vue/views/${moduleName}/${serviceName}/', '${serviceName}-from', 'vue', 17, '[\"create_time\",\"update_by\",\"update_time\",\"del_flag\",\"create_by\"]', 0, 'anonymity', '2025-12-05 13:03:36', 'anonymity', '2025-12-05 13:03:36');

-- ----------------------------
-- Table structure for gen_table_info
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_info`;
CREATE TABLE `gen_table_info`  (
                                   `id` bigint NOT NULL COMMENT '表ID',
                                   `group_id` bigint NULL DEFAULT NULL COMMENT '分组id',
                                   `data_source_id` bigint NOT NULL COMMENT '数据源ID',
                                   `engine_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '引擎名称',
                                   `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库表名',
                                   `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表注释',
                                   `code_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成的类名',
                                   `code_comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码的注释',
                                   `module_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
                                   `function_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能名',
                                   `table_create_time` datetime NULL DEFAULT NULL COMMENT '表创建时间',
                                   `table_update_time` datetime NULL DEFAULT NULL COMMENT '表修改时间',
                                   `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
                                   `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                   `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                   `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-表结构信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_info
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column_query
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column_query`;
CREATE TABLE `gen_table_column_query`  (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `table_id` bigint NOT NULL COMMENT '表id',
                                           `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名称(冗余字段)',
                                           `column_id` bigint NULL DEFAULT NULL COMMENT '字段id',
                                           `column_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
                                           `from_length` int NULL DEFAULT NULL COMMENT '表单输入长度',
                                           `query_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询类型（如等于、不等于、大于、小于等）',
                                           `condition_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件标签（显示用的文本）',
                                           `condition_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段值（条件值）',
                                           `sort_order` int NULL DEFAULT 0 COMMENT '字段排序',
                                           `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                           `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                           `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                           `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-查询条件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column_query
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
                                     `id` bigint NOT NULL COMMENT '主键',
                                     `table_id` bigint NOT NULL COMMENT '表id',
                                     `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名称(冗余字段)',
                                     `db_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名',
                                     `db_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
                                     `db_primary` tinyint NOT NULL DEFAULT 0 COMMENT '字段主键：0-非主键，1-主键',
                                     `db_required` tinyint NOT NULL DEFAULT 0 COMMENT '字段必填：0-非必填，1-必填',
                                     `db_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段注释',
                                     `db_length` int NULL DEFAULT NULL COMMENT '字段长度',
                                     `code_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码名称',
                                     `code_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码注释',
                                     `from_insert` tinyint NOT NULL DEFAULT 1 COMMENT '表单新增：0-不显示，1-显示',
                                     `from_update` tinyint NOT NULL DEFAULT 1 COMMENT '表单更新：0-不显示，1-显示',
                                     `from_length` int NULL DEFAULT NULL COMMENT '表单输入长度',
                                     `from_fill` tinyint NOT NULL DEFAULT 0 COMMENT '表单必填：0-非必填，1-必填',
                                     `from_component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单组件',
                                     `list_show` tinyint NOT NULL DEFAULT 1 COMMENT '列表显示：0-不显示，1-显示',
                                     `list_disabled` tinyint NOT NULL DEFAULT 0 COMMENT '显示切换禁用：0-不禁用，1-禁用',
                                     `list_hidden` tinyint NOT NULL DEFAULT 0 COMMENT '默认隐藏：0-不隐藏，1-隐藏',
                                     `code_java` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'java类型',
                                     `code_java_package` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'java类型 包地址',
                                     `code_ts` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ts类型',
                                     `sort_order` int NOT NULL DEFAULT 0 COMMENT '字段排序',
                                     `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
                                     `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                     `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                     `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                     `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表结构元数据信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
                              `id` bigint NOT NULL COMMENT '表ID',
                              `group_id` bigint NOT NULL COMMENT '分组id',
                              `data_source_id` bigint NOT NULL COMMENT '数据源ID',
                              `data_base_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库类型',
                              `engine_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '引擎名称',
                              `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库表名',
                              `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表注释',
                              `module_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名称',
                              `service_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务名称',
                              `code_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码名称',
                              `code_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码注释',
                              `back_end_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端作者',
                              `front_end_author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端作者',
                              `url_prefix` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求前缀',
                              `permission_prefix` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限前缀',
                              `parent_menu_id` bigint NULL DEFAULT NULL COMMENT '上级菜单',
                              `page_style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面风格',
                              `page_style_width` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面宽度',
                              `from_number` int NULL DEFAULT 0 COMMENT '每行数量',
                              `table_create_time` datetime NOT NULL COMMENT '表创建时间',
                              `table_update_time` datetime NULL DEFAULT NULL COMMENT '表更新时间',
                              `del_flag` tinyint NULL DEFAULT NULL COMMENT '删除标识(0:未删除;1:已删除)',
                              `create_by` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `update_by` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-表结构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (1996837779885985792, 1, 1, 'MySql', 'InnoDB', 'sys_dict', '系统管理-字典', 'system', 'dict', 'SysDict', '字典管理', 'xht', 'xht', '/sys/dict', 'sys:dict', 0, '0', '45', 4, '2025-09-15 00:00:00', NULL, NULL, 'anonymity', '2025-12-05 15:03:21', 'anonymity', '2025-12-11 18:54:31');

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_source`;
CREATE TABLE `gen_data_source`  (
                                    `id` bigint NOT NULL COMMENT '数据源ID',
                                    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据源名称',
                                    `db_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库类型(MySQL/Oracle)',
                                    `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库地址',
                                    `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库用户名',
                                    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库密码',
                                    `test_result` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连接测试结果(success/fail)',
                                    `last_test_time` datetime NULL DEFAULT NULL COMMENT '最后测试时间',
                                    `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
                                    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-数据源配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_data_source
-- ----------------------------
INSERT INTO `gen_data_source` VALUES (1, '本地MySql', 'MySql', 'jdbc:mysql://localhost:3306/xht_platform?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8', 'root', '123456', 'success', '2025-12-03 18:34:55', 0, 'admin', '2025-09-15 14:46:07', 'admin', '2025-09-15 14:46:07');

-- ----------------------------
-- Table structure for gen_column_info
-- ----------------------------
DROP TABLE IF EXISTS `gen_column_info`;
CREATE TABLE `gen_column_info`  (
                                    `id` bigint NOT NULL COMMENT '字段ID',
                                    `table_id` bigint NOT NULL COMMENT '表ID(关联gen_table_info)',
                                    `column_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库字段名',
                                    `db_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库字段类型',
                                    `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段注释',
                                    `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段默认值',
                                    `is_required` tinyint NULL DEFAULT 0 COMMENT '是否必填(0否 1是)',
                                    `is_primary` tinyint NULL DEFAULT 0 COMMENT '是否主键(0否 1是)',
                                    `ext_config` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置属性',
                                    `sort_order` int NULL DEFAULT 0 COMMENT '字段排序',
                                    `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
                                    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成器-表字段信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_column_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
