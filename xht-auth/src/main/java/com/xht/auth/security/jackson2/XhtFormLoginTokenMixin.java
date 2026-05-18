package com.xht.auth.security.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * XhtFormLoginToken的Jackson Mixin抽象类
 * 通过注解配置XhtFormLoginToken的序列化和反序列化行为：
 * - 使用类名作为类型信息包含在JSON中
 * - 允许访问所有字段，忽略getter方法
 * - 指定使用XhtFormLoginTokenDeserializer进行反序列化
 * @author xht
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(using = XhtFormLoginTokenDeserializer.class)
abstract class XhtFormLoginTokenMixin {

}
