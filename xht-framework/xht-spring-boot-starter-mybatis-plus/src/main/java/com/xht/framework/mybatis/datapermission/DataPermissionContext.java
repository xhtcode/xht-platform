package com.xht.framework.mybatis.datapermission;

import com.xht.framework.ibatis.mapping.XhtSqlSource;
import com.xht.framework.mybatis.datapermission.annoataion.DataPermissions;
import com.xht.framework.mybatis.datapermission.strategy.AbstractDataPermissionStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
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
        BoundSql boundSql = mappedStatement.getBoundSql(args[1]);
        String oldSql = boundSql.getSql();
        Statement statement = CCJSqlParserUtil.parse(oldSql);
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
                        dataPermissionStrategy.executeInsert(dataPermissionBO);
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
                if (statement instanceof Select plain) {
                    PlainSelect plainSelect = plain.getPlainSelect();
                    Expression oldWhere = plainSelect.getWhere();
                    plainSelect.setWhere(createAndExpression(oldWhere, newWhere));
                    updateSqlWhere(args, mappedStatement, boundSql, plainSelect);
                } else if (statement instanceof Update update) {
                    Expression oldWhere = update.getWhere();
                    update.setWhere(createAndExpression(oldWhere, newWhere));
                    updateSqlWhere(args, mappedStatement, boundSql, update);
                } else if (statement instanceof Delete delete) {
                    Expression oldWhere = delete.getWhere();
                    delete.setWhere(createAndExpression(oldWhere, newWhere));
                    updateSqlWhere(args, mappedStatement, boundSql, delete);
                }  else {
                    log.warn("数据权限策略查询不到：{}，statement：{}，SQL 类型：{}",
                            dataPermissionBO.getPermissionType(),
                            statement.getClass().getName(),
                            sqlCommandType);
                }
            }
        }
    }

    /**
     * 创建 AND 连接的条件表达式
     *
     * @param oldWhere 原有的 WHERE 条件
     * @param newWhere 新增的数据权限 WHERE 条件
     * @return 合并后的条件表达式
     */
    private Expression createAndExpression(Expression oldWhere, Expression newWhere) {
        if (Objects.isNull(oldWhere)) {
            return newWhere;
        }
        return new AndExpression(oldWhere, newWhere);
    }

    /**
     * 更新 SQL 的 WHERE 条件到 MyBatis 执行参数中
     *
     * @param args 拦截器方法参数数组
     * @param mappedStatement 原始的 MappedStatement 对象
     * @param boundSql 原始的 BoundSql 对象
     * @param statement 修改后的 JSqlParser Statement 对象
     */
    private void updateSqlWhere(Object[] args, MappedStatement mappedStatement, BoundSql boundSql, Statement statement) {
        XhtSqlSource xhtSqlSource = new XhtSqlSource(mappedStatement.getConfiguration(),
                statement.toString(), boundSql.getParameterMappings());
        if (args.length == 6) {
            args[args.length - 1] = xhtSqlSource.getBoundSql(boundSql.getParameterObject());
        } else {
            args[0] = copyFromMappedStatement(mappedStatement, new XhtSqlSource(mappedStatement.getConfiguration(),
                    statement.toString(), boundSql.getParameterMappings()));
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
            DataPermissions clas = clazz.getAnnotation(DataPermissions.class);
            if (Objects.nonNull(clas)) {
                DATA_PERMISSION_MAP.put(simpleMappedStatementId, DataPermissionBO.builder().of(clas).build());
            }
            Method[] var4 = clazz.getMethods();
            for (Method var8 : var4) {
                DataPermissions methodAnnotation = var8.getAnnotation(DataPermissions.class);
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
