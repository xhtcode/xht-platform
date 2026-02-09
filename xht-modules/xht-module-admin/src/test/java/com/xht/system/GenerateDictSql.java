package com.xht.system;

import cn.hutool.core.util.IdUtil;
import com.xht.modules.admin.notice.enums.*;

/**
 *
 * @author xht
 **/
public class GenerateDictSql {

    public static void main(String[] args) {
        long dictId = 1L;
        mainSql(dictId, "notice_status", "通知状态");
        NoticeStatusEnums[] values = NoticeStatusEnums.values();
        int sort = 1;
        for (NoticeStatusEnums value : values) {
            itemSql(dictId, "notice_status", value.getDesc(), value.getValue(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_operate_type", "通知操作类型");
        sort = 1;
        for (NoticeOperateTypeEnums value : NoticeOperateTypeEnums.values()) {
            itemSql(dictId, "notice_operate_type", value.getDesc(), value.name(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_perm_type", "通知权限类型");
        sort = 1;
        for (NoticePermTypeEnums value : NoticePermTypeEnums.values()) {
            itemSql(dictId, "notice_perm_type", value.getDesc(), value.name(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_timed_publish", "通知是否定时发布");
        sort = 1;
        for (NoticeTimedPublishEnums value : NoticeTimedPublishEnums.values()) {
            itemSql(dictId, "notice_timed_publish", value.getDesc(), value.name(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_jump_type", "通知跳转类型");
        sort = 1;
        for (NoticeJumpTypeEnums value : NoticeJumpTypeEnums.values()) {
            itemSql(dictId, "notice_jump_type", value.getDesc(), value.name(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_all_visible", "通知可见范围");
        sort = 1;
        for (NoticeAllVisibleEnums value : NoticeAllVisibleEnums.values()) {
            itemSql(dictId, "notice_all_visible", value.getDesc(), value.name(), sort++);
        }
        dictId +=1L;
        mainSql(dictId, "notice_top", "通知置顶状态");
        sort = 1;
        for (NoticeTopEnums value : NoticeTopEnums.values()) {
            itemSql(dictId, "notice_top", value.getDesc(), value.name(), sort++);
        }

    }

    public static void mainSql(Long id, String name, String desc) {
        System.out.printf("""
                INSERT INTO `sys_dict`
                 (`id`,`dict_code`, `dict_name`, `sort_order`, `remark`, `status`, `show_disabled`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES
                 (%s,'%s', '%s', 5, '%s', 1, 0, 0, 'admin', now(), 'admin', now());
                %n""", id, name, desc, desc);
    }

    public static void itemSql(
            Long dict_id,
            String dict_code,
            String item_label,
            Object item_value,
            int sort_order
    ) {
        String format = String.format("""
                INSERT INTO `sys_dict_item`
                 (`id`,`dict_id`, `dict_code`, `item_label`, `item_value`, `item_color`, `sort_order`, `remark`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`)
                  VALUES
                  (%s,%s, '%s','%s', '%s', null, %s,  '%s', 1, 0, 'admin', now(), 'admin',  now());
                """, IdUtil.getSnowflakeNextId(), dict_id, dict_code, item_label, item_value, sort_order, item_value);
        System.out.println(format);
    }
}
