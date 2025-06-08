package com.xht.framework.web.convert;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 描述 ：string 转 IEnum枚举
 *
 * @author 小糊涂
 **/
@Slf4j
public class IEnumsStringConvert implements Converter<String, IEnum<Serializable>> {
    private final Map<String, IEnum<Serializable>> ENUM_MAP = new HashMap<>();

    IEnumsStringConvert(Class<? extends IEnum<Serializable>> enumType) {
        IEnum<Serializable>[] enumConstants = enumType.getEnumConstants();
        for (IEnum<Serializable> e : enumConstants) {
            try {
                String value = StringUtils.str(e.getValue());
                if (StringUtils.hasText(value)) {
                    ENUM_MAP.put(value, e);
                } else {
                    throw new BusinessException("系统配置错误 com.baomidou.mybatisplus.annotation.IEnum; is null!");
                }
            } catch (Exception exception) {
                log.error("string 转 IEnum枚举 异常:{}", exception.getMessage(), exception);
            }
        }
    }

    @Override
    public IEnum<Serializable> convert(String source) {
        IEnum<Serializable> serializableIEnum = ENUM_MAP.get(source);
        return Objects.nonNull(serializableIEnum) ? serializableIEnum : null;
    }
}
