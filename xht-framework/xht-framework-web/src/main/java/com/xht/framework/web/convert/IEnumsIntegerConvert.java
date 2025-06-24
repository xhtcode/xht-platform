package com.xht.framework.web.convert;


import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：Integer 转 IEnum枚举
 *
 * @author xht
 **/
@Slf4j
public class IEnumsIntegerConvert implements Converter<Integer, IEnum<Integer>> {
    private final Map<Integer, IEnum<Integer>> ENUM_MAP = new HashMap<>();

    IEnumsIntegerConvert(Class<? extends IEnum<Integer>> enumType) {
        IEnum<Integer>[] enumConstants = enumType.getEnumConstants();
        for (IEnum<Integer> e : enumConstants) {
            try {
                ENUM_MAP.put(e.getValue(), e);
            } catch (Exception exception) {
                log.error("Integer 转 IEnum枚举 异常:{}", exception.getMessage(), exception);
            }
        }
    }

    @Override
    public IEnum<Integer> convert(Integer source) {
        return ENUM_MAP.get(source);
    }
}

