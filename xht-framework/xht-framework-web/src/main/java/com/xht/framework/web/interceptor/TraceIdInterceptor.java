package com.xht.framework.web.interceptor;

import com.xht.framework.log.utils.TraceIdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.xht.framework.log.constat.LogConstant.REQUEST_TRACE_ID;

@Slf4j
public class TraceIdInterceptor implements HandlerInterceptor {

    /**
     * 在处理器执行前的拦截点。在 HandlerMapping 确定了一个合适的处理器对象之后，
     * 但在 HandlerAdapter 调用该处理器之前调用。
     * <p>DispatcherServlet 会按照执行链来处理一个处理器，该执行链包括任意数量的拦截器，
     * 处理器本身位于链条末尾。通过此方法，每个拦截器可以决定是否中止执行链，
     * 通常发送一个 HTTP 错误或者写入自定义响应。
     * <p><strong>注意：</strong>异步请求处理有特殊考虑。更多详情请参见
     * {@link AsyncHandlerInterceptor}。
     * <p>默认实现返回 {@code true}。
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  选择执行的处理器，可用于类型和/或实例评估
     * @return {@code true} 如果执行链应该继续下一个拦截器或处理器本身。
     * 否则，DispatcherServlet 认为这个拦截器已经自行处理了响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String traceId = request.getHeader(REQUEST_TRACE_ID);
        if (traceId == null) {
            traceId = TraceIdUtils.generateTraceId();
        }
        TraceIdUtils.putTraceId(traceId);
        return true;
    }

    /**
     * 请求处理完成后的回调，即在渲染视图之后调用。
     * 不论处理器执行结果如何都会被调用，因此允许适当的资源清理。
     * <p>注意：只有当前置拦截器的 {@code preHandle} 方法成功完成并返回 {@code true} 时才会被调用！
     * <p>与 {@code postHandle} 方法类似，该方法也将在链条中的每个拦截器上以相反顺序调用，
     * 所以第一个拦截器将是最后一个被调用的。
     * <p><strong>注意：</strong>异步请求处理有特殊考虑。更多详情请参见
     * {@link AsyncHandlerInterceptor}。
     * <p>默认实现为空。
     *
     * @param request  当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler  开始异步执行的处理器（或 {@link HandlerMethod}），
     *                 用于类型和/或实例检查
     * @param ex       处理器执行过程中抛出的任何异常；不包括已通过异常解析器处理的异常
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        TraceIdUtils.removeTraceId();
    }


}