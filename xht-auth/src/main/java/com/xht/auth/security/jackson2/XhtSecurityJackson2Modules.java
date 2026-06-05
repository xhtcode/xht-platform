package com.xht.auth.security.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xht.auth.security.web.authentication.form.token.XhtFormLoginToken;
import com.xht.auth.security.web.authentication.phone.token.XhtPhoneLoginToken;
import com.xht.framework.core.jackson.mixin.HashSetMixin;

import java.util.HashSet;

/**
 * 描述 ：XhtSecurityJackson2Modules
 *
 * @author xht
 */
public final class XhtSecurityJackson2Modules extends SimpleModule {

    /**
     * 构造函数，初始化Jackson模块
     * 使用类名作为模块名称，版本号为1.0.0
     */
    public XhtSecurityJackson2Modules() {
        super(XhtSecurityJackson2Modules.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    /**
     * 配置模块的Mixin注解映射
     * 将XhtFormLoginToken与XhtFormLoginTokenMixin关联，实现自定义序列化行为
     * 将HashSet与HashSetMixin关联，处理HashSet的序列化
     *
     * @param context SetupContext对象，用于注册Mixin注解映射
     */
    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(XhtFormLoginToken.class,
                XhtFormLoginTokenMixin.class);
        context.setMixInAnnotations(XhtPhoneLoginToken.class,
                XhtPhoneLoginTokenMixin.class);
        context.setMixInAnnotations(HashSet.class,
                HashSetMixin.class);
    }
}