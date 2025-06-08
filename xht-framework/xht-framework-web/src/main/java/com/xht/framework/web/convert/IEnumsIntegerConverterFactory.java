package com.xht.framework.web.convert;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 ：Integer 转 IEnum枚举工厂
 *
 * @author 小糊涂
 **/
public class IEnumsIntegerConverterFactory implements ConverterFactory<Integer, IEnum<Integer>> {

    private static final Map<Class<? extends IEnum<Integer>>, Converter<Integer, ? extends IEnum<Integer>>> CONVERTERS = new HashMap<>();

    @Override
    @SuppressWarnings("all")
    public <T extends IEnum<Integer>> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter<Integer, ? extends IEnum<Integer>> converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new IEnumsIntegerConvert(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return (Converter<Integer, T>) converter;
    }
}
