package com.xht.framework.mybatis.datapermission;

import com.xht.framework.ibatis.mapping.XhtSqlSource;
import com.xht.framework.mybatis.datapermission.annoataion.DataPermission;
import com.xht.framework.mybatis.datapermission.strategy.AbstractDataPermissionStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.xht.framework.mybatis.utils.MybatisPluginsUtils.copyFromMappedStatement;

/**
 * 描述：
 *
 * @author xht
 **/
@Slf4j
public record DataPermissionContext(List<AbstractDataPermissionStrategy> dataPermissionStrategies) {

    private static final Map<String, DataPermissionBO> DATA_PERMISSION_MAP = new ConcurrentHashMap<>();

    public DataPermissionContext(List<AbstractDataPermissionStrategy> dataPermissionStrategies) {
        if (!CollectionUtils.isEmpty(dataPermissionStrategies)) {
            this.dataPermissionStrategies = dataPermissionStrategies.stream().sorted(Comparator.comparingInt(AbstractDataPermissionStrategy::getOrder)).toList();
        } else {
            this.dataPermissionStrategies = Collections.emptyList();
        }
    }

    /**
     * 执行数据权限过滤
     *
     * @param args 方法参数
     * @param mappedStatement 当前 SQL 语句的 MappedStatement 对象
     * @param sqlCommandType 当前 SQL 语句的 SqlCommandType 对象
     */
    @SneakyThrows
    public void execute(Object[] args, MappedStatement mappedStatement, SqlCommandType sqlCommandType) {
        String mappedStatementId = mappedStatement.getId();
        String simpleMappedStatementId = getClassName(mappedStatementId);
        DataPermissionBO dataPermissionBO = DATA_PERMISSION_MAP.get(mappedStatementId);
        if (Objects.isNull(dataPermissionBO)) {
            dataPermissionBO = DATA_PERMISSION_MAP.getOrDefault(mappedStatementId, DATA_PERMISSION_MAP.get(simpleMappedStatementId));
        }
        if (Objects.isNull(dataPermissionBO)) {
            initDataPermission(simpleMappedStatementId);
            dataPermissionBO = DATA_PERMISSION_MAP.getOrDefault(mappedStatementId, DATA_PERMISSION_MAP.get(simpleMappedStatementId));
        }
        if (Objects.nonNull(dataPermissionBO) && !dataPermissionBO.isIgnore() && !CollectionUtils.isEmpty(dataPermissionStrategies)) {
            Expression newWhere = null;
            for (AbstractDataPermissionStrategy dataPermissionStrategy : dataPermissionStrategies) {
                if (Objects.nonNull(newWhere)) {
                    continue;
                }
                boolean support = dataPermissionStrategy.support(dataPermissionBO.getPermissionType(), mappedStatementId);
                if (support) {
                    if (sqlCommandType == SqlCommandType.INSERT) {
                        newWhere = dataPermissionStrategy.executeInsert(dataPermissionBO);
                    } else if (sqlCommandType == SqlCommandType.DELETE) {
                        newWhere = dataPermissionStrategy.executeDelete(dataPermissionBO);
                    } else if (sqlCommandType == SqlCommandType.UPDATE) {
                        newWhere = dataPermissionStrategy.executeUpdate(dataPermissionBO);
                    } else if (sqlCommandType == SqlCommandType.SELECT) {
                        newWhere = dataPermissionStrategy.executeSelect(dataPermissionBO);
                    } else {
                        log.warn("不支持的 SQL 类型：{}", sqlCommandType);
                    }
                }
            }
            if (Objects.nonNull(newWhere)) {
                BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
                String sql = boundSql.getSql();
                Statement stmt = CCJSqlParserUtil.parse(sql);
                PlainSelect plain = ((Select) stmt).getPlainSelect();
                Expression where = plain.getWhere();
                if (Objects.isNull(where)) {
                    plain.setWhere(newWhere);
                } else {
                    plain.setWhere(new AndExpression(where, newWhere));
                }
                XhtSqlSource xhtSqlSource = new XhtSqlSource(mappedStatement.getConfiguration(),
                        stmt.toString(), boundSql.getParameterMappings());
                if (args.length == 6) {
                    args[args.length - 1] = xhtSqlSource.getBoundSql(boundSql.getParameterObject());
                } else {
                    args[0] = copyFromMappedStatement(mappedStatement, new XhtSqlSource(mappedStatement.getConfiguration(),
                            stmt.toString(), boundSql.getParameterMappings()));
                }
            }
        }
    }

    /**
     * 初始化数据权限
     *
     * @param simpleMappedStatementId 简单的 mappedStatement id
     */
    private void initDataPermission(String simpleMappedStatementId) {
        try {
            Class<?> clazz = Class.forName(simpleMappedStatementId);
            DataPermission clas = clazz.getAnnotation(DataPermission.class);
            if (Objects.nonNull(clas)) {
                DATA_PERMISSION_MAP.put(simpleMappedStatementId, DataPermissionBO.builder().of(clas).build());
            }
            Method[] var4 = clazz.getMethods();
            for (Method var8 : var4) {
                DataPermission methodAnnotation = var8.getAnnotation(DataPermission.class);
                if (Objects.nonNull(methodAnnotation)) {
                    DATA_PERMISSION_MAP.put(simpleMappedStatementId + "." + var8.getName(), DataPermissionBO.builder().of(methodAnnotation).build());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据 mappedStatement id 获取类名
     * @param id mappedStatement id
     * @return 类名
     */
    private static String getClassName(String id) {
        return id.substring(0, id.lastIndexOf("."));
    }

}
