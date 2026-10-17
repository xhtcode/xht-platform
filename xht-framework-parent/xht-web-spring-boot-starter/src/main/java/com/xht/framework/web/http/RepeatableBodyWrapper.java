package com.xht.framework.web.http;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 可重复读取请求体 Request包装器
 * 解决原生ServletRequest输入流只能单次读取问题，常用于请求日志打印、参数解密、前置校验拦截器
 * 注意：仅用于POST/PUT/PATCH等带请求体接口，排除GET、文件上传multipart请求，避免内存溢出
 * @author xht
 */
@Slf4j
public class RepeatableBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 缓存原始请求体字节数组
     */
    private final byte[] requestBody;

    /**
     * 构造函数：包装原始Request并预加载请求体
     * @param request 原始HttpServletRequest对象
     * @throws IOException 读取输入流异常
     */
    public RepeatableBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 将原始输入流全部读取并缓存为字节数组，后续可重复消费
        this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * 重写获取输入流方法，每次返回全新的ByteArrayInputStream，支持重复读取
     * @return 可重复读取的Servlet输入流
     * @throws IOException IO异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBody);
        return new ServletInputStream() {

            /**
             * 读取单个字节
             */
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            /**
             * 判断流是否读取完成
             */
            @Override
            public boolean isFinished() {
                // 剩余可读字节为0代表已读完
                return byteArrayInputStream.available() == 0;
            }

            /**
             * 判断流是否立即可读（同步阻塞读取直接返回true）
             */
            @Override
            public boolean isReady() {
                return true;
            }

            /**
             * 设置异步读取监听器（同步模式空实现）
             */
            @Override
            public void setReadListener(ReadListener readListener) {
                // 同步读取不处理异步监听
            }
        };
    }

    /**
     * 重写获取字符读取器，基于缓存流构建，保证与输入流数据一致
     * @return BufferedReader
     * @throws IOException IO异常
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    // ===================== 对外扩展工具方法（推荐添加） =====================

    /**
     * 获取原始缓存的字节数组
     * @return byte[]
     */
    public byte[] getRequestBodyBytes() {
        return requestBody.clone();
    }

    /**
     * 获取UTF-8编码的请求体字符串
     * @return 请求Body文本，空请求返回空字符串
     */
    public String getRequestBodyStr() {
        return new String(requestBody, StandardCharsets.UTF_8);
    }

    /**
     * 判断本次请求是否携带有效请求体
     * @return true=有body内容，false=空body
     */
    public boolean hasBody() {
        return requestBody.length > 0;
    }
}