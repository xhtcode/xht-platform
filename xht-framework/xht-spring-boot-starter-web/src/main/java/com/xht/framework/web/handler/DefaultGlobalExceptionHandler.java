package com.xht.framework.web.handler;


import com.xht.framework.common.domain.ErrorR;
import com.xht.framework.common.domain.R;
import com.xht.framework.exception.BusinessException;
import com.xht.framework.exception.ValidationException;
import com.xht.framework.exception.code.GlobalErrorStatusCode;
import com.xht.framework.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自定义全局异常
 *
 * @author xht
 * @see Exception
 * @see Throwable
 * @see BindException
 * @see NoHandlerFoundException
 * @see NoResourceFoundException
 **/
@Slf4j
@RestControllerAdvice
public class DefaultGlobalExceptionHandler implements Serializable {

    /**
     * 默认参数校验失败信息
     */
    private static final String MESSAGE = GlobalErrorStatusCode.PARAM_INVALID.getMsg();

    /**
     * 参数校验失败信息key
     */
    private static final String PARAMS_ERROR_KEY = "params";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 捕获 {@link Exception} 异常
     */
    @ExceptionHandler(value = Exception.class)
    public ErrorR<Void> handle(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return new ErrorR<>(R.error().info(GlobalErrorStatusCode.ERROR).build());
    }

    /**
     * 捕获 {@link BusinessException}  异常
     */
    @ExceptionHandler(value = {BusinessException.class})
    public ErrorR<Void> handle(BusinessException e) {
        log.error("自定义异常: code={} MESSAGE={}", e.getCode(), e.getMessage(), e);
        return new ErrorR<>(R.error(e.getCode()).msg(e.getMsg()).build());
    }


    /**
     * controller 接口拦截  {@link NoHandlerFoundException} 以及 {@link NoResourceFoundException}
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class, NoResourceFoundException.class})
    public ErrorR<Void> handle() {
        return new ErrorR<>(R.error().info(GlobalErrorStatusCode.NOT_FOUND).build());
    }


    /**
     * 错误的请求  {@link HttpRequestMethodNotSupportedException}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorR<Void> handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.debug(" {} 请求方法不支持: {}", request.getRequestURI(), e.getMessage(), e);
        return new ErrorR<>(R.error().info(GlobalErrorStatusCode.METHOD_NOT_ALLOWED).build());
    }

    /**
     * jsr 303校验异常捕获
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public ErrorR<Void> handleException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        BindingResult bindingResult = e.getBindingResult();
        Map<String, Object> resultMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(fieldErrors)) {
                for (FieldError fieldError : fieldErrors) {
                    resultMap.put(fieldError.getField(), StringUtils.emptyToDefault(fieldError.getDefaultMessage(), "参数校验失败"));
                }
            }
        }
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,codeData={}", requestURI, request.getMethod(), resultMap, e);
        return new ErrorR<>(R.error().info(GlobalErrorStatusCode.PARAM_INVALID).build(), Map.of(PARAMS_ERROR_KEY, resultMap));
    }

    /**
     * 自定义校验异常捕获
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(ValidationException.class)
    public ErrorR<Void> handleException(ValidationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(e.getField(), StringUtils.emptyToDefault(e.getMessage(), MESSAGE));
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,codeData={}", requestURI, request.getMethod(), resultMap, e);
        return new ErrorR<>(R.error().info(GlobalErrorStatusCode.PARAM_INVALID).build(), Map.of(PARAMS_ERROR_KEY, resultMap));
    }

}
