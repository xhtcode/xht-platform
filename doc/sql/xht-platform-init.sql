SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;
USE
    `xht_platform`;
-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area`
VALUES (0, '000000', '-1', '中国', '000000', '1', '1', 0, '1', 0, NULL, NULL, NULL, NULL);
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
VALUES (1, 'user_status', '用户状态', 1, '用户账号状态', 1, 0, 0, 'admin', '2025-09-15 14:46:19', 'admin',
        '2025-09-15 14:46:19');
INSERT INTO `sys_dict`
VALUES (2, 'nation', '民族', 2, '民族编码字典（国标 GB/T 3304-2022 中国各民族名称和代码）', 1, 0, 0, 'admin',
        '2026-11-19 18:35:54', 'admin', '2026-11-19 18:35:54');
-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item`
VALUES (1, 1, 'user_status', '正常', '1', '#409eff', 1, '正常', 1, 0, 'admin', '2025-09-15 14:46:20', 'admin',
        '2025-09-15 14:46:20');
INSERT INTO `sys_dict_item`
VALUES (2, 1, 'user_status', '锁定', '2', '#e6a23c', 2, '账号被锁定', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (3, 1, 'user_status', '禁用', '0', '#f56c6c', 3, '账号被禁用', 1, 0, 'admin', '2025-09-15 14:46:21', 'admin',
        '2025-09-15 14:46:21');
INSERT INTO `sys_dict_item`
VALUES (6, 2, 'nation', '汉族', '01', NULL, 1, '汉族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (7, 2, 'nation', '蒙古族', '02', NULL, 2, '蒙古族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (8, 2, 'nation', '回族', '03', NULL, 3, '回族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (9, 2, 'nation', '藏族', '04', NULL, 4, '藏族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (10, 2, 'nation', '维吾尔族', '05', NULL, 5, '维吾尔族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (11, 2, 'nation', '苗族', '06', NULL, 6, '苗族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (12, 2, 'nation', '彝族', '07', NULL, 7, '彝族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (13, 2, 'nation', '壮族', '08', NULL, 8, '壮族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (14, 2, 'nation', '布依族', '09', NULL, 9, '布依族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (15, 2, 'nation', '朝鲜族', '10', NULL, 10, '朝鲜族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (16, 2, 'nation', '满族', '11', NULL, 11, '满族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (17, 2, 'nation', '侗族', '12', NULL, 12, '侗族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (18, 2, 'nation', '瑶族', '13', NULL, 13, '瑶族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (19, 2, 'nation', '白族', '14', NULL, 14, '白族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (20, 2, 'nation', '土家族', '15', NULL, 15, '土家族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (21, 2, 'nation', '哈尼族', '16', NULL, 16, '哈尼族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (22, 2, 'nation', '哈萨克族', '17', NULL, 17, '哈萨克族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (23, 2, 'nation', '傣族', '18', NULL, 18, '傣族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (24, 2, 'nation', '黎族', '19', NULL, 19, '黎族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (25, 2, 'nation', '傈僳族', '20', NULL, 20, '傈僳族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (26, 2, 'nation', '佤族', '21', NULL, 21, '佤族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (27, 2, 'nation', '畲族', '22', NULL, 22, '畲族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (28, 2, 'nation', '高山族', '23', NULL, 23, '高山族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (29, 2, 'nation', '拉祜族', '24', NULL, 24, '拉祜族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (30, 2, 'nation', '水族', '25', NULL, 25, '水族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (31, 2, 'nation', '东乡族', '26', NULL, 26, '东乡族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (32, 2, 'nation', '纳西族', '27', NULL, 27, '纳西族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (33, 2, 'nation', '景颇族', '28', NULL, 28, '景颇族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (34, 2, 'nation', '柯尔克孜族', '29', NULL, 29, '柯尔克孜族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (35, 2, 'nation', '土族', '30', NULL, 30, '土族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (36, 2, 'nation', '达斡尔族', '31', NULL, 31, '达斡尔族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (37, 2, 'nation', '仫佬族', '32', NULL, 32, '仫佬族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (38, 2, 'nation', '羌族', '33', NULL, 33, '羌族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (39, 2, 'nation', '布朗族', '34', NULL, 34, '布朗族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (40, 2, 'nation', '撒拉族', '35', NULL, 35, '撒拉族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (41, 2, 'nation', '毛南族', '36', NULL, 36, '毛南族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (42, 2, 'nation', '仡佬族', '37', NULL, 37, '仡佬族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (43, 2, 'nation', '锡伯族', '38', NULL, 38, '锡伯族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (44, 2, 'nation', '阿昌族', '39', NULL, 39, '阿昌族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (45, 2, 'nation', '普米族', '40', NULL, 40, '普米族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (46, 2, 'nation', '塔吉克族', '41', NULL, 41, '塔吉克族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (47, 2, 'nation', '怒族', '42', NULL, 42, '怒族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (48, 2, 'nation', '乌孜别克族', '43', NULL, 43, '乌孜别克族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (49, 2, 'nation', '俄罗斯族', '44', NULL, 44, '俄罗斯族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (50, 2, 'nation', '鄂温克族', '45', NULL, 45, '鄂温克族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (51, 2, 'nation', '德昂族', '46', NULL, 46, '德昂族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (52, 2, 'nation', '保安族', '47', NULL, 47, '保安族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (53, 2, 'nation', '裕固族', '48', NULL, 48, '裕固族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (54, 2, 'nation', '京族', '49', NULL, 49, '京族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (55, 2, 'nation', '塔塔尔族', '50', NULL, 50, '塔塔尔族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (56, 2, 'nation', '独龙族', '51', NULL, 51, '独龙族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (57, 2, 'nation', '鄂伦春族', '52', NULL, 52, '鄂伦春族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (58, 2, 'nation', '赫哲族', '53', NULL, 53, '赫哲族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (59, 2, 'nation', '门巴族', '54', NULL, 54, '门巴族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (60, 2, 'nation', '珞巴族', '55', NULL, 55, '珞巴族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (61, 2, 'nation', '基诺族', '56', NULL, 56, '基诺族', 1, 0, 'admin', '2026-11-19 18:39:30', 'admin',
        '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (62, 2, 'nation', '其他未识别民族', '97', NULL, 97,
        '主体：本身就是中国公民\r\n\r\n- 属于国内的族群，但还没有被国家正式认定为 56 个民族之一（未定族称），比如穿青人、僜人等；\r\n- 根源是世居国内的群体，只是民族成份暂未划定；\r\n- 户口民族栏会登记对应的族群名称，统计归类为【未识别民族】。',
        1, 0, 'admin', '2026-11-19 18:39:30', 'admin', '2026-11-19 18:39:30');
INSERT INTO `sys_dict_item`
VALUES (63, 2, 'nation', '外国人加入中国籍', '98', NULL, 98,
        '主体：原本是外国人，后来申请入籍拿到中国国籍\r\n\r\n- 原本不属于我国境内 56 个民族、也不属于国内未识别族群；\r\n- 比如乌克兰人、越南人等获批加入中国国籍；\r\n- 户籍登记一般标注：`XX（入籍）`；\r\n- 这个人不是 “国内某个未识别族群”，是外来入籍人员。',
        1, 0, 'admin', '2026-11-19 18:39:30', 'admin', '2026-11-19 18:39:30');
-- ----------------------------
-- Records of sys_file_type
-- ----------------------------
INSERT INTO `sys_file_type`
VALUES (1, 'image', '图片', 'jpg', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (2, 'image', '图片', 'jpeg', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (3, 'image', '图片', 'png', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (4, 'image', '图片', 'gif', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (5, 'image', '图片', 'bmp', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (6, 'image', '图片', 'webp', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (7, 'image', '图片', 'svg', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (8, 'image', '图片', 'ico', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (9, 'pdf', 'PDF文档', 'pdf', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (10, 'video', '视频', 'mp4', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (11, 'video', '视频', 'avi', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (12, 'video', '视频', 'mov', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (13, 'video', '视频', 'mkv', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (14, 'video', '视频', 'flv', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (15, 'video', '视频', 'wmv', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (16, 'video', '视频', 'rmvb', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (17, 'audio', '音频', 'mp3', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (18, 'audio', '音频', 'wav', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (19, 'audio', '音频', 'flac', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (20, 'audio', '音频', 'aac', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (21, 'audio', '音频', 'ogg', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (22, 'audio', '音频', 'm4a', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (23, 'text', '文本', 'txt', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (24, 'text', '文本', 'md', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (25, 'text', '文本', 'log', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (26, 'text', '文本', 'csv', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (27, 'json', 'JSON文件', 'json', 0, 0, 'admin', '2026-11-04 13:52:06', 'admin', '2026-11-04 13:52:35');
INSERT INTO `sys_file_type`
VALUES (28, 'image', '图片', NULL, 0, 1, 'admin', '2026-11-04 13:54:32', 'admin', '2026-11-04 13:54:45');
INSERT INTO `sys_file_type`
VALUES (29, 'image', '图片', NULL, 0, 1, 'admin', '2026-11-04 13:54:42', 'admin', '2026-11-04 13:54:47');
INSERT INTO `sys_file_type`
VALUES (30, 'image', '图片', 'tif', 16, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (31, 'image', '图片', 'tiff', 17, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (32, 'image', '图片', 'heic', 18, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (33, 'pdf', 'PDF', 'doc', 40, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (34, 'pdf', 'PDF', 'docx', 41, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (35, 'pdf', 'PDF', 'xls', 42, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (36, 'pdf', 'PDF', 'xlsx', 43, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (37, 'pdf', 'PDF', 'ppt', 44, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (38, 'pdf', 'PDF', 'pptx', 45, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (39, 'pdf', 'PDF', 'rtf', 46, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (40, 'pdf', 'PDF', 'odt', 47, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (41, 'pdf', 'PDF', 'ods', 48, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (42, 'pdf', 'PDF', 'odp', 49, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (43, 'json', 'JSON', 'xml', 71, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (44, 'json', 'JSON', 'yaml', 72, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (45, 'json', 'JSON', 'yml', 73, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (46, 'video', '视频', 'webm', 81, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (47, 'other', '其他', 'zip', 200, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (48, 'other', '其他', 'rar', 201, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (49, 'other', '其他', '7z', 202, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (70, 'other', '其他', NULL, 999, 0, 'system', '2026-11-09 18:08:49', 'system', '2026-11-09 18:08:49');
INSERT INTO `sys_file_type`
VALUES (111, 'other', '其他', NULL, 999, 1, 'system', '2026-11-09 18:50:03', 'admin', '2026-11-17 15:09:56');
-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 'M', '首页', 'i-menu-dashboard', '/', 1, NULL, 0, NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:01', 'admin', '2026-11-25 16:48:01');
INSERT INTO `sys_menu` VALUES (2, 1, 'C', '工作台', 'i-menu-admin', '/dashboard', 1, 0, 0, NULL, 1, 'DashboardSystemViewIndex', '/views/dashboard/system/index.vue', NULL, 1, 0, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (3, 1, 'C', '个人工作台', 'i-menu-dashboard', '/dashboard/personal', 1, 0, 0, NULL, 2, 'DashboardPersonalViewIndex', '/views/dashboard/personal/index.vue', NULL, 1, 0, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (4, 0, 'M', '系统管理', 'i-menu-system', '/system', 1, NULL, 0, NULL, 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (5, 4, 'C', '用户管理', 'i-menu-user', '/system/user', 1, 1, 0, NULL, 1, 'UserViewIndex', '/views/system/user/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (6, 5, 'B', '用户增加', NULL, NULL, NULL, NULL, 0, 'sys:user:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (7, 5, 'B', '用户密码修改', NULL, NULL, NULL, NULL, 0, 'sys:user:pwd', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (8, 5, 'B', '用户删除', NULL, NULL, NULL, NULL, 0, 'sys:user:remove', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (9, 5, 'B', '用户角色绑定', NULL, NULL, NULL, NULL, 0, 'sys:user:role:bind', 4, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (10, 5, 'B', '用户修改', NULL, NULL, NULL, NULL, 0, 'sys:user:update', 5, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (11, 4, 'C', '菜单管理', 'i-menu-menu', '/system/menu', 1, 1, 0, NULL, 2, 'SysMenuViewIndex', '/views/system/menu/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (12, 11, 'B', '菜单增加', NULL, NULL, NULL, NULL, 0, 'sys:menu:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:02', 'admin', '2026-11-25 16:48:02');
INSERT INTO `sys_menu` VALUES (13, 11, 'B', '菜单删除', NULL, NULL, NULL, NULL, 0, 'sys:menu:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (14, 11, 'B', '菜单修改', NULL, NULL, NULL, NULL, 0, 'sys:menu:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (15, 4, 'C', '角色分类', 'i-menu-role', '/system/roleType', 1, 1, 0, NULL, 3, 'RoleTypeViewIndex', '/views/system/role-type/index.vue', '/system/roleType', 0, 0, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (16, 15, 'B', '角色类型增加', NULL, NULL, NULL, NULL, 0, 'sys:roleType:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (17, 15, 'B', '角色类型删除', NULL, NULL, NULL, NULL, 0, 'sys:roleType:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (18, 15, 'B', '角色类型修改', NULL, NULL, NULL, NULL, 0, 'sys:roleType:updateById', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (19, 15, 'B', '角色类型绑定菜单', NULL, NULL, NULL, NULL, 0, 'sys:roleType:menu:bind', 4, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (20, 4, 'C', '角色管理', 'i-menu-role', '/system/role', 1, 1, 0, NULL, 4, 'RoleViewIndex', '/views/system/role/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (21, 20, 'B', '角色增加', NULL, NULL, NULL, NULL, 0, 'sys:role:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (22, 20, 'B', '角色菜单绑定', NULL, NULL, NULL, NULL, 0, 'sys:role:menu:bind', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (23, 20, 'B', '角色删除', NULL, NULL, NULL, NULL, 0, 'sys:role:remove', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (24, 20, 'B', '角色修改', NULL, NULL, NULL, NULL, 0, 'sys:role:update', 4, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (25, 4, 'C', '部门管理', 'i-menu-tree', '/system/dept', 1, 1, 0, NULL, 5, 'SysDeptViewIndex', '/views/system/dept/index.vue', '/system/dept', 0, 0, 0, 'admin', '2026-11-25 16:48:03', 'admin', '2026-11-25 16:48:03');
INSERT INTO `sys_menu` VALUES (26, 25, 'B', '部门增加', NULL, NULL, NULL, NULL, 0, 'sys:dept:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (27, 25, 'B', '部门删除', NULL, NULL, NULL, NULL, 0, 'sys:dept:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (28, 25, 'B', '部门修改', NULL, NULL, NULL, NULL, 0, 'sys:dept:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (29, 4, 'C', '字典管理', 'i-menu-dict', '/system/dict', 1, 1, 0, NULL, 6, 'SysDictViewIndex', '/views/system/dict/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (30, 29, 'B', '字典增加', NULL, NULL, NULL, NULL, 0, 'sys:dict:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (31, 29, 'B', '字典删除', NULL, NULL, NULL, NULL, 0, 'sys:dict:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (32, 29, 'B', '字典修改', NULL, NULL, NULL, NULL, 0, 'sys:dict:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:04', 'admin', '2026-11-25 16:48:04');
INSERT INTO `sys_menu` VALUES (33, 4, 'C', '字典项管理', 'i-menu-dict', '/system/dict/:id', 0, 1, 0, NULL, 7, 'SysDictItemViewIndex', '/views/system/dict-item/index.vue', '/system/dict', 0, 0, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (34, 33, 'B', '字典项增加', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (35, 33, 'B', '字典项删除', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (36, 33, 'B', '字典项修改', NULL, NULL, NULL, NULL, 0, 'sys:dict:item:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (37, 4, 'C', '客户端', 'i-login-code', '/sys/oauth2/client', 1, 1, 0, NULL, 8, 'SysOauth2ClientViewIndex', '/views/system/oauth2-client/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (38, 37, 'B', '客户端增加', NULL, NULL, NULL, NULL, 0, 'sys:oauth2:client:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (39, 37, 'B', '客户端删除', NULL, NULL, NULL, NULL, 0, 'sys:oauth2:client:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (40, 37, 'B', '客户端修改', NULL, NULL, NULL, NULL, 0, 'sys:oauth2:client:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (41, 4, 'C', '行政区划', 'i-menu-address', '/sys/area', 1, 1, 0, NULL, 9, 'SysAreaViewIndex', '/views/system/area/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (42, 41, 'B', '行政区划增加', NULL, NULL, NULL, NULL, 0, 'sys:area:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (43, 41, 'B', '行政区划删除', NULL, NULL, NULL, NULL, 0, 'sys:area:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (44, 41, 'B', '行政区划修改', NULL, NULL, NULL, NULL, 0, 'sys:area:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:05', 'admin', '2026-11-25 16:48:05');
INSERT INTO `sys_menu` VALUES (45, 0, 'M', '通知中心', 'i-menu-notice', '/notice', 1, NULL, 0, NULL, 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (46, 45, 'C', '通知类型管理', 'i-menu-notice-type', '/sys/notice/type', 1, 1, 0, NULL, 1, 'SysNoticeTypeViewIndex', '/views/system/notice-type/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (47, 46, 'B', '通知类型增加', NULL, NULL, NULL, NULL, 0, 'sys:notice:type:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (48, 46, 'B', '通知类型删除', NULL, NULL, NULL, NULL, 0, 'sys:notice:type:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (49, 46, 'B', '通知类型修改', NULL, NULL, NULL, NULL, 0, 'sys:notice:type:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (50, 45, 'C', '通知管理', 'i-menu-notice', '/sys/notice', 1, 1, 0, NULL, 2, 'SysNoticeViewIndex', '/views/system/notice/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (51, 50, 'B', '通知增加', NULL, NULL, NULL, NULL, 0, 'sys:notice:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (52, 50, 'B', '通知删除', NULL, NULL, NULL, NULL, 0, 'sys:notice:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (53, 50, 'B', '通知修改', NULL, NULL, NULL, NULL, 0, 'sys:notice:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (54, 45, 'C', '站内信管理', 'i-menu-message', '/sys/message', 1, 1, 0, NULL, 3, 'SysMessageViewIndex', '/views/system/message/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (55, 54, 'B', '站内信增加', NULL, NULL, NULL, NULL, 0, 'sys:message:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (56, 54, 'B', '站内信删除', NULL, NULL, NULL, NULL, 0, 'sys:message:remove', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:06', 'admin', '2026-11-25 16:48:06');
INSERT INTO `sys_menu` VALUES (57, 54, 'B', '站内信修改', NULL, NULL, NULL, NULL, 0, 'sys:message:update', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (58, 0, 'M', '基础工具', 'i-menu-system-tools', '/basic', 1, 0, 0, NULL, 4, NULL, NULL, NULL, NULL, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (59, 58, 'C', '附件管理', 'i-menu-folder', '/basic/file', 1, 1, 0, NULL, 1, 'SysFileViewIndex', '/views/system/file/index.vue', '/basic/file', 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (60, 58, 'C', '文件预览类型', 'i-menu-model', '/basic/file-type', 1, 1, 0, NULL, 2, 'SysFileTypeViewIndex', '/views/system/file-type/index.vue', '/basic/file-type', 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (61, 60, 'B', '预览类型增加', NULL, NULL, NULL, NULL, 0, 'sys:file:type:create', 1, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (62, 60, 'B', '预览类型修改', NULL, NULL, NULL, NULL, 0, 'sys:file:type:update', 2, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (63, 60, 'B', '预览类型删除', NULL, NULL, NULL, NULL, 0, 'sys:file:type:remove', 3, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (64, 0, 'M', '监控中心', 'i-menu-monitor', '/monitor', 1, NULL, 0, NULL, 5, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (65, 64, 'C', '操作日志', 'i-menu-monitor', '/monitor/log', 1, 1, 0, NULL, 1, 'SysOperLogViewIndex', '/views/monitor/blog/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (66, 0, 'M', '在线开发', 'i-menu-model', '/tool', NULL, NULL, 0, NULL, 6, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (67, 66, 'C', '数据源', 'i-menu-database', '/tool/datasource', 1, 1, 0, NULL, 1, 'GenDataSourceViewIndex', '/views/generate/datasource/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (68, 66, 'C', '类型映射', 'i-menu-dataPermission', '/tool/type/mapping', 1, 1, 0, NULL, 2, 'GenTypeMappingViewIndex', '/views/generate/type-mapping/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (69, 66, 'C', '模板管理', 'i-menu-template', '/tool/template', 1, 1, 0, NULL, 3, 'GenTemplateViewIndex', '/views/generate/template/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (70, 66, 'C', '表管理', 'i-menu-database-table', '/tool/table', 1, 1, 0, NULL, 4, 'GenTableInfoViewIndex', '/views/generate/table/index.vue', NULL, 0, 0, 0, 'admin', '2026-11-25 16:48:07', 'admin', '2026-11-25 16:48:07');
INSERT INTO `sys_menu` VALUES (71, 0, 'M', '外部链接', 'i-menu-guide', '/web', 1, NULL, 0, NULL, 7, NULL, NULL, NULL, NULL, NULL, 0, 'admin', '2026-11-25 16:48:08', 'admin', '2026-11-25 16:48:08');
INSERT INTO `sys_menu` VALUES (72, 71, 'C', '接口文档', 'i-menu-monitor', 'http://localhost:8080/doc.html', 1, 0, 0, NULL, 1, NULL, NULL, NULL, 0, 1, 0, 'admin', '2026-11-25 16:48:08', 'admin', '2026-11-25 16:48:08');
INSERT INTO `sys_menu` VALUES (73, 71, 'C', '文档中心', 'i-menu-monitor', 'https://xhtcode.github.io/xht-cloud-doc/', 1, 0, 0, NULL, 2, NULL, NULL, NULL, 0, 1, 0, 'admin', '2026-11-25 16:48:08', 'admin', '2026-11-25 16:48:08');
-- ----------------------------
-- Records of sys_notice_type
-- ----------------------------
INSERT INTO `sys_notice_type`
VALUES (1, '112312', 1, 0, 0, 'admin', '2026-09-29 17:50:26', 'admin', '2026-09-29 17:50:26');

-- ----------------------------
-- Records of sys_oauth2_client
-- ----------------------------
INSERT INTO `sys_oauth2_client`
VALUES (1, 'oidc-client', '2025-07-19 16:14:53', '5ebe2294ecd0e0f08eab7690d2a6ee69', '2035-09-01 16:15:05',
        'oidc-client', '[\"client_secret_basic\"]',
        '[\"refresh_token\",\"password\",\"client_credentials\",\"phone\",\"authorization_code\"]',
        '[\"http://127.0.0.1:3000/login\",\"https://www.baidu.com\"]', NULL, '[\"user_info\",\"openid\",\"profile\"]',
        10000, 10000, '{}', 1, NULL, 0, 'admin', '2025-09-15 14:46:25', 'admin', '2025-09-15 14:46:25');
-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES (108, 1, 'admin', '系统管理员', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, '13812345678',
        '/img/user-avatar/c9be87b0012a475b9f067b1a03f31b8a', 1, NULL, 0, 'admin', '2025-09-15 14:46:29', 'admin',
        '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (109, 2, 'zhang_san', '张三', 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, '13812345679',
        '/img/user-avatar/b0e75c3470614e9c9bbc64416d911895', 2, NULL, 0, 'admin', '2025-09-15 14:46:29', 'admin',
        '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (110, 2, 'li_si', '李四', '5e8747fddda684cc69dbe13c6d1369b2', 'YtY.[DA4g_ja', 0, '13812345680',
        '/img/user-avatar/b0e75c3470614e9c9bbc64416d911895', 5, '后端开发组', 0, 'admin', '2025-09-15 14:46:29',
        'admin', '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (111, 2, 'wang_wu', '王五', '44feb1003c44fc7857214d4a51ffcc2f', 'n2G67j]#8Vwn', 0, '13812345681',
        '/img/user-avatar/b0e75c3470614e9c9bbc64416d911895', 5, NULL, 0, 'admin', '2025-09-15 14:46:29', 'admin',
        '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (112, 2, 'zhao_liu', '赵六', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, '13812345682',
        '/img/user-avatar/b0e75c3470614e9c9bbc64416d911895', 3, NULL, 0, 'admin', '2025-09-15 14:46:29', 'admin',
        '2025-09-15 14:46:29');
INSERT INTO `sys_user`
VALUES (2123345743890563072, 2, 'asd', 'asdasdasd', 'a3e0a65dae1c194cbe6f208088e5a15f', 'qTSRVbvZ*hC)', 0,
        '13112312312', NULL, 5, '后端开发组', 0, 'admin', '2026-11-19 17:20:50', 'admin', '2026-11-19 17:20:50');
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
VALUES (110, '李四', '110101199503033456', 1, '1995-03-03', 30, '北京市西城区', '张三', '13112312312', '01', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (111, '王五', '110101199804044567', 1, '1998-04-04', 27, '北京市东城区', '张三', '13112312312', '01', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (112, '赵六', '110101200005055678', 0, '2000-05-05', 25, '北京市丰台区', '张三', '13112312312', '汉', 0, 'admin',
        '2025-09-15 14:46:31', 'admin', '2025-09-15 14:46:31');
INSERT INTO `sys_user_detail`
VALUES (2123345743890563072, 'xhtli', '410325200001029870', 0, '2026-11-12', 123, '123123', '12312123', '13112312312',
        '01', 0, 'admin', '2026-11-19 17:20:50', 'admin', '2026-11-19 17:20:50');
