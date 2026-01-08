package com.xht.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.mybatis.enums.DelFlagEnum;
import com.xht.api.system.enums.MenuStatusEnums;
import com.xht.api.system.enums.MenuTypeEnums;
import com.xht.modules.system.dao.mapper.SysMenuMapper;
import com.xht.modules.system.entity.SysMenuEntity;
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

    @Autowired
    private SysMenuMapper sysMenuMapper;

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

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        create(list, 41L, "数据源增加", "gen:datasource:create", 1);
        create(list, 41L, "数据源修改", "gen:datasource:update", 2);
        create(list, 41L, "数据源删除", "gen:datasource:remove", 3);
        create(list, 42L, "数据源增加", "gen:type:create", 1);
        create(list, 42L, "数据源修改", "gen:type:update", 2);
        create(list, 42L, "数据源删除", "gen:type:remove", 3);
        create(list, 43L, "模板组增加", "gen:group:create", 1);
        create(list, 43L, "模板组修改", "gen:group:update", 2);
        create(list, 43L, "模板组删除", "gen:group:remove", 3);
        create(list, 43L, "模板增加", "gen:template:create", 4);
        create(list, 43L, "模板修改", "gen:template:update", 5);
        create(list, 43L, "模板删除", "gen:template:remove", 6);
        create(list, 44L, "表管理", "gen:table:all", 1);
        list.forEach(System.out::println);
    }

    private static long count = 47;

    private static void create(List<String> list, Long parentId, String menuName, String menuAuthority, Integer menuSort) {
        SysMenuEntity sysMenuEntity = new SysMenuEntity();
        sysMenuEntity.setId(count++);
        sysMenuEntity.setParentId(parentId);
        sysMenuEntity.setMenuType(MenuTypeEnums.B);
        sysMenuEntity.setMenuName(menuName);
        sysMenuEntity.setMenuStatus(MenuStatusEnums.NORMAL);
        sysMenuEntity.setMenuAuthority(menuAuthority);
        sysMenuEntity.setMenuSort(menuSort);
        sysMenuEntity.setDelFlag(DelFlagEnum.DELETE);
        print(list, sysMenuEntity, sysMenuEntity.getId().intValue(), sysMenuEntity.getParentId().intValue(), menuSort);
    }

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

    final static String sql = "INSERT INTO `sys_menu`" +
            " (`id`, `parent_id`, `menu_type`, `menu_name`, `menu_icon`, `menu_path`, `menu_hidden`, `menu_cache`, `menu_status`, `menu_authority`, `menu_sort`, `view_name`, `view_path`, `active_menu_path`, `frame_flag`,`affix_status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES " +
            "(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,%s, %s);";
}
