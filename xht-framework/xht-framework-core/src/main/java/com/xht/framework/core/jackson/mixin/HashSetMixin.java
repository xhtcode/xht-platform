package com.xht.framework.core.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * 为HashSet创建Mixin，加入反序列化白名单
 * @author xht
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonTypeName("java.util.HashSet")
public abstract class HashSetMixin {
    // 空Mixin类，仅用于添加Jackson注解
}