package com.xht.auth.security.oauth2;

import com.xht.framework.core.exception.UtilException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 描述：自定以oauth2授权端点
 *
 * @author xht
 **/
public final class OAuth2EndpointUtils {

    private OAuth2EndpointUtils() {
        throw new UtilException("工具类不允许实例化");
    }


    /**
     * 从HTTP请求中提取表单参数
     * 该方法会遍历请求的所有参数，区分查询字符串参数和表单参数。
     * 只有不在查询字符串中出现的参数才被认为是表单参数。
     *
     * @param request HTTP请求对象
     * @return 包含表单参数的MultiValueMap，键为参数名，值为参数值列表
     */
    public static MultiValueMap<String, String> getFormParameters(HttpServletRequest request) {
        // 获取请求中的所有参数映射
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameterMap.forEach((key, values) -> {
            // 获取查询字符串，如果为空则使用空字符串
            String queryString = StringUtils.hasText(request.getQueryString()) ? request.getQueryString() : "";
            // 如果参数名不在查询字符串中，并且有值，则认为是表单参数
            if (!queryString.contains(key)) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }
}
