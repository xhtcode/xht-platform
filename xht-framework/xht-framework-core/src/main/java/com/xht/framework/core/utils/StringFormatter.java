package com.xht.framework.core.utils;

import com.xht.framework.core.constant.StringConstant;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 字符串格式化工具
 *
 * @author xht
 */
public class StringFormatter {

    /**
     * 格式化文本，使用 {varName} 占位<br>
     * map = {a: "aValue", b: "bValue"} format("{a} and {b}", map) ---=》 aValue and bValue
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(final String template, Map<String, Object> map) {
        if (Objects.isNull(template) || CollectionUtils.isEmpty(map)) {
            return template;
        }
        String result = template;
        String value;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            value = StringUtils.str(entry.getValue(), StringConstant.EMPTY);
            result = StringUtils.replace(result, "{" + entry.getKey() + "}", value);
        }
        return result;
    }

}
