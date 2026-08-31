package com.xht.workflow;

import com.alibaba.qlexpress4.Express4Runner;
import com.alibaba.qlexpress4.InitOptions;
import com.alibaba.qlexpress4.QLOptions;
import com.alibaba.qlexpress4.runtime.trace.TracePointTree;
import com.xht.framework.jackson.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述：
 *
 * @author xht
 **/
public class TestQlexpress {
    public final   static Express4Runner runner = new Express4Runner(InitOptions.DEFAULT_OPTIONS);

    public static void main(String[] args) throws Exception {
        // 1. 创建执行器


        // 2. 准备上下文数据（类似一个Map）
        Map<String, Object> context = new HashMap<>();
        context.put("a", "100");
        context.put("b", 2);

        // 3. 执行脚本，并获取结果
        String script = "a + b * 2";
        List<TracePointTree> expressionTracePoints = runner.getExpressionTracePoints(script);
        System.out.println(JsonUtils.toJsonString(expressionTracePoints));
        Set<String> outVarNames = runner.getOutVarNames(script);
        System.out.println("输出变量: " + outVarNames);
        Object result = runner.execute(script, context, QLOptions.DEFAULT_OPTIONS).getResult();
        System.out.println("计算结果: " + result); // 输出: 计算结果: 140
    }

}
