SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;
USE
`xht_platform`;
-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept`
VALUES (1, 0, 'DEP001', '总公司', 1, 0, 1, '0', '13800138000', 'company@example.com', NULL, 0, 'admin',
        '2025-09-15 14:46:19', 'admin', '2025-09-15 14:46:19');
INSERT INTO `sys_dept`
VALUES (2, 1, 'DEP002', '技术部', 2, 0, 1, '0,1', '13800138001', 'tech@example.com', NULL, 0, 'admin',
        '2025-09-15 14:46:19', 'admin', '2025-09-15 14:46:19');
INSERT INTO `sys_dept`
VALUES (3, 1, 'DEP003', '市场部', 2, 0, 2, '0,1', '13800138002', 'market@example.com', NULL, 0, 'admin',
        '2025-09-15 14:46:19', 'admin', '2025-09-15 14:46:19');
INSERT INTO `sys_dept`
VALUES (4, 2, 'DEP004', '前端开发组', 3, 0, 1, '0,1,2', '13800138003', 'frontend@example.com', NULL, 0, 'admin',
        '2025-09-15 14:46:19', 'admin', '2025-09-15 14:46:19');
INSERT INTO `sys_dept`
VALUES (5, 2, 'DEP005', '后端开发组', 3, 0, 2, '0,1,2', '13800138004', 'backend@example.com', NULL, 0, 'admin',
        '2025-09-15 14:46:19', 'admin', '2025-09-15 14:46:19');
-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict`
VALUES (1, 'gender', '性别', 1, '用户性别字典', 1, NULL, 0, 'admin', '2025-09-15 14:46:19', 'admin',
        '2025-09-15 14:46:19');
INSERT INTO `sys_dict`
VALUES (2, 'user_status', '用户状态', 2, '用户账号状态', 1, NULL, 0, 'admin', '2025-09-15 14:46:19', 'admin',
        '2025-09-15 14:46:19');
INSERT INTO `sys_dict`
VALUES (3, 'order_status', '订单状态', 3, '订单流程状态', 1, NULL, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict`
VALUES (4, 'education', '学历', 4, '学历等级', 1, NULL, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict`
VALUES (5, 'yes_no', '是否', 5, '是否选择', 1, NULL, 0, 'admin', '2025-09-15 14:46:20', 'admin', '2025-09-15 14:46:20');
INSERT INTO `sys_dict`
VALUES (6, 'D001', '示例字典', 1, '这是一个示例字典', 1, NULL, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict`
VALUES (7, '12312', '123123', 0, '12312', 1, NULL, 0, 'admin', '2025-09-15 14:46:20', 'admin', '2025-09-15 14:46:20');
-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item`
VALUES (1, 1, 'gender', '男', '1', '#1E90FF', 1, '男性', 1, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict_item`
VALUES (2, 1, 'gender', '女', '2', '#FF4500', 2, '女性', 1, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict_item`
VALUES (3, 1, 'gender', '未知', '0', '#F2F6FC', 3, '性别未知', 1, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict_item`
VALUES (4, 2, 'user_status', '正常', '1', NULL, 1, '正常状态', 1, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict_item`
VALUES (5, 2, 'user_status', '锁定', '2', NULL, 2, '账号被锁定', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (6, 2, 'user_status', '禁用', '0', NULL, 3, '账号被禁用', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (7, 3, 'order_status', '待付款', '1', NULL, 1, '等待用户付款', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (8, 3, 'order_status', '已付款', '2', NULL, 2, '已付款待发货', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (9, 3, 'order_status', '已发货', '3', NULL, 3, '已发货待收货', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (10, 3, 'order_status', '已完成', '4', NULL, 4, '交易已完成', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (11, 4, 'education', '小学', '1', NULL, 1, '小学学历', 0, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (12, 4, 'education', '初中', '2', NULL, 2, '初中学历', 0, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (13, 4, 'education', '高中', '3', NULL, 3, '高中学历', 0, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (14, 4, 'education', '本科', '4', NULL, 4, '本科学历', 0, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (15, 5, 'yes_no', '是', '1', NULL, 1, '是', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (16, 5, 'yes_no', '否', '0', NULL, 2, '否', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, 0, 'M', '首页', 'dashboard', '/', 1, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin',
        '2026-01-03 01:01:05', 'admin', '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (2, 1, 'C', '工作台', 'admin', '/home/admin', 1, 0, 0, NULL, 1, 'HomeIndex', '/views/dashboard/admin/index.vue',
        '', 1, 0, 0, 'admin', '2026-01-03 01:01:05', 'admin', '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (3, 1, 'C', '个人工作台', 'dashboard', '/home', 1, 0, 0, NULL, 2, 'HomeIndex2',
        '/views/dashboard/common/index.vue', '', 1, 0, 0, 'admin', '2026-01-03 01:01:05', 'admin',
        '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (4, 1, 'C', '用户中心', 'role', '/home/user/admin', 0, 1, 0, NULL, 3, 'UserAdmin',
        '/views/dashboard/user-admin/index.vue', '/home/admin', 0, 0, 0, 'admin', '2026-01-03 01:01:05', 'admin',
        '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (5, 1, 'C', '用户中心', 'user', '/home/user', 0, 0, 0, NULL, 4, 'UserCommon',
        '/views/dashboard/user-common/index.vue', '/home/user', 0, 0, 0, 'admin', '2026-01-03 01:01:05', 'admin',
        '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (6, 0, 'M', '系统管理', 'system', '/system', 1, NULL, 0, NULL, 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin',
        '2026-01-03 01:01:05', 'admin', '2026-01-03 01:01:05');
INSERT INTO `sys_menu`
VALUES (7, 6, 'C', '用户管理', 'user', '/system/user', 1, 1, 0, NULL, 1, 'UserView', '/views/system/user/index.vue',
        NULL, 0, 0, 0, 'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (8, 7, 'B', '用户增加', NULL, NULL, NULL, NULL, 0, 'sys:user:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (9, 7, 'B', '用户密码修改', NULL, NULL, NULL, NULL, 0, 'sys:user:pwd', 2, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (10, 7, 'B', '用户删除', NULL, NULL, NULL, NULL, 0, 'sys:user:remove', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (11, 7, 'B', '用户角色绑定', NULL, NULL, NULL, NULL, 0, 'sys:user:role:bind', 4, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (12, 7, 'B', '用户修改', NULL, NULL, NULL, NULL, 0, 'sys:user:update', 5, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (13, 6, 'C', '角色管理', 'role', '/system/role', 1, 1, 0, NULL, 2, 'RoleView', '/views/system/role/index.vue',
        NULL, 0, 0, 0, 'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (14, 13, 'B', '角色增加', NULL, NULL, NULL, NULL, 0, 'sys:role:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (15, 13, 'B', '角色菜单绑定', NULL, NULL, NULL, NULL, 0, 'sys:role:menu:bind', 2, NULL, NULL, NULL, NULL, NULL,
        0, 'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (16, 13, 'B', '角色删除', NULL, NULL, NULL, NULL, 0, 'sys:role:remove', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (17, 13, 'B', '角色修改', NULL, NULL, NULL, NULL, 0, 'sys:role:update', 4, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (18, 6, 'C', '菜单管理', 'menu', '/system/menu', 1, 1, 0, NULL, 3, 'MenuView', '/views/system/menu/index.vue',
        NULL, 0, 0, 0, 'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (19, 18, 'B', '菜单增加', NULL, NULL, NULL, NULL, 0, 'sys:menu:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (20, 18, 'B', '菜单删除', NULL, NULL, NULL, NULL, 0, 'sys:menu:remove', 2, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (21, 18, 'B', '菜单修改', NULL, NULL, NULL, NULL, 0, 'sys:menu:update', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:06', 'admin', '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (22, 6, 'C', '部门管理', 'tree', '/system/dept', 1, 1, 0, NULL, 4, 'SysDeptViewIndex',
        '/views/system/dept/index.vue', '/system/dept', 0, 0, 0, 'admin', '2026-01-03 01:01:06', 'admin',
        '2026-01-03 01:01:06');
INSERT INTO `sys_menu`
VALUES (23, 22, 'B', '部门增加', NULL, NULL, NULL, NULL, 0, 'sys:dept:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (24, 22, 'B', '部门删除', NULL, NULL, NULL, NULL, 0, 'sys:dept:remove', 2, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (25, 22, 'B', '部门修改', NULL, NULL, NULL, NULL, 0, 'sys:dept:update', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (26, 6, 'C', '岗位管理', 'post', '/system/post', 1, 1, 0, NULL, 5, 'SysDeptPostIndex',
        '/views/system/dept-post/index.vue', NULL, 0, 0, 0, 'admin', '2026-01-03 01:01:07', 'admin',
        '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (27, 26, 'B', '岗位增加', NULL, NULL, NULL, NULL, 0, 'sys:post:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (28, 26, 'B', '岗位删除', NULL, NULL, NULL, NULL, 0, 'sys:post:remove', 2, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (29, 26, 'B', '岗位修改', NULL, NULL, NULL, NULL, 0, 'sys:post:update', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (30, 6, 'C', '字典管理', 'dict', '/system/dict', 1, 1, 0, NULL, 6, 'SysDictIndex',
        '/views/system/dict/index.vue', NULL, 0, 0, 0, 'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (31, 30, 'B', '字典增加', NULL, NULL, NULL, NULL, 0, 'sys:dict:create', 1, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (32, 30, 'B', '字典删除', NULL, NULL, NULL, NULL, 0, 'sys:dict:remove', 2, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (33, 30, 'B', '字典修改', NULL, NULL, NULL, NULL, 0, 'sys:dict:update', 3, NULL, NULL, NULL, NULL, NULL, 0,
        'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (34, 6, 'C', '字典项管理', 'dict', '/system/dict/:id', 0, 1, 0, NULL, 7, 'SysDictItemIndex',
        '/views/system/dict-item/index.vue', '/system/dict', 0, 0, 0, 'admin', '2026-01-03 01:01:07', 'admin',
        '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (35, 34, 'B', '字典项增加', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:create', 1, NULL, NULL, NULL, NULL, NULL,
        0, 'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (36, 34, 'B', '字典项删除', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:remove', 2, NULL, NULL, NULL, NULL, NULL,
        0, 'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (37, 34, 'B', '字典项修改', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:update', 3, NULL, NULL, NULL, NULL, NULL,
        0, 'admin', '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (38, 0, 'M', '监控中心', 'monitor', '/monitor', 1, NULL, 0, NULL, 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin',
        '2026-01-03 01:01:07', 'admin', '2026-01-03 01:01:07');
INSERT INTO `sys_menu`
VALUES (39, 38, 'C', '操作日志', 'monitor', '/monitor/log', 1, 1, 0, NULL, 1, 'LogView', '/views/monitor/log/index.vue',
        '/monitor/log', 0, 0, 0, 'admin', '2026-01-03 01:01:08', 'admin', '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (40, 0, 'M', '系统工具', 'system-tools', '/tool', 1, NULL, 0, NULL, 4, NULL, NULL, NULL, NULL, NULL, 0, 'admin',
        '2026-01-03 01:01:08', 'admin', '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (41, 40, 'C', '数据源', 'database', '/tool/datasource', 1, 1, 0, NULL, 1, 'GenDataSourceViewIndex',
        '/views/generate/datasource/index.vue', '/tool/datasource', 0, 0, 0, 'admin', '2026-01-03 01:01:08', 'admin',
        '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (42, 40, 'C', '类型映射', 'column', '/tool/type/mapping', 1, 1, 0, NULL, 2, 'GenTypeMappingViewIndex',
        '/views/generate/type-mapping/index.vue', '/tool/type/mapping', 0, 0, 0, 'admin', '2026-01-03 01:01:08',
        'admin', '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (43, 40, 'C', '模板管理', 'template', '/tool/template', 1, 1, 0, NULL, 3, 'GenTemplateViewIndex',
        '/views/generate/template/index.vue', '/tool/template', 0, 0, 0, 'admin', '2026-01-03 01:01:08', 'admin',
        '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (44, 40, 'C', '表管理', 'database-table', '/tool/table', 1, 1, 0, NULL, 4, 'GenTableInfoViewIndex',
        '/views/generate/table/index.vue', '/tool/table', 0, 0, 0, 'admin', '2026-01-03 01:01:08', 'admin',
        '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (45, 0, 'M', '外部链接', 'guide', '/web', 1, NULL, 0, NULL, 5, NULL, NULL, NULL, NULL, NULL, 0, 'admin',
        '2026-01-03 01:01:08', 'admin', '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (46, 45, 'C', '接口文档', 'monitor', 'http://localhost:8080/doc.html', 1, 0, 0, NULL, 1, NULL, NULL, NULL, 0, 1,
        0, 'admin', '2026-01-03 01:01:08', 'admin', '2026-01-03 01:01:08');
INSERT INTO `sys_menu`
VALUES (47, 45, 'C', '文档中心', 'monitor', 'https://xhtcode.github.io/xht-cloud-doc/', 1, 0, 0, NULL, 2, NULL, NULL,
        NULL, 0, 1, 0, 'admin', '2026-01-03 01:01:08', 'admin', '2026-01-03 01:01:08');
-- ----------------------------
-- Records of sys_oauth2_client
-- ----------------------------
INSERT INTO `sys_oauth2_client`
VALUES (1, 'oidc-client', '2025-07-19 16:14:53', '5ebe2294ecd0e0f08eab7690d2a6ee69', '2035-09-01 16:15:05',
        'oidc-client', '[\"client_secret_basic\"]',
        '[\"refresh_token\",\"password\",\"client_credentials\",\"authorization_code\",\"phone\"]',
        '[\"http://127.0.0.1:3000/login\",\"https://www.baidu.com\"]', NULL, '[\"user_info\",\"openid\",\"profile\"]',
        10000, 10000, '{}', NULL, NULL, 0, 'admin', '2025-09-15 14:46:25', 'admin', '2025-09-15 14:46:25');
-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post`
VALUES (1926558918036635656, 1, 'P001', '总经理', 1, 0, 1, 0, 0, '公司最高负责人', 0, 'admin', '2025-09-15 14:46:26',
        'admin', '2025-09-15 14:46:26');
INSERT INTO `sys_post`
VALUES (1926558918036635657, 2, 'P002', '技术总监', 1, 0, 1, 0, 0, '技术部门负责人', 0, 'admin', '2025-09-15 14:46:26',
        'admin', '2025-09-15 14:46:26');
INSERT INTO `sys_post`
VALUES (1926558918036635658, 3, 'P003', '前端开发工程师', 2, 0, 5, 0, 0, '负责前端开发工作', 0, 'admin',
        '2025-09-15 14:46:26', 'admin', '2025-09-15 14:46:26');
INSERT INTO `sys_post`
VALUES (1926558918036635659, 4, 'P004', '后端开发工程师', 2, 0, 5, 0, 0, '负责后端开发工作', 0, 'admin',
        '2025-09-15 14:46:26', 'admin', '2025-09-15 14:46:26');
INSERT INTO `sys_post`
VALUES (1926558918036635660, 5, 'P005', '市场专员', 1, 0, 3, 0, 0, '负责市场推广工作', 0, 'admin',
        '2025-09-15 14:46:26', 'admin', '2025-09-15 14:46:26');
-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, 'admin', '系统管理员', 1, 0, 1, '拥有系统所有权限', 0, 'admin', '2025-09-15 14:46:26', 'admin',
        '2025-09-15 14:46:26');
INSERT INTO `sys_role`
VALUES (2, 'user', '普通用户', 1, 0, 2, '基础用户权限', 0, 'admin', '2025-09-15 14:46:27', 'admin',
        '2025-09-15 14:46:27');
INSERT INTO `sys_role`
VALUES (3, 'audit', '审计员', 1, 0, 3, '查看系统日志和审计信息', 0, 'admin', '2025-09-15 14:46:27', 'admin',
        '2025-09-15 14:46:27');
-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu`
VALUES (1, 1, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 3, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 4, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 5, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 6, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 7, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 8, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 9, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 10, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 11, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 12, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 13, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 14, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 15, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 16, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 17, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 18, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 19, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 20, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 21, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 22, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 23, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 24, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 25, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 26, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 27, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 28, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 29, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 30, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 31, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 32, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 33, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 34, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 35, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 36, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 37, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 38, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 39, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 40, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 41, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 42, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 43, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 44, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 45, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2007087172918726658, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2007087659608985602, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2007087659608985603, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2007095192637747202, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (1, 2007095192637747203, 'admin', '2026-01-02 22:50:08');
INSERT INTO `sys_role_menu`
VALUES (10, 1, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 2, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 3, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 4, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 5, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 6, 'admin', '2025-09-15 14:46:27');
INSERT INTO `sys_role_menu`
VALUES (10, 7, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 8, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 9, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 10, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 11, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 12, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 13, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 14, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 18, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 19, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 20, 'admin', '2025-09-15 14:46:28');
INSERT INTO `sys_role_menu`
VALUES (10, 258, 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_role_menu`
VALUES (10, 259, 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_role_menu`
VALUES (10, 264, 'admin', '2025-09-15 14:46:29');
-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (108, 1, 'admin', '系统管理员', 'ea48576f30be1669971699c09ad05c94', '123456', 0, '13812345678', NULL, 1, NULL, 0,
        'admin', '2025-09-15 14:46:29', 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (109, 2, 'zhang_san', '张三', 'ea48576f30be1669971699c09ad05c94', '123456', 1, '13812345679', NULL, 2, NULL, 0,
        'admin', '2025-09-15 14:46:29', 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (110, 2, 'li_si', '李四', 'ea48576f30be1669971699c09ad05c94', '123456', 2, '13812345680', NULL, 4, NULL, 0,
        'admin', '2025-09-15 14:46:29', 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (111, 2, 'wang_wu', '王五', 'ea48576f30be1669971699c09ad05c94', '123456', 3, '13812345681', NULL, 5, NULL, 0,
        'admin', '2025-09-15 14:46:29', 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (112, 2, 'zhao_liu', '赵六', 'ea48576f30be1669971699c09ad05c94', '123456', 4, '13812345682', NULL, 3, NULL, 0,
        'admin', '2025-09-15 14:46:29', 'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (1986700829652856832, 3, 'a06e2a0770094cd2a0874a8979558cad', '13112312312', 'ea48576f30be1669971699c09ad05c94',
        '123456', 1, '13112312312', NULL, NULL, NULL, 0, NULL, '2025-11-07 15:42:44', NULL, '2025-11-07 15:42:44');
-- ----------------------------
-- Records of sys_user_detail
-- ----------------------------
INSERT INTO `sys_user_detail`
VALUES (108, '系统管理员', '331081200701147067', 1, '1990-01-01', 35, '北京市海淀区', '张三', '13112312312', '汉', 0,
        'admin', '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (109, '张三', '110101199202022345', 1, '1992-02-02', 33, '北京市朝阳区', '张三', '13112312312', '汉', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (110, '李四', '110101199503033456', 1, '1995-03-03', 30, '北京市西城区', '张三', '13112312312', '汉', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (111, '王五', '110101199804044567', 1, '1998-04-04', 27, '北京市东城区', '张三', '13112312312', '汉', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (112, '赵六', '110101200005055678', 2, '2000-05-05', 25, '北京市丰台区', '张三', '13112312312', '汉', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (1986700829652856832, NULL, '32118120070720306X', 2, '2007-07-20', 18, ':江苏省 镇江市 丹阳市', '张三',
        '13112312312', '汉', 0, 'admin', '2025-11-07 15:42:44', 'admin', '2025-11-07 15:42:44');
-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post`
VALUES (1, 1, 108, 1926558918036635656, 'admin', '2025-09-15 14:46:30');
INSERT INTO `sys_user_post`
VALUES (2, 1, 109, 1926558918036635657, 'admin', '2025-09-15 14:46:30');
INSERT INTO `sys_user_post`
VALUES (3, 2, 110, 1926558918036635658, 'admin', '2025-09-15 14:46:30');
INSERT INTO `sys_user_post`
VALUES (4, 2, 111, 1926558918036635659, 'admin', '2025-09-15 14:46:30');
INSERT INTO `sys_user_post`
VALUES (5, 2, 112, 1926558918036635660, 'admin', '2025-09-15 14:46:30');

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES (108, 1, 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_role`
VALUES (108, 2, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 3, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 4, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 5, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 6, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 7, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 8, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 9, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 10, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 11, 'admin', '2025-09-15 14:46:32');
INSERT INTO `sys_user_role`
VALUES (108, 12, 'admin', '2025-09-15 14:46:33');
INSERT INTO `sys_user_role`
VALUES (108, 13, 'admin', '2025-09-15 14:46:33');
INSERT INTO `sys_user_role`
VALUES (5, 2, 'admin', '2025-09-15 14:46:33');
INSERT INTO `sys_user_role`
VALUES (4, 2, 'admin', '2025-09-15 14:46:33');
INSERT INTO `sys_user_role`
VALUES (3, 2, 'admin', '2025-09-15 14:46:33');
INSERT INTO `sys_user_role`
VALUES (2, 1, 'admin', '2025-09-15 14:46:33');


SET
FOREIGN_KEY_CHECKS = 1;