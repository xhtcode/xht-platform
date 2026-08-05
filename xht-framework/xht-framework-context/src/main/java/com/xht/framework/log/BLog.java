package com.xht.framework.log;

import com.xht.framework.log.enums.LogStatusEnum;

import java.time.LocalDateTime;

/**
 * 描述： 日志信息
 *
 * @author xht
 **/
public interface BLog {

    /**
     * 获取日志名称
     *
     * @return 日志名称
     */
    String getTitle();

    /**
     * 设置日志名称
     *
     * @param title 日志名称
     */
    void setTitle(String title);

    /**
     * 获取日志描述
     *
     * @return 日志描述
     */
    String getLogDesc();

    /**
     * 设置日志描述
     *
     * @param logDesc 日志描述
     */
    void setLogDesc(String logDesc);

    /**
     * 获取全局链路唯一标识
     *
     * @return 链路ID
     */
    String getTraceId();

    /**
     * 设置全局链路唯一标识
     *
     * @param traceId 链路ID
     */
    void setTraceId(String traceId);

    /**
     * 获取当前微服务名称
     *
     * @return 服务名称
     */
    String getServiceName();

    /**
     * 设置当前微服务名称
     *
     * @param serviceName 服务名称
     */
    void setServiceName(String serviceName);

    /**
     * 获取全限定类名‑方法名
     *
     * @return 类名加方法名
     */
    String getClassMethod();

    /**
     * 设置全限定类名‑方法名
     *
     * @param classMethod 类名加方法名
     */
    void setClassMethod(String classMethod);

    /**
     * 获取接口请求参数JSON字符串
     *
     * @return 请求参数
     */
    String getRequestParams();

    /**
     * 设置接口请求参数JSON字符串
     *
     * @param requestParams 请求参数
     */
    void setRequestParams(String requestParams);

    /**
     * 获取服务端本机IP地址
     *
     * @return 服务器地址
     */
    String getServerAddr();

    /**
     * 设置服务端本机IP地址
     *
     * @param serverAddr 服务器地址
     */
    void setServerAddr(String serverAddr);

    /**
     * 获取客户端真实请求IP
     *
     * @return 请求IP
     */
    String getRequestIp();

    /**
     * 设置客户端真实请求IP
     *
     * @param requestIp 请求IP
     */
    void setRequestIp(String requestIp);

    /**
     * 获取请求头JSON信息
     *
     * @return 请求头信息
     */
    String getRequestHeaders();

    /**
     * 设置请求头JSON信息
     *
     * @param requestHeaders 请求头信息
     */
    void setRequestHeaders(String requestHeaders);

    /**
     * 获取操作人登录账号
     *
     * @return 请求账号
     */
    String getRequestAccount();

    /**
     * 设置操作人登录账号
     *
     * @param requestAccount 请求账号
     */
    void setRequestAccount(String requestAccount);

    /**
     * 获取HTTP请求方式
     *
     * @return 请求类型
     */
    String getRequestType();

    /**
     * 设置HTTP请求方式
     *
     * @param requestType 请求类型
     */
    void setRequestType(String requestType);

    /**
     * 获取接口请求开始时间
     *
     * @return 执行时间
     */
    LocalDateTime getExecuteTime();

    /**
     * 设置接口请求开始时间
     *
     * @param executeTime 执行时间
     */
    void setExecuteTime(LocalDateTime executeTime);

    /**
     * 获取接口执行耗时，单位毫秒
     *
     * @return 执行耗时
     */
    Long getExecuteCost();

    /**
     * 设置接口执行耗时，单位毫秒
     *
     * @param executeCost 执行耗时
     */
    void setExecuteCost(Long executeCost);

    /**
     * 获取接口执行状态枚举
     *
     * @return 执行状态
     */
    LogStatusEnum getExecuteStatus();

    /**
     * 设置接口执行状态枚举
     *
     * @param executeStatus 执行状态
     */
    void setExecuteStatus(LogStatusEnum executeStatus);

    /**
     * 获取异常堆栈详情，接口失败时赋值
     *
     * @return 执行异常信息
     */
    String getExecuteException();

    /**
     * 设置异常堆栈详情
     *
     * @param executeException 执行异常信息
     */
    void setExecuteException(String executeException);

}
