package com.xht.framework.mybatis.datapermission.plugins;

import com.xht.framework.mybatis.datapermission.DataPermissionContext;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 描述：自定义mybatis拦截器
 *
 * @author xht
 **/
@Slf4j
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DataPermissionInterceptor implements Interceptor {

    @Setter
    @Autowired
    private DataPermissionContext dataPermissionContext;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 在执行前进行拦截逻辑
        System.out.println("Before executing the database operation...");
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        log.info("Target: {} Args: {}", target, Arrays.toString(args));
        dataPermissionContext.execute(args, mappedStatement, sqlCommandType);
        // 执行原始操作
        Object result = invocation.proceed();
        // 在执行后进行拦截逻辑
        System.out.println("After executing the database operation...");
        return result;
    }


}
