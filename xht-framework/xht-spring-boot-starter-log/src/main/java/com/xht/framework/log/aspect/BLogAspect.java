package com.xht.framework.log.aspect;

import cn.hutool.core.net.NetUtil;
import com.xht.framework.common.constant.HttpConstants;
import com.xht.framework.common.constant.StringConstant;
import com.xht.framework.jackson.JsonUtils;
import com.xht.framework.core.blog.dto.BLogDTO;
import com.xht.framework.core.blog.enums.LogStatusEnums;
import com.xht.framework.utils.IpUtils;
import com.xht.framework.utils.ServletUtil;
import com.xht.framework.utils.mdc.TraceIdUtils;
import com.xht.framework.log.annotations.BLog;
import com.xht.framework.log.repository.BLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 日志切面
 *
 * @author xht
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class BLogAspect {

    private final BLogRepository bLogRepository;

    @Value("${spring.application.name:unknown}")
    private String applicationName;

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
        // 发送异步日志事件
        StopWatch stopWatch = new StopWatch("blog日志");
        stopWatch.start();
        BLogDTO bLogDTO = new BLogDTO();
        Object obj;
        try {
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            Object[] args = point.getArgs();
            String[] parameterNames = ((CodeSignature) point.getSignature()).getParameterNames();
            bLogDTO.setTitle(bLog.value());
            bLogDTO.setLogDesc(bLog.description());
            bLogDTO.setTraceId(TraceIdUtils.getTraceId());
            bLogDTO.setServiceName(applicationName);
            bLogDTO.setServerAddr(NetUtil.getLocalhostStr());
            Optional<HttpServletRequest> optHttpServletRequest = ServletUtil.getOptHttpServletRequest();
            optHttpServletRequest.ifPresent(request -> {
                bLogDTO.setRequestIp(IpUtils.getClientIP(request));
                bLogDTO.setRequestHeaders(JsonUtils.toJsonString(ServletUtil.getHeaderMap(request)));
                bLogDTO.setRequestAccount(request.getHeader(HttpConstants.Header.USER_ACCOUNT.getValue()));
                bLogDTO.setRequestType(request.getMethod());
            });
            bLogDTO.setExecuteTime(LocalDateTime.now());
            obj = point.proceed();
            bLogDTO.setClassMethod(decorateMethodName(className, methodName));
            bLogDTO.setRequestParams(getRequestParams(parameterNames, args));
            bLogDTO.setExecuteStatus(LogStatusEnums.NORMAL);
        } catch (Exception e) {
            bLogDTO.setExecuteStatus(LogStatusEnums.ERROR);
            bLogDTO.setExecuteException(e.getMessage());
            throw e;
        } finally {
            stopWatch.stop();
            bLogDTO.setExecuteCost(stopWatch.getTotalTimeMillis());
            try {
                bLogRepository.save(bLogDTO);
            } catch (Exception e) {
                log.error("日志保存失败 {}", e.getMessage(), e);
            }
        }
        return obj;
    }

    /**
     * 装饰方法名，将类名和方法名组合成标准格式
     *
     * @param className 类名
     * @param methodName 方法名
     * @return 格式化后的方法名，格式为"类名。方法名 ()"
     */
    private String decorateMethodName(String className, String methodName) {
        return String.format("%s.%s()", className, methodName);
    }

    /**
     * 获取请求参数的 JSON 字符串表示
     *
     * @param parameterNames 参数名称数组
     * @param args 参数值数组
     * @return 参数的 JSON 字符串表示；如果参数为空则返回空 JSON 字符串
     */
    private String getRequestParams(String[] parameterNames, Object[] args) {
        if (Objects.isNull(args) || args.length == 0) {
            return StringConstant.EMPTY_JSON;
        }
        Map<String, LogInfo> paramMap = new LinkedHashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            String parameterName = parameterNames[i];
            Object arg = args[i];
            if (filterArgs(arg)) {
                LogInfo logInfo = new LogInfo();
                logInfo.setType(logInfo.getClass().getName());
                logInfo.setValue(arg);
                paramMap.put(parameterName, logInfo);
            }
        }
        return JsonUtils.toJsonString(paramMap);
    }

    /**
     * 过滤不需要记录的参数类型
     *
     * @param arg 待过滤的参数对象
     * @return 如果参数需要记录则返回 true，否则返回 false
     */
    private boolean filterArgs(Object arg) {
        return !(arg instanceof HttpServletRequest) && !(arg instanceof MultipartFile)
                && !(arg instanceof HttpServletResponse);
    }

    @Data
    private static class LogInfo {

        private String type;

        private Object value;

    }

}
