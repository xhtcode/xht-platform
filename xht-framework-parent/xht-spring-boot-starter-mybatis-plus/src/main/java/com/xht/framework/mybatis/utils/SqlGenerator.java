package com.xht.framework.mybatis.utils;

import java.util.List;
import java.util.Objects;

/**
 * 描述 ：数据权限数据拼装类
 *
 * @author xht
 **/
@SuppressWarnings("unused")
public final class SqlGenerator {

    /**
     * 生成in条件语句
     *
     * @param fieldName   字段名称
     * @param fieldValues 字段值
     * @return in条件语句
     */
    public static String generateInClause(String fieldName, List<String> fieldValues) {
        if (Objects.isNull(fieldValues) || fieldValues.isEmpty()) {
            return "";
        } else if (fieldValues.size() == 1) {
            return " fieldName = " + fieldValues.get(0);
        }
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append(" IN ( ");
        for (int i = 0; i < fieldValues.size(); i++) {
            sb.append("'").append(fieldValues.get(i)).append("'");
            if (i != fieldValues.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }


}
