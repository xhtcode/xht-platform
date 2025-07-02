package com.xht.framework.core.utils.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

/**
 * aop 工具类
 *
 * @author xht
 **/
public final class AopUtils {
    /**
     * 通过连接点对象获取其方法或类上的注解
     *
     * @param point     连接点
     * @param authClass 注解类型
     * @return 注解
     */
    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint point, Class<T> authClass) {
        Class<?> clazz = point.getTarget().getClass();
        return AnnotationUtils.findAnnotation(clazz, authClass);
    }
}
