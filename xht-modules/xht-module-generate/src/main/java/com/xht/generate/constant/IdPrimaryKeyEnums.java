package com.xht.generate.constant;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    private final Integer value;

    private final String desc;
}
