package com.xht.framework.utils.spring;

import com.xht.framework.core.spel.MapPropertyAccessor;
import com.xht.framework.exception.UtilException;
import com.xht.framework.utils.StringUtils;
import com.xht.framework.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Spring SpEL 工具类
 *
 * @author xht
 **/
@Slf4j
public abstract class SpELUtils {

    /**
     * SpEL解析器，线程安全，单例复用
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();


    /**
     * 解析SpEL表达式并获取结果
     * <p>适用场景：表达式无需依赖外部变量，如 #{1 + 1}、#{'hello'.toUpperCase()}</p>
     *
     * @param expressionStr SpEL表达式字符串
     * @param clazz         返回结果类型
     * @param <T>           返回结果类型
     * @return 表达式求值结果, 解析失败返回null
     */
    public static <T> T parseExpression(String expressionStr, Class<T> clazz) {
        return parseExpression(expressionStr, clazz, null);
    }

    /**
     * 解析SpEL表达式并获取结果
     * <p>适用场景：表达式无需依赖外部变量，如 #{1 + 1}、#{'hello'.toUpperCase()}</p>
     *
     * @param expressionStr SpEL表达式字符串
     * @param clazz         返回结果类型
     * @param <T>           返回结果类型
     * @param defaultValue  默认值
     * @return 表达式求值结果, 解析失败返回默认值
     */
    public static <T> T parseExpression(String expressionStr, Class<T> clazz, T defaultValue) {
        try {
            Expression exp = PARSER.parseExpression(unwrapSpELMarker(expressionStr));
            return exp.getValue(clazz);
        } catch (Exception e) {
            log.error("SpEL表达式解析失败，expression:{}", expressionStr, e);
            return defaultValue;
        }
    }

    /**
     * 支持指定根对象rootObject的解析，可直接写属性名不需要#，如 "a == 1"
     *
     * @param expressionStr 表达式
     * @param rootObject    根对象
     * @param clazz         返回类型
     * @return 表达式求值结果, 解析失败返回null
     */
    public static <T> T parseExpression(String expressionStr, Map<String, Object> rootObject, Class<T> clazz) {
        return parseExpression(expressionStr, rootObject, clazz, null);
    }

    /**
     * 支持指定根对象rootObject的解析，可直接写属性名不需要#，如 "a == 1"
     *
     * @param expressionStr 表达式
     * @param rootObject    根对象
     * @param clazz         返回类型
     * @param defaultValue  默认值
     * @return 表达式求值结果, 解析失败返回默认值
     */
    public static <T> T parseExpression(String expressionStr, Map<String, Object> rootObject, Class<T> clazz, T defaultValue) {
        try {
            StandardEvaluationContext context = new StandardEvaluationContext(rootObject);
            // 加上这个访问器，低版本spring就支持直接 .key 读取map
            context.addPropertyAccessor(new MapPropertyAccessor());
            Expression exp = PARSER.parseExpression(unwrapSpELMarker(expressionStr));
            return exp.getValue(context, clazz);
        } catch (Exception e) {
            log.error("SpEL表达式解析失败，expression:{} rootObject:{}", expressionStr, rootObject, e);
            return defaultValue;
        }
    }

    /**
     * 判断字符串是否是 #{xxx} 格式的SpEL
     *
     * @param str 输入字符串
     * @return true代表是#{包裹的spEL}
     */
    private static boolean isSpElWithMarker(String str) {
        if (StringUtils.isEmpty(str) || str.length() < 3) {
            return false;
        }
        return str.startsWith("#{") && str.endsWith("}");
    }

    /**
     * 剥离 #{ } 获取真实spel表达式
     */
    private static String unwrapSpELMarker(String spELMarker) {
        ThrowUtils.throwIf(!isSpElWithMarker(spELMarker), () -> new UtilException("非法的spEL表达式:" + spELMarker));
        return spELMarker.substring(2, spELMarker.length() - 1);
    }

}
