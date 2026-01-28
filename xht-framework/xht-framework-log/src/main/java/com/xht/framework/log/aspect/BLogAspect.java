package com.xht.framework.log.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.jackson.JsonUtils;
import com.xht.framework.core.support.blog.BLogEvent;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.annotations.BLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 日志切面
 *
 * @author xht
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BLogAspect {

    /**
     * 环绕通知方法，用于处理系统日志记录
     *
     * @param point 连接点对象
     * @param bLog  系统日志注解
     * @return 目标方法执行结果
     */
    @Around("@annotation(bLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, BLog bLog) {
        BLogDTO bLogDTO = new BLogDTO();
        bLogDTO.setTraceId(TraceIdUtils.getTraceId());
        bLogDTO.setTitle(bLog.title());
        bLogDTO.setDescription(bLog.description());
        bLogDTO.setServiceName(SpringContextUtils.getApplicationName());
        Optional<HttpServletRequest> optHttpServletRequest = ServletUtil.getOptHttpServletRequest();
        optHttpServletRequest.ifPresent(request -> {
            bLogDTO.setRemoteAddr(IpUtils.getIpAddr(request));
            bLogDTO.setUserAgent(request.getHeader(HttpConstants.Header.USER_AGENT.getValue()));
            bLogDTO.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            bLogDTO.setMethod(request.getMethod());
            bLogDTO.setParams(request.getRequestURI());
            bLogDTO.setUserAccount(SpringContextUtils.getUserContextService().userName());
        });
        // 获取请求 body 参数
        if (StrUtil.isBlank(bLogDTO.getParams())) {
            bLogDTO.setParams(JsonUtils.toJsonString(point.getArgs()));
        }
        // 发送异步日志事件
        StopWatch stopWatch = new StopWatch();
        Object obj;
        try {
            bLogDTO.setExecuteTime(LocalDateTime.now());
            stopWatch.start();
            obj = point.proceed();
            bLogDTO.setStatus(LogStatusEnums.NORMAL);
        } catch (Exception e) {
            bLogDTO.setStatus(LogStatusEnums.ERROR);
            bLogDTO.setException(e.getMessage());
            throw e;
        } finally {
            stopWatch.stop();
            bLogDTO.setTiming(stopWatch.getTotalTimeMillis());
            SpringContextUtils.publishEvent(new BLogEvent(bLogDTO));
        }
        return obj;
    }

}
