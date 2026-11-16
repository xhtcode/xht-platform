package com.xht.framework.ibatis.mapping;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * 描述：sql source
 *
 * @author xht
 **/
public record XhtSqlSource(Configuration configuration, String sql,
                           List<ParameterMapping> parameterMappings) implements SqlSource {

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}
