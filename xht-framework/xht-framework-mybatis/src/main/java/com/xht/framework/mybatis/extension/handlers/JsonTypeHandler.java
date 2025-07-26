package com.xht.framework.mybatis.extension.handlers;

import com.xht.framework.core.jackson.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;


@Slf4j
@MappedTypes({Set.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonTypeHandler extends BaseTypeHandler<Set<String>> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtils.toJsonString(parameter));
    }

    /**
     * Gets the nullable result.
     *
     * @param rs         the rs
     * @param columnName Column name, when configuration <code>useColumnLabel</code> is <code>false</code>
     * @return the nullable result
     * @throws SQLException the SQL exception
     */
    @Override
    public Set<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JsonUtils.toObject(rs.getString(columnName), Object.class);
    }

    @Override
    public Set<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JsonUtils.toObject(rs.getString(columnIndex), Object.class);
    }

    @Override
    public Set<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JsonUtils.toObject(cs.getString(columnIndex), Object.class);
    }
}