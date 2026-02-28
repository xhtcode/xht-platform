package com.xht.framework.log.aspect;

import com.xht.framework.core.domain.HttpServletRequestInfo;
import com.xht.framework.core.support.blog.dto.BLogDTO;
import com.xht.framework.core.support.blog.enums.LogStatusEnums;
import com.xht.framework.core.utils.ServletUtil;
import com.xht.framework.core.utils.mdc.TraceIdUtils;
import com.xht.framework.core.utils.spring.SpringContextUtils;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.log.repository.BLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BLogAspect {

    private BLogRepository bLogRepository;

    @Autowired(required = false)
    public void setbLogRepository(BLogRepository bLogRepository) {
        this.bLogRepository = bLogRepository;
    }

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
        bLogDTO.setTitle(bLog.value());
        bLogDTO.setDescription(bLog.description());
        bLogDTO.setServiceName(SpringContextUtils.getApplicationName());
        Optional<HttpServletRequest> optHttpServletRequest = ServletUtil.getOptHttpServletRequest();
        optHttpServletRequest.ifPresent(request -> {
            HttpServletRequestInfo build = HttpServletRequestInfo.builder(request)
                    .requestUrl()
                    .paramMap()
                    .body()
                    .headerMap()
                    .ip()
                    .requestMethod()
                    .build();
            bLogDTO.setRemoteAddr(build.ip());
            bLogDTO.setUserAgent(build.getUserAgent());
            bLogDTO.setRequestUri(build.requestUrl());
            bLogDTO.setMethod(build.requestMethod());
            bLogDTO.setParams(build);
            bLogDTO.setUserAccount(SpringContextUtils.getUserContextService().userName());
        });
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
            try {
                bLogRepository.save(bLogDTO);
            } catch (Exception e) {
                log.error("日志保存失败 {}", e.getMessage(), e);
            }
        }
        return obj;
    }

}
