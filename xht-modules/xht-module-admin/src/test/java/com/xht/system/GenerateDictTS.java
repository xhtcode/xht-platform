package com.xht.system;

import cn.hutool.core.util.StrUtil;
import com.xht.modules.admin.notice.enums.NoticeStatusEnums;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author xht
 **/
public class GenerateDictTS {

    public static void main(String[] args) {
        generateType("是否置顶", NoticeStatusEnums.class, NoticeStatusEnums.values());
        String descName = "是否置顶";
        String keyName = "NoticeStatus";
        generateEnums(descName, keyName);
    }

    public static void generateEnums(String descName, String keyName) {
        System.out.printf("""
                 /**
                 * %s key类型
                 */
                export const %sEnums: DictEnum<number, %sKeyType> = {
                %n""", descName, StrUtil.lowerFirst(keyName), StrUtil.upperFirst(keyName));
        for (NoticeStatusEnums item : NoticeStatusEnums.values()) {
            Integer value = item.getValue();
            String desc = item.getDesc();
            String name = item.name();
            System.out.printf("""         
                      %s: {
                          label: '%s',
                          value: %s,
                        },
                    """, name, desc, value);
        }
        System.out.println("}");
    }

    public static void generateType(String descName, Class<?> key, Enum<?>[] enums) {
        List<String> list = Arrays.stream(enums).map(Enum::name).toList();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(String.format("'%s'", list.get(i)));
            if (i != list.size() - 1) {
                builder.append(" | ");
            }
        }
        String name = key.getSimpleName().replaceAll("Enums", "");
        System.out.format("""
                 /**
                 * %s
                 */
                export type %sKeyType = %s
                
                """, descName, name, builder);

    }


}
