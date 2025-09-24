-- 代码生成器数据库表结构设计（最终完整版本）
-- 表名gen_前缀+无外键+无自定义索引+支持自定义配置

-- 1. 项目表（gen_project）
CREATE TABLE `gen_project`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `name`          varchar(100) NOT NULL COMMENT '项目名称',
    `description`   varchar(500) DEFAULT NULL COMMENT '项目描述',
    `config_schema` json         DEFAULT null comment '配置元数据（定义控件类型/标签/选项等）',
    `custom_config` json         DEFAULT null comment '自定义配置值（与config_schema对应）',
    `create_by`     varchar(65)  default null comment '创建者',
    `create_time`   datetime     default null comment '创建时间',
    `update_by`     varchar(65)  default null comment '更新者',
    `update_time`   datetime     default null comment '更新时间',
    `del_flag`        tinyint      default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_project_name` (`name`) comment '项目名称唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-代码生成项目表（支持自定义配置）';

-- 2. 模板表（gen_template）
create table `gen_template`
(
    `id`                 bigint       not null auto_increment comment '模板ID',
    `name`               varchar(100) not null comment '模板名称',
    `content`            text         not null comment '模板内容（Velocity语法）',
    `file_type`          varchar(20)  not null comment '文件类型（java/ts/vue/xml/sql等）',
    `file_path_template` varchar(500) not null comment '文件路径模板（如：src/main/java/{{package}}）',
    `file_name_template` varchar(100) not null comment '文件名模板（如：{{className}}Controller.java）',
    `create_by`          varchar(65) default null comment '创建者',
    `create_time`        datetime    default null comment '创建时间',
    `update_by`          varchar(65) default null comment '更新者',
    `update_time`        datetime    default null comment '更新时间',
    `del_flag`             tinyint     default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_template_name_type` (`name`, `file_type`) comment '同文件类型的模板名称唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-代码生成模板表（区分文件类型）';

-- 3. 项目-模板关联表（gen_project_template）
create table `gen_project_template`
(
    `id`          bigint not null auto_increment comment '关联ID',
    `project_id`  bigint not null comment '项目ID',
    `template_id` bigint not null comment '模板ID',
    `sort`        int default 0 comment '模板排序',
    primary key (`id`),
    unique key `uk_gen_project_template` (`project_id`, `template_id`) comment '项目与模板关联唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-项目与模板关联表';

-- 4. 数据源表（gen_data_source）
create table `gen_data_source`
(
    `id`             bigint       not null auto_increment comment '数据源ID',
    `name`           varchar(100) not null comment '数据源名称',
    `db_type`        varchar(20)  not null comment '数据库类型（MySQL/Oracle）',
    `host`           varchar(100) not null comment '数据库地址',
    `port`           varchar(10)  not null comment '端口号',
    `database_name`  varchar(100) not null comment '数据库名称',
    `username`       varchar(50)  not null comment '用户名',
    `password`       varchar(255) not null comment '加密存储的密码（AES加密）',
    `test_result`    varchar(20) default null comment '连接测试结果（success/fail）',
    `last_test_time` datetime    default null comment '最后测试时间',
    `create_by`      varchar(65) default null comment '创建者',
    `create_time`    datetime    default null comment '创建时间',
    `update_by`      varchar(65) default null comment '更新者',
    `update_time`    datetime    default null comment '更新时间',
    `del_flag`         tinyint     default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_ds_name` (`name`) comment '数据源名称唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-数据源配置表';

-- 5. 项目-数据源关联表（gen_project_data_source）
create table `gen_project_data_source`
(
    `id`             bigint not null auto_increment comment '关联ID',
    `project_id`     bigint not null comment '项目ID',
    `data_source_id` bigint not null comment '数据源ID',
    `is_default`     tinyint default 0 comment '是否默认数据源（0否 1是）',
    primary key (`id`),
    unique key `uk_gen_project_ds` (`project_id`, `data_source_id`) comment '项目与数据源关联唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-项目与数据源关联表';

-- 6. 类型映射表（gen_type_mapping）
create table `gen_type_mapping`
(
    `id`               bigint      not null auto_increment comment '映射ID',
    `db_type`          varchar(20) not null comment '数据库类型（MySQL/Oracle）',
    `db_data_type`     varchar(50) not null comment '数据库数据类型（如：INT/VARCHAR2）',
    `target_language`  varchar(50) not null comment '目标编程语言（Java/TypeScript）',
    `target_data_type` varchar(50) not null comment '目标语言数据类型（如：Integer/number）',
    `import_package`   varchar(255) default null comment '导入包路径（如：java.time.LocalDateTime）',
    `create_by`        varchar(65)  default null comment '创建者',
    `create_time`      datetime     default null comment '创建时间',
    `update_by`        varchar(65)  default null comment '更新者',
    `update_time`      datetime     default null comment '更新时间',
    `del_flag`           tinyint      default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_type_mapping` (`db_type`, `db_data_type`, `target_language`) comment '同数据库类型+数据类型+目标语言映射唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-数据类型映射表';

-- 7. 表信息表（gen_table_info）【已移除is_selected字段】
create table `gen_table_info`
(
    `id`             bigint       not null auto_increment comment '表ID',
    `project_id`     bigint       not null comment '项目ID',
    `data_source_id` bigint       not null comment '数据源ID',
    `table_name`     varchar(100) not null comment '数据库表名',
    `table_comment`  varchar(500) default null comment '表注释（如：用户表）',
    `primary_key`    varchar(100) default null comment '主键字段名',
    `code_name`      varchar(100) default null comment '生成的类名（如：User）',
    `code_comment`   varchar(100) default null comment '代码的注释（如：用户）',
    `create_by`      varchar(65)  default null comment '创建者',
    `create_time`    datetime     default null comment '创建时间',
    `update_by`      varchar(65)  default null comment '更新者',
    `update_time`    datetime     default null comment '更新时间',
    `del_flag`         tinyint      default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_table_project_ds` (`project_id`, `data_source_id`, `table_name`) comment '项目+数据源+表名唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-表结构信息表';

-- 8. 字段信息表（gen_column_info）
create table `gen_column_info`
(
    `id`             bigint       not null auto_increment comment '字段ID',
    `table_id`       bigint       not null comment '表ID（关联gen_table_info）',
    `column_name`    varchar(100) not null comment '数据库字段名',
    `db_data_type`   varchar(50)  not null comment '数据库字段类型',
    `column_comment` varchar(500) default null comment '字段注释',
    `ext_config`     json         default null comment '字段自定义配置（JSON格式），示例：{"codeFieldName":"userId","codeFieldLabel":"用户ID","isQuery":1,"queryType":"eq","isList":1,"isAdd":1,"isEdit":1,"isView":1}',
    `default_value`  varchar(255) default null comment '字段默认值',
    `is_required`    tinyint      default 0 comment '是否必填（0否 1是）',
    `is_primary`     tinyint      default 0 comment '是否主键（0否 1是）',
    `is_foreign`     tinyint      default 0 comment '是否外键（0否 1是）',
    `foreign_table`  varchar(100) default null comment '外键关联表名',
    `foreign_column` varchar(100) default null comment '外键关联字段名',
    `sort_order`     int          default 0 comment '字段排序',
    `create_by`      varchar(65)  default null comment '创建者',
    `create_time`    datetime     default null comment '创建时间',
    `update_by`      varchar(65)  default null comment '更新者',
    `update_time`    datetime     default null comment '更新时间',
    `del_flag`         tinyint      default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`),
    unique key `uk_gen_column_table` (`table_id`, `column_name`) comment '表内字段名唯一'
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-表字段信息表（支持扩展配置）';

-- 9. 代码生成历史表（gen_generate_history）
create table `gen_generate_history`
(
    `id`            bigint      not null auto_increment comment '历史记录ID',
    `project_id`    bigint      not null comment '项目ID',
    `batch_no`      varchar(50) not null comment '生成批次号',
    `generate_time` datetime    not null comment '生成时间',
    `file_count`    int          default 0 comment '生成文件数量',
    `generate_path` varchar(500) default null comment '本地生成路径',
    `template_ids`  varchar(500) default null comment '使用的模板ID（逗号分隔）',
    `table_ids`     varchar(500) default null comment '生成的表ID（逗号分隔）',
    `status`        varchar(20) not null comment '生成状态（success/fail）',
    `error_msg`     text comment '错误信息（失败时记录）',
    `create_by`     varchar(65)  default null comment '创建者',
    `create_time`   datetime     default null comment '创建时间',
    `update_by`     varchar(65)  default null comment '更新者',
    `update_time`   datetime     default null comment '更新时间',
    `del_flag`        tinyint      default 0 comment '删除标志(0正常 1删除)',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='代码生成器-代码生成历史记录表';
