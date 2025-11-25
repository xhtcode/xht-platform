package com.xht.framework.web.handler;


import com.xht.framework.core.domain.R;
import com.xht.framework.core.exception.BusinessException;
import com.xht.framework.core.exception.ValidationException;
import com.xht.framework.core.exception.code.GlobalErrorStatusCode;
import com.xht.framework.core.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 捕获 {@link Exception} 异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.error(GlobalErrorStatusCode.ERROR, "系统未知异常，请联系管理员!");
    }

    /**
     * 捕获 {@link BusinessException}  异常
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> handle(BusinessException e) {
        log.error("自定义异常: code={} MESSAGE={}", e.getCode(), e.getMessage(), e);
        return new R<>(e.getCode(), false, e.getMessage());
    }


    /**
     * controller 接口拦截  {@link NoHandlerFoundException}
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<String> handle(NoHandlerFoundException e, HttpServletRequest request) {
        log.debug(" {} 请求URL404: {}", request.getRequestURI(), e.getMessage(), e);
        return R.error(GlobalErrorStatusCode.NOT_FOUND);
    }

    /**
     * 静态资源拦截 {@link NoResourceFoundException}
     */
    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<String> handle(NoResourceFoundException e, HttpServletRequest request) {
        log.debug(" {} 请求URL404: {}", request.getRequestURI(), e.getMessage(), e);
        return R.error(GlobalErrorStatusCode.NOT_FOUND);
    }

    /**
     * 错误的请求  {@link HttpRequestMethodNotSupportedException}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<String> handle(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.debug(" {} 请求方法不支持: {}", request.getRequestURI(), e.getMessage(), e);
        return R.error(GlobalErrorStatusCode.METHOD_NOT_ALLOWED);
    }

    /**
     * jsr 303校验异常捕获
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Map<String, Object>> handleException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        BindingResult bindingResult = e.getBindingResult();
        String message = GlobalErrorStatusCode.PARAM_INVALID.getMsg();
        Map<String, Object> resultMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (!CollectionUtils.isEmpty(fieldErrors)) {
                message = StringUtils.emptyToDefault(fieldErrors.get(0).getDefaultMessage(), "参数校验失败");
                for (FieldError fieldError : fieldErrors) {
                    resultMap.put(fieldError.getField(), StringUtils.emptyToDefault(fieldError.getDefaultMessage(), "参数校验失败"));
                }
            }
        }
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,codeData={}", requestURI, request.getMethod(), resultMap, e);
        R<Map<String, Object>> mapR = R.errorData(GlobalErrorStatusCode.PARAM_INVALID, resultMap);
        mapR.setMsg(message);
        return mapR;
    }

    /**
     * 自定义校验异常捕获
     *
     * @param e       BindException
     * @param request HttpServletRequest
     * @return Result
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Map<String, Object>> handleException(ValidationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(e.getField(), StringUtils.emptyToDefault(e.getMessage(), MESSAGE));
        log.warn("请求地址:{}参数检验失败,请求方式：{} ,codeData={}", requestURI, request.getMethod(), resultMap, e);
        R<Map<String, Object>> mapR = R.errorData(GlobalErrorStatusCode.PARAM_INVALID, resultMap);
        mapR.setMsg(MESSAGE);
        return mapR;
    }

}
