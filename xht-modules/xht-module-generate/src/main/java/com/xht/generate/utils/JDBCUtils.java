package com.xht.generate.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Closeable;

/**
 * JDBC工具类，用于创建JdbcTemplate并管理数据源生命周期
 * 支持try-with-resources语法自动关闭资源
 *
 * @author 小糊涂
 */
@Slf4j
public class JDBCUtils implements Closeable {

    @Getter
    private final JdbcTemplate jdbcTemplate;

    private final HikariDataSource dataSource;

    /**
     * 私有构造方法，通过工厂方法创建实例
     */
    private JDBCUtils(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 创建JDBC工具类实例
     *
     * @param config 数据源配置
     * @return JDBC工具类实例
     */
    public static JDBCUtils create(JDBCConfig config) {
        // 使用HikariConfig构建数据源，更符合官方推荐方式
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriverClassName());

        // 配置连接池参数，使用用户配置或默认值
        hikariConfig.setConnectionTestQuery(config.getConnectionTestQuery());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        hikariConfig.setMinimumIdle(config.getMinimumIdle());
        hikariConfig.setMaximumPoolSize(config.getMaximumPoolSize());
        hikariConfig.setMaxLifetime(config.getMaxLifetime());
        hikariConfig.setValidationTimeout(config.getValidationTimeout());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setLeakDetectionThreshold(config.getLeakDetectionThreshold());

        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return new JDBCUtils(dataSource);
    }

    /**
     * 查看链接状态
     *
     * @return 链接状态
     */
    public boolean isRunning() {
        return dataSource.isRunning();
    }

    /**
     * 关闭数据源，释放资源
     */
    @Override
    public void close() {
        if (!dataSource.isClosed()) {
            try {
                dataSource.close();
                log.info("数据源已成功关闭");
            } catch (Exception e) {
                log.error("关闭数据源时发生异常", e);
            }
        }
    }

}
