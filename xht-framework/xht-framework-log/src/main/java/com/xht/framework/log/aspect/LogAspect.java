package com.xht.framework.log.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.xht.framework.core.constant.HttpConstants;
import com.xht.framework.core.jackson.JsonUtils;
import com.xht.framework.core.utils.IpUtils;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.annotations.Log;
import com.xht.framework.log.domain.dto.LogDTO;
import com.xht.framework.log.enums.LogStatusEnums;
import com.xht.framework.log.properties.BasicLogProperties;
import com.xht.framework.log.repository.LogRepository;
import com.xht.framework.log.utils.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 日志切面
 *
 * @author xht
 */
@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class LogAspect {

    private final LogRepository repository;

    private final BasicLogProperties basicLogProperties;


    /**
     * 环绕通知方法，用于处理系统日志记录
     *
     * @param point  连接点对象
     * @param sysLog 系统日志注解
     * @return 目标方法执行结果
     */
    @Around("@annotation(sysLog)")
    @SneakyThrows
    public Object around(ProceedingJoinPoint point, Log sysLog) {
        LogDTO logDTO = new LogDTO();
        logDTO.setTraceId(TraceIdUtils.getTraceId());
        logDTO.setTitle(sysLog.title());
        logDTO.setDescription(sysLog.description());
        logDTO.setServiceName(SpringContextUtils.getApplicationName());
        Optional<HttpServletRequest> optHttpServletRequest = ServletUtil.getOptHttpServletRequest();
        optHttpServletRequest.ifPresent(request -> {
            logDTO.setRemoteAddr(IpUtils.getIpAddr(request));
            logDTO.setUserAgent(request.getHeader(HttpConstants.Header.USER_AGENT.getValue()));
            logDTO.setRequestUri(URLUtil.getPath(request.getRequestURI()));
            logDTO.setMethod(request.getMethod());
            logDTO.setParams(request.getRequestURI());
        });
        // 获取请求body参数
        if (StrUtil.isBlank(logDTO.getParams())) {
            logDTO.setParams(JsonUtils.toJsonString(point.getArgs()));
        }
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj;
        try {
            obj = point.proceed();
            logDTO.setStatus(LogStatusEnums.NORMAL);
        } catch (Exception e) {
            logDTO.setStatus(LogStatusEnums.ERROR);
            logDTO.setException(e.getMessage());
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            logDTO.setTime(endTime - startTime);
            repository.save(logDTO);
        }
        return obj;
    }


}
