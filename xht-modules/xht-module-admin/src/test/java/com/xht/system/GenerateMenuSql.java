package com.xht.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.modules.admin.system.dao.mapper.SysMenuMapper;
import com.xht.modules.admin.system.entity.SysMenuEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xht
 **/
@SpringBootTest
public class GenerateMenuSql {

    final static String sql = "INSERT INTO `sys_menu`" +
            " (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`, `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`, `view_name`, `view_path`, `active_menu_path`, `frame_flag`,`affix_status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES " +
            "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,%s, %s);";
    @Autowired
    private SysMenuMapper sysMenuMapper;

    private static int print(List<String> list, SysMenuEntity entity, int index, int parent, int sort) {
        list.add(String.format(sql,
                index,
                parent,
                Objects.isNull(entity.getMenuType()) ? null : "'" + entity.getMenuType().getValue() + "'",
                !StringUtils.hasLength(entity.getMenuName()) ? null : "'" + entity.getMenuName() + "'",
                !StringUtils.hasLength(entity.getMenuIcon()) ? null : "'" + entity.getMenuIcon() + "'",
                !StringUtils.hasLength(entity.getMenuPath()) ? null : "'" + entity.getMenuPath() + "'",
                Objects.isNull(entity.getMenuHidden()) ? null : entity.getMenuHidden().getValue(),
                Objects.isNull(entity.getMenuCache()) ? null : entity.getMenuCache().getValue(),
                Objects.isNull(entity.getMenuStatus()) ? null : entity.getMenuStatus().getValue(),
                !StringUtils.hasLength(entity.getMenuAuthority()) ? null : "'" + entity.getMenuAuthority() + "'",
                sort,
                !StringUtils.hasLength(entity.getViewName()) ? null : "'" + entity.getViewName() + "'",
                !StringUtils.hasLength(entity.getViewPath()) ? null : "'" + entity.getViewPath() + "'",
                !StringUtils.hasLength(entity.getActiveMenuPath()) ? null : "'" + entity.getActiveMenuPath() + "'",
                Objects.isNull(entity.getFrameFlag()) ? null : entity.getFrameFlag().getValue(),
                Objects.isNull(entity.getAffixStatus()) ? null : entity.getAffixStatus().getValue(),
                Objects.isNull(entity.getDelFlag()) ? null : entity.getDelFlag().getValue(),
                "'admin'",
                "NOW()",
                "'admin'",
                "NOW()"
        ));
        return index;
    }

    @Test
    public void test() {
        LambdaQueryWrapper<SysMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenuEntity::getParentId, 0);
        queryWrapper.orderByAsc(SysMenuEntity::getMenuSort);
        List<SysMenuEntity> menuEntityList = sysMenuMapper.selectList(queryWrapper);
        int index = 1;
        List<String> list = new ArrayList<>();
        list.add("TRUNCATE table sys_menu;");
        for (int i = 0; i < menuEntityList.size(); i++) {
            SysMenuEntity sysMenuEntity = menuEntityList.get(i);
            int p1 = print(list, sysMenuEntity, index++, 0, i + 1);
            List<SysMenuEntity> itemList = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getParentId, sysMenuEntity.getId()).orderByAsc(SysMenuEntity::getMenuSort));
            for (int j = 0; j < itemList.size(); j++) {
                SysMenuEntity sysMenuEntity1 = itemList.get(j);
                int p2 = print(list, sysMenuEntity1, index++, p1, j + 1);
                List<SysMenuEntity> itemList2 = sysMenuMapper.selectList(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getParentId, sysMenuEntity1.getId()).orderByAsc(SysMenuEntity::getMenuSort));
                for (int k = 0; k < itemList2.size(); k++) {
                    print(list, itemList2.get(k), index++, p2, k + 1);

                }
            }
        }
        list.forEach(System.out::println);
    }
}
