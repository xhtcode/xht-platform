package com.xht.framework.log.aspect;

import com.xht.framework.core.utils.StringUtils;
import com.xht.framework.log.annotations.MethodTimeAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * 描述 ：aop 方法计时 配置类
 *
 * @author xht
 **/
@Slf4j
@Aspect
@Component
public class MethodTimeAspect {

    /**
     * 定义切面需要使用的注释
     */
    @Pointcut("@annotation(com.xht.framework.log.annotations.MethodTimeAnnotation)")
    public void point() {
    }

    /**
     * 方法计时 切点
     *
     * @param joinPoint               {@link ProceedingJoinPoint}
     * @param methodTimeAnnotation {@link MethodTimeAnnotation}
     * @return Object
     */
    @Around(value = "point() && @annotation(methodTimeAnnotation)")
    public Object doAround(ProceedingJoinPoint joinPoint, MethodTimeAnnotation methodTimeAnnotation) throws Throwable {
        Instant start = Instant.now();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = StringUtils.emptyToDefault(methodTimeAnnotation.value(), String.format("%s()", signature.getMethod().getName()));
        log.info("{}【方法计时】开始......", methodName);
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            long millis = between.toMillis();
            log.error("{}【方法计时】异常  执行耗时: {}ms", methodName, millis, e);
            throw e;
        } finally {
            Instant end = Instant.now();
            Duration between = Duration.between(start, end);
            long millis = between.toMillis();
            log.info("{}【方法计时】结束  执行耗时: {}ms", methodName, millis);
        }
    }

}
