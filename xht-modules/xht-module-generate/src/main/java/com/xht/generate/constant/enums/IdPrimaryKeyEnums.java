package com.xht.generate.constant.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 是否是主键
 *
 * @author xht
 **/
@Getter
@RequiredArgsConstructor
public enum IdPrimaryKeyEnums implements IEnum<Integer> {

    NO(0, "否"),

    YES(1, "是"),

    ;

    @JsonValue
    private final Integer value;

    private final String desc;

    public static IdPrimaryKeyEnums of(Integer value) {
        return Arrays.stream(IdPrimaryKeyEnums.values()).filter(item -> item.value.equals(value)).findFirst().orElse(null);
    }
}
