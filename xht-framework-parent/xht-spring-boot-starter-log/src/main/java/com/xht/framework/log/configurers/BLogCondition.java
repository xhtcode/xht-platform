package com.xht.framework.log.configurers;

import com.xht.framework.log.annotations.ConditionalOnBLog;
import com.xht.framework.utils.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 日志自动装配条件注解
 *
 * @author xht
 */
public class BLogCondition implements Condition {

    private final static String REPOSITORY_TYPE = "xht.blog.repository-type";

    private final static String DEFAULT_NAME = BLogProperties.RepositoryType.DEFAULT.name();


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty(REPOSITORY_TYPE, DEFAULT_NAME);
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnBLog.class.getName());
        if (CollectionUtils.isEmpty(attributes)) {
            return false;
        }
        String propertyValue = StringUtils.str(attributes.getOrDefault("value", DEFAULT_NAME), null);
        return StringUtils.equalsIgnoreCase(property, propertyValue);
    }


}