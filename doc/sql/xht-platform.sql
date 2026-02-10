SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;
USE
`xht_platform`;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`
(
    `area_code`        varchar(32) NOT NULL COMMENT '区划编码',
    `parent_area_code` varchar(64) NULL DEFAULT NULL COMMENT '上级区划编码',
    `area_name`        varchar(100) NULL DEFAULT NULL COMMENT '区划名称',
    `area_post_code`   varchar(30) NULL DEFAULT NULL COMMENT '邮政编码',
    `area_longitude`   varchar(30) NULL DEFAULT NULL COMMENT '经度',
    `area_latitude`    varchar(30) NULL DEFAULT NULL COMMENT '纬度',
    `area_sort`        int NULL DEFAULT NULL COMMENT '排序',
    `has_child`        tinyint NULL DEFAULT NULL COMMENT '是否存在下级',
    `del_flag`         tinyint NULL DEFAULT NULL COMMENT '删除标识(0:未删除;1:已删除)',
    PRIMARY KEY (`area_code`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-行政区划' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_calendar
-- ----------------------------
DROP TABLE IF EXISTS `sys_calendar`;
CREATE TABLE `sys_calendar`
(
    `id`        bigint  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `date_info` date    NOT NULL COMMENT '日期时间',
    `date_type` char(1) NOT NULL COMMENT '日期类型',
    `date_week` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_german2_ci NULL DEFAULT NULL COMMENT '周几',
    `remark`    varchar(255) NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4233  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `id`          bigint  NOT NULL COMMENT '部门id',
    `parent_id`   bigint NULL DEFAULT 0 COMMENT '父部门id',
    `dept_code`   varchar(64) NULL DEFAULT NULL COMMENT '部门编码',
    `dept_name`   varchar(30) NULL DEFAULT NULL COMMENT '部门名称',
    `dept_level`  int     NOT NULL COMMENT '树层级',
    `dept_status` tinyint NOT NULL DEFAULT 0 COMMENT '状态(0:正常;1:停用)',
    `dept_sort`   int UNSIGNED NULL DEFAULT 0 COMMENT '显示顺序',
    `ancestors`   varchar(500) NULL DEFAULT NULL COMMENT '祖级列表',
    `phone`       varchar(11) NULL DEFAULT NULL COMMENT '联系电话',
    `email`       varchar(50) NULL DEFAULT NULL COMMENT '邮箱',
    `remark`      varchar(200) NULL DEFAULT NULL COMMENT '备注',
    `del_flag`    tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`   varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-部门信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`
(
    `id`            bigint       NOT NULL COMMENT '字典ID',
    `dict_code`     varchar(64)  NOT NULL COMMENT '字典编码',
    `dict_name`     varchar(128) NOT NULL COMMENT '字典名称',
    `sort_order`    int NULL DEFAULT 0 COMMENT '排序序号',
    `remark`        varchar(512) NULL DEFAULT NULL COMMENT '字典描述',
    `status`        tinyint NULL DEFAULT 1 COMMENT '状态(0:正常;1:停用)',
    `show_disabled` tinyint NULL DEFAULT NULL COMMENT '子节点是否显示禁用状态',
    `del_flag`      tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`     varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`     varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_dict_code`(`dict_code` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`
(
    `id`          bigint       NOT NULL COMMENT '字典项ID',
    `dict_id`     bigint       NOT NULL COMMENT '所属字典ID',
    `dict_code`   varchar(64)  NOT NULL COMMENT '字典项编码',
    `item_label`  varchar(128) NOT NULL COMMENT '字典项标签',
    `item_value`  varchar(128) NOT NULL COMMENT '字典项值',
    `item_color`  varchar(100) NULL DEFAULT NULL COMMENT '显示颜色',
    `sort_order`  int NULL DEFAULT 0 COMMENT '排序序号',
    `remark`      varchar(512) NULL DEFAULT NULL COMMENT '字典项描述',
    `status`      tinyint NULL DEFAULT 1 COMMENT '状态(0:正常;1:停用)',
    `del_flag`    tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`   varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `idx_dict_id`(`dict_id` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-字典项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`               bigint       NOT NULL COMMENT '菜单ID',
    `parent_id`        bigint       NOT NULL COMMENT '父菜单ID',
    `menu_type`        char(1)      NOT NULL COMMENT '类型',
    `menu_name`        varchar(200) NOT NULL COMMENT '菜单名称',
    `menu_icon`        varchar(100) NULL DEFAULT NULL COMMENT '菜单图标',
    `menu_path`        varchar(255) NULL DEFAULT NULL COMMENT '路由地址',
    `menu_hidden`      tinyint NULL DEFAULT 1 COMMENT '显示状态',
    `menu_cache`       tinyint NULL DEFAULT 0 COMMENT '是否缓存',
    `menu_status`      tinyint      NOT NULL DEFAULT 0 COMMENT '菜单状态',
    `menu_authority`   varchar(255) NULL DEFAULT NULL COMMENT '菜单权限字符串',
    `menu_sort`        int NULL DEFAULT NULL COMMENT '菜单排序',
    `view_name`        varchar(255) NULL DEFAULT NULL COMMENT '组件视图名称',
    `view_path`        varchar(255) NULL DEFAULT NULL COMMENT '组件视图路径',
    `active_menu_path` varchar(255) NULL DEFAULT NULL COMMENT '菜单显示(菜单隐藏时填写)',
    `affix_status`     tinyint NULL DEFAULT NULL COMMENT 'tag固定',
    `frame_flag`       tinyint NULL DEFAULT 0 COMMENT '是否为外链',
    `del_flag`         tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`        varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`
(
    `id`              bigint NOT NULL COMMENT '主键ID',
    `sender_id`       bigint NULL DEFAULT NULL COMMENT '发件人ID（0表示系统）',
    `sender_name`     varchar(32) NULL DEFAULT NULL COMMENT '发件人名称',
    `message_type`    tinyint NULL DEFAULT NULL COMMENT '消息类型：1-系统通知 2-业务提醒',
    `message_title`   varchar(128) NULL DEFAULT NULL COMMENT '消息标题',
    `message_content` text NULL COMMENT '消息内容（支持富文本）',
    `message_extend`  json NULL COMMENT '消息扩展信息（如关联订单ID、跳转链接）',
    `del_flag`        tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`       varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`     datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-站内信主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_message_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_info`;
CREATE TABLE `sys_message_info`
(
    `id`             bigint NOT NULL COMMENT '主键ID',
    `message_id`     bigint NULL DEFAULT NULL COMMENT '关联主表的消息唯一编号',
    `recipient_id`   bigint NULL DEFAULT NULL COMMENT '收件人ID',
    `recipient_name` varchar(32) NULL DEFAULT NULL COMMENT '收件人名称',
    `message_status` tinyint NULL DEFAULT 1 COMMENT '消息状态：1-未读 2-已读 3-已删除（收件人侧）4-已撤回（发件人侧）',
    `message_top`    tinyint NULL DEFAULT 0 COMMENT '信息置顶：0-否 1-是',
    `message_star`   tinyint NULL DEFAULT NULL COMMENT '信息收藏',
    `read_time`      datetime NULL DEFAULT NULL COMMENT '阅读时间',
    `remove_time`    datetime NULL DEFAULT NULL COMMENT '删除时间',
    `del_flag`       tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-站内信收件人明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`                   bigint UNSIGNED NOT NULL COMMENT '通知ID',
    `notice_type_id`       bigint NULL DEFAULT NULL COMMENT '通知类型（如：系统公告、活动通知等）',
    `notice_title`         varchar(200) NULL DEFAULT NULL COMMENT '通知标题',
    `notice_summary`       varchar(512) NULL DEFAULT NULL COMMENT '通知摘要',
    `notice_content`       longtext NULL COMMENT '通知内容',
    `notice_status`        tinyint UNSIGNED NULL DEFAULT 0 COMMENT '通知状态(0:未发布;1:已发布;2:已下架;3:已过期)',
    `notice_order`         int UNSIGNED NULL DEFAULT 0 COMMENT '通知排序（值越大越靠前）',
    `notice_top`           tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否置顶(0:否;1:是)',
    `notice_timed_publish` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否定时发布(0:否(立即发布);1:是(按发布时间生效))',
    `notice_publish_time`  datetime NULL DEFAULT NULL COMMENT '发布时间（正式生效时间）',
    `notice_expire_time`   datetime NULL DEFAULT NULL COMMENT '过期时间（自动失效，到期后不再展示）',
    `notice_offline_time`  datetime NULL DEFAULT NULL COMMENT '下架时间（手动操作下架的时间）',
    `notice_jump_type`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '跳转类型(0:无跳转;1:内部页面;2:外部链接)',
    `notice_jump_url`      varchar(512) NULL DEFAULT NULL COMMENT '跳转地址（内部页面路径/外部URL）',
    `notice_read_count`    int UNSIGNED NULL DEFAULT 0 COMMENT '已读人数',
    `notice_click_count`   int UNSIGNED NULL DEFAULT 0 COMMENT '点击次数',
    `notice_remark`        text NULL COMMENT '备注（通知背景、修改说明等）',
    `del_flag`             tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`            varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`            varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`          datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                  `idx_notice_status`(`notice_status` ASC) USING BTREE,
    INDEX                  `idx_notice_publish_time`(`notice_publish_time` ASC) USING BTREE,
    INDEX                  `idx_notice_top_order`(`notice_top` DESC, `notice_order` DESC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-通知详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_attachment`;
CREATE TABLE `sys_notice_attachment`
(
    `id`                   bigint UNSIGNED NOT NULL COMMENT '附件ID',
    `notice_id`            bigint UNSIGNED NOT NULL COMMENT '关联通知ID（关联sys_notice.id）',
    `attachment_name`      varchar(255) NOT NULL COMMENT '附件原始名称（文件上传时的原名，如：20260122公告.pdf）',
    `attachment_show_name` varchar(255) NULL DEFAULT NULL COMMENT '附件显示名称（自定义展示名，为空则显示原始名称）',
    `attachment_path`      varchar(512) NOT NULL COMMENT '附件存储路径（服务器绝对路径/OSS地址）',
    `attachment_size`      bigint UNSIGNED NULL DEFAULT 0 COMMENT '附件大小（单位：字节）',
    `attachment_type`      varchar(50) NULL DEFAULT NULL COMMENT '附件类型（如：pdf、doc、jpg、zip等）',
    `attachment_order`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '附件排序（值越大越靠前）',
    `del_flag`             tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`            varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`          datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`            varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`          datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                  `idx_notice_id`(`notice_id` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-通知附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_type`;
CREATE TABLE `sys_notice_type`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '通知类型',
    `notice_type_name` varchar(100) NOT NULL COMMENT '类型名称',
    `notice_type_sort` int UNSIGNED NULL DEFAULT 0 COMMENT '通知类型',
    `del_flag`         tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`        varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`        varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`      datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-通知类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_notice_user_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_user_operate`;
CREATE TABLE `sys_notice_user_operate`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `notice_id`    bigint UNSIGNED NOT NULL COMMENT '通知ID',
    `user_id`      bigint NOT NULL COMMENT '用户ID',
    `operate_type` tinyint UNSIGNED NOT NULL COMMENT '操作类型(1:阅读;2:点击)',
    `operate_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
    `del_flag`     tinyint NULL DEFAULT 0 COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_notice_user_operate`(`notice_id` ASC, `user_id` ASC, `operate_type` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-用户操作记录(防重复统计)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oauth2_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth2_client`;
CREATE TABLE `sys_oauth2_client`
(
    `id`                            bigint       NOT NULL COMMENT 'ID',
    `client_id`                     varchar(200) NOT NULL COMMENT '客户端ID',
    `client_id_issued_at`           timestamp NULL DEFAULT NULL COMMENT '客户端发布时间',
    `client_secret`                 varchar(200) NOT NULL COMMENT '客户端密钥',
    `client_secret_expires_at`      timestamp NULL DEFAULT NULL COMMENT '客户端过期时间',
    `client_name`                   varchar(200) NOT NULL COMMENT '客户端名称',
    `client_authentication_methods` varchar(1000) NULL DEFAULT NULL COMMENT '客户认证方式',
    `authorization_grant_types`     varchar(1000) NULL DEFAULT NULL COMMENT '客户端授权类型',
    `redirect_uris`                 varchar(1000) NULL DEFAULT NULL COMMENT '授权后重定向URI',
    `post_logout_redirect_uris`     varchar(1000) NULL DEFAULT NULL COMMENT '登出后重定向URI',
    `scopes`                        varchar(1000) NULL DEFAULT NULL COMMENT '客户端作用域',
    `access_token_validity`         int NULL DEFAULT NULL COMMENT '请求令牌有效时间',
    `refresh_token_validity`        int NULL DEFAULT NULL COMMENT '刷新令牌有效时间',
    `additional_information`        varchar(2000) NULL DEFAULT NULL COMMENT '扩展信息',
    `auto_approve`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否自动放行',
    `remark`                        varchar(200) NULL DEFAULT NULL COMMENT '备注',
    `del_flag`                      tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`                     varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`                   datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`                     varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`                   datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-客户端管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `id`          bigint      NOT NULL COMMENT '岗位ID',
    `dept_id`     bigint      NOT NULL COMMENT '部门id',
    `post_code`   varchar(64) NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) NOT NULL COMMENT '岗位名称',
    `post_sort`   int         NOT NULL COMMENT '岗位排序',
    `post_status` tinyint NULL DEFAULT NULL COMMENT '岗位状态',
    `post_limit`  int NULL DEFAULT NULL COMMENT '岗位限制人数',
    `post_have`   int NULL DEFAULT NULL COMMENT '岗位已分配人数',
    `system_flag` tinyint NULL DEFAULT NULL COMMENT '系统内置(0:是;1不是)',
    `remark`      varchar(500) NULL DEFAULT NULL COMMENT '岗位描述',
    `del_flag`    tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`   varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint NOT NULL COMMENT '主键',
    `role_code`   varchar(50) NULL DEFAULT NULL COMMENT '角色编码',
    `role_name`   varchar(50) NULL DEFAULT NULL COMMENT '角色名称',
    `data_scope`  tinyint NULL DEFAULT NULL COMMENT '数据范围(1:全部数据权限;2:自定数据权限;3:本部门数据权限;4:本部门及以下数据权限;5:本岗位数据权限;6:仅本人数据权限)',
    `role_status` tinyint NULL DEFAULT NULL COMMENT '状态(0:正常;1:停用)',
    `role_sort`   int NULL DEFAULT NULL COMMENT '显示顺序',
    `remark`      varchar(100) NULL DEFAULT NULL COMMENT '角色描述',
    `del_flag`    tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`   varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id`     bigint NOT NULL COMMENT '角色id',
    `menu_id`     bigint NOT NULL COMMENT '菜单id',
    `create_by`   varchar(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`             bigint UNSIGNED NOT NULL COMMENT '用户ID',
    `user_type`      tinyint NULL DEFAULT NULL COMMENT '用户类型',
    `user_name`      varchar(50)  NOT NULL COMMENT '用户账号',
    `nick_name`      varchar(15)  NOT NULL COMMENT '用户昵称',
    `pass_word`      varchar(200) NOT NULL COMMENT '用户密码',
    `pass_word_salt` varchar(10) NULL DEFAULT NULL COMMENT '密码盐值',
    `user_status`    tinyint      NOT NULL DEFAULT 1 COMMENT '账号状态',
    `user_phone`     varchar(11) NULL DEFAULT NULL COMMENT '手机号码',
    `user_avatar`    varchar(200) NULL DEFAULT NULL COMMENT '头像地址',
    `dept_id`        bigint NULL DEFAULT NULL COMMENT '部门id',
    `dept_name`      varchar(255) NULL DEFAULT NULL COMMENT '部门名称',
    `del_flag`       tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`      varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_user_account`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_detail`;
CREATE TABLE `sys_user_detail`
(
    `user_id`           bigint NOT NULL COMMENT '用户ID',
    `real_name`         varchar(32) NULL DEFAULT NULL COMMENT '真实姓名',
    `id_card`           varchar(18) NULL DEFAULT NULL COMMENT '身份证号',
    `gender`            tinyint NULL DEFAULT NULL COMMENT '用户性别',
    `birth_date`        date NULL DEFAULT NULL COMMENT '出生日期',
    `age`               tinyint NULL DEFAULT NULL COMMENT '用户年龄',
    `address`           varchar(100) NULL DEFAULT NULL COMMENT '用户地址',
    `emergency_contact` varchar(32) NULL DEFAULT NULL COMMENT '紧急联系人',
    `emergency_phone`   varchar(11) NULL DEFAULT NULL COMMENT '紧急联系人电话',
    `nation`            varchar(20) NULL DEFAULT NULL COMMENT '用户民族',
    `del_flag`          tinyint NULL DEFAULT 0 COMMENT '删除标识(0:未删除;1:已删除)',
    `create_by`         varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(32) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`       datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
    UNIQUE INDEX `idx_id_card`(`id_card` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `id`              bigint NOT NULL COMMENT 'id',
    `position_nature` tinyint NULL DEFAULT NULL COMMENT '岗位性质',
    `user_id`         bigint NOT NULL COMMENT '用户id',
    `post_id`         bigint NOT NULL COMMENT '部门岗位id',
    `create_by`       varchar(32) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`, `user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-用户岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id`     bigint NOT NULL COMMENT '用户id',
    `role_id`     bigint NOT NULL COMMENT '角色id',
    `create_by`   varchar(64) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id` DESC, `role_id`) USING BTREE,
    UNIQUE INDEX `sys_user_role`(`role_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统管理-用户角色关联表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
