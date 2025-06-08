create database xht_platform;
use xht_platform;

-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`             bigint                                                        NOT NULL COMMENT '部门id',
    `parent_id`      bigint                                                        NULL     DEFAULT 0 COMMENT '父部门id',
    `dept_code`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '部门编码',
    `dept_name`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '部门名称',
    `dept_level`     int                                                           NOT NULL COMMENT '树层级',
    `dept_status`    tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `dept_sort`      int UNSIGNED                                                  NULL     DEFAULT 0 COMMENT '显示顺序',
    `ancestors`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '祖级列表',
    `leader_user_id` bigint                                                        NULL     DEFAULT NULL COMMENT '负责人用户id',
    `leader_name`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '负责人',
    `leader_post_id` bigint                                                        NULL     DEFAULT NULL COMMENT '负责人岗位id',
    `phone`          varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '联系电话',
    `email`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '邮箱',
    `remark`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '备注',
    `create_by`      varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '创建者',
    `create_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`      varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '更新者',
    `update_time`    datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `is_del`         tinyint                                                       NOT NULL DEFAULT 0 COMMENT '删除标志(0正常 1删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_post`;
CREATE TABLE `sys_dept_post`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `dept_id`     bigint                                                        NOT NULL COMMENT '部门id',
    `post_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '岗位名称',
    `post_sort`   int                                                           NOT NULL COMMENT '岗位排序',
    `post_status` tinyint                                                       NULL     DEFAULT NULL COMMENT '岗位状态',
    `post_limit`  int                                                           NULL     DEFAULT NULL COMMENT '岗位限制人数',
    `post_have`   int                                                           NULL     DEFAULT NULL COMMENT '岗位已分配人数',
    `system_flag` tinyint                                                       NULL     DEFAULT NULL COMMENT '系统内置0是',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '岗位描述',
    `is_del`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
    `create_time` datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '创建人',
    `update_time` datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典ID',
    `dict_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '字典编码',
    `dict_name`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
    `sort_order`  int                                                           NULL DEFAULT 0 COMMENT '排序序号',
    `remark`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典描述',
    `status`      tinyint                                                       NULL DEFAULT 1 COMMENT '状态(1:启用 0:禁用)',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建人',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新人',
    `is_del`      tinyint                                                       NULL DEFAULT 0 COMMENT '是否删除(1:是 0:否)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_dict_code` (`dict_code` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统字典表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
    `dict_id`     bigint                                                        NOT NULL COMMENT '所属字典ID',
    `dict_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '字典项编码',
    `item_label`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项标签',
    `item_value`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典项值',
    `item_color`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '显示颜色',
    `sort_order`  int                                                           NULL DEFAULT 0 COMMENT '排序序号',
    `remark`      varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典项描述',
    `status`      tinyint                                                       NULL DEFAULT 1 COMMENT '状态(1:启用 0:禁用)',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建人',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新人',
    `is_del`      tinyint                                                       NULL DEFAULT 0 COMMENT '是否删除(1:是 0:否)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dict_id` (`dict_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统字典项表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `parent_id`        bigint                                                        NOT NULL COMMENT '父菜单ID',
    `menu_type`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL COMMENT '类型',
    `menu_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `menu_icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '菜单图标',
    `menu_path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '路由地址',
    `menu_hidden`      tinyint(1)                                                    NULL     DEFAULT 1 COMMENT '显示状态',
    `menu_cache`       tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '是否缓存',
    `menu_status`      tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '菜单状态',
    `menu_authority`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '菜单权限字符串',
    `menu_sort`        int                                                           NULL     DEFAULT NULL COMMENT '菜单排序',
    `view_name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '组件视图名称',
    `view_path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '组件视图路径',
    `active_menu_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '菜单显示(菜单隐藏时填写)',
    `frame_flag`       tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '是否为外链',
    `frame_url`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '外链地址',
    `create_by`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '创建者',
    `create_time`      datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '更新者',
    `update_time`      datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_del`           tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '删除标志(0正常 1删除)',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统菜单表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_code`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '角色编码',
    `role_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '角色名称',
    `data_scope`  tinyint(1)                                                    NULL DEFAULT NULL COMMENT '数据范围（1全部数据权限 2自定数据权限 3本部门数据权限 4本部门及以下数据权限 5本岗位数据权限  6仅本人数据权限）',
    `role_status` tinyint                                                       NULL DEFAULT NULL COMMENT '状态（0正常 1停用）',
    `role_sort`   int                                                           NULL DEFAULT NULL COMMENT '显示顺序',
    `remark`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
    `create_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建人',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新人',
    `is_del`      tinyint                                                       NULL DEFAULT 0 COMMENT '是否删除(1:是 0:否)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_role_code` (`role_code` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id`     bigint                                                       NOT NULL COMMENT '角色id',
    `menu_id`     bigint                                                       NOT NULL COMMENT '菜单id',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色-菜单关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户名',
    `pass_word`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `nick_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '用户昵称',
    `avatar_url`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '头像地址',
    `user_status` tinyint                                                       NOT NULL DEFAULT 1 COMMENT '账号状态(1-正常,2-锁定,3-禁用,4-过期)',
    `dept_id`     bigint                                                        NULL     DEFAULT NULL COMMENT '部门id',
    `create_time` datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '创建人',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '更新人',
    `is_del`      tinyint                                                       NULL     DEFAULT 0 COMMENT '是否删除(1:是 0:否)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_username` (`user_name` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户认证表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id`     bigint                                                       NOT NULL COMMENT '用户id',
    `dept_id`     bigint                                                       NOT NULL COMMENT '部门id',
    `post_id`     bigint                                                       NOT NULL COMMENT '部门岗位id',
    `create_time` datetime                                                     NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `sys_user_dept` (`user_id` ASC, `dept_id` ASC) USING BTREE,
    INDEX `idx_user_cover` (`user_id` ASC, `dept_id` ASC, `post_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户部门关联表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_profiles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_profiles`;
CREATE TABLE `sys_user_profiles`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '信息ID',
    `user_id`        bigint                                                        NOT NULL COMMENT '关联用户ID',
    `real_name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '真实姓名',
    `id_card_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '身份证号',
    `gender`         tinyint                                                       NULL DEFAULT NULL COMMENT '性别(1-男,2-女,3-其他)',
    `birth_date`     date                                                          NULL DEFAULT NULL COMMENT '出生日期',
    `age`            tinyint                                                       NULL DEFAULT NULL COMMENT '年龄(可计算字段)',
    `address`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
    `postal_code`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '邮政编码',
    `create_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '创建人',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '更新人',
    `is_del`         tinyint                                                       NULL DEFAULT 0 COMMENT '是否删除(1:是 0:否)',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_user_id` (`user_id` ASC) USING BTREE,
    UNIQUE INDEX `idx_id_card` (`id_card_number` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id`     bigint                                                       NOT NULL COMMENT '用户id',
    `role_id`     bigint                                                       NOT NULL COMMENT '角色id',
    `create_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`user_id` DESC, `role_id`) USING BTREE,
    UNIQUE INDEX `sys_user_role` (`role_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关联表'
  ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
