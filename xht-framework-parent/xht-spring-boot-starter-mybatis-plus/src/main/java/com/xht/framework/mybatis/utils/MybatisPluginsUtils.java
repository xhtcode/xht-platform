package com.xht.framework.mybatis.utils;

import com.xht.framework.exception.UtilException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 描述：mybatis 插件工具类
 *
 * @author xht
 **/
@Slf4j
public final class MybatisPluginsUtils {

    private MybatisPluginsUtils() {
        throw new UtilException("工具类不允许实例化");
    }

    /**
     * 复制一个MappedStatement对象
     * @param ms          原MappedStatement对象
     * @param newSqlSource 新的SqlSource对象
     * @return 复制后的MappedStatement对象
     **/
    public static MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.fetchSize(ms.getFetchSize());
        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        builder.resultOrdered(ms.isResultOrdered());
        builder.keyGenerator(ms.getKeyGenerator());
        if (null != ms.getKeyProperties() && ms.getKeyProperties().length > 0) {
            builder.keyProperty(String.join(",", ms.getKeyProperties()));
        }
        if (null != ms.getKeyColumns() && ms.getKeyColumns().length > 0) {
            builder.keyColumn(String.join(",", ms.getKeyColumns()));
        }
        builder.databaseId(ms.getDatabaseId());
        builder.lang(ms.getLang());
        if (null != ms.getResultSets() && ms.getResultSets().length > 0) {
            builder.resultSets(String.join(",", ms.getResultSets()));
        }
        return builder.build();
    }


}
