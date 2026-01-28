package com.xht.modules.utils;

import com.xht.framework.core.exception.UtilException;
import com.xht.framework.core.utils.StringUtils;
import com.xht.modules.generate.entity.GenDataSourceEntity;
import lombok.Getter;

import java.util.Objects;

/**
 * 数据源配置类，基于建造者模式实现灵活配置
 * 包含必要参数校验和默认值管理，确保配置合法性
 *
 * @author xht
 */
@Getter
@SuppressWarnings("all")
public class JDBCConfig {

    /**
     * 必要参数（无默认值，必须配置）
     * 数据库连接URL（如：sql:mysql://localhost:3306/test）
     */
    private final String url;

    /**
     * 数据库登录用户名
     */
    private final String username;

    /**
     * 数据库登录密码（可为null）
     */
    private final String password;

    /**
     * JDBC驱动类全限定名（如：com.mysql.cj.sql.Driver）
     */
    private final String driverClassName;

    /**
     * 连接池参数（带默认值，可按需覆盖）
     * 连接测试SQL，默认"SELECT 1"
     */
    private final String connectionTestQuery;

    /**
     * 连接超时时间（毫秒），默认60000ms
     */
    private final long connectionTimeout;

    /**
     * 最小空闲连接数，默认2
     */
    private final int minimumIdle;

    /**
     * 最大连接池大小，默认10
     */
    private final int maximumPoolSize;

    /**
     * 连接最大存活时间（毫秒），默认600000ms
     */
    private final long maxLifetime;

    /**
     * 连接验证超时时间（毫秒），默认5000ms
     */
    private final long validationTimeout;

    /**
     * 连接空闲超时时间（毫秒），默认300000ms
     */
    private final long idleTimeout;

    /**
     * 连接泄漏检测阈值（毫秒），默认500000ms
     */
    private final long leakDetectionThreshold;

    /**
     * 私有构造方法，通过建造者模式创建实例
     *
     * @param builder 配置建造者
     */
    private JDBCConfig(Builder builder) {
        // 必要参数直接赋值（Builder已做非空校验）
        this.url = builder.getUrl();
        this.username = builder.getUsername();
        this.password = builder.getPassword();
        this.driverClassName = builder.getDriverClassName();
        // 可选参数：优先使用Builder设置的值，未设置则使用默认值
        this.connectionTestQuery = builder.getConnectionTestQuery();
        this.connectionTimeout = builder.getConnectionTimeout();
        this.minimumIdle = builder.getMinimumIdle();
        this.maximumPoolSize = builder.getMaximumPoolSize();
        this.maxLifetime = builder.getMaxLifetime();
        this.validationTimeout = builder.getValidationTimeout();
        this.idleTimeout = builder.getIdleTimeout();
        this.leakDetectionThreshold = builder.getLeakDetectionThreshold();
        // 最终配置校验（确保参数逻辑合法性）
        validateConfig();
    }

    /**
     * 校验配置参数的逻辑合法性
     * 包括连接池大小关系、超时时间关系等
     */
    private void validateConfig() {
        if (minimumIdle > maximumPoolSize) {
            throw new UtilException("最小空闲连接数（" + minimumIdle + "）不能大于最大连接池大小（" + maximumPoolSize + "）");
        }
        if (validationTimeout > connectionTimeout) {
            throw new UtilException("连接验证超时（" + validationTimeout + "ms）不能大于连接超时（" + connectionTimeout + "ms）");
        }
        if (maxLifetime < 0) {
            throw new UtilException("连接最大存活时间不能为负数");
        }
    }


    /**
     * 建造者模式：简化配置创建，支持链式调用
     * 负责参数收集和基础校验
     */
    @Getter
    public static class Builder {

        /**
         * 数据库连接配置类
         *
         * @param url 数据库连接URL
         * @param username 数据库用户名
         * @param password 数据库密码
         * @param driverClassName 数据库驱动类名
         */
        private final String url;
        private final String username;
        private final String password;
        private final String driverClassName;

        /**
         * 连接测试查询语句，用于验证数据库连接的有效性
         */
        private String connectionTestQuery = "SELECT 1";

        /**
         * 连接超时时间（毫秒），获取数据库连接的最大等待时间
         */
        private Long connectionTimeout = 60000L;

        /**
         * 最小空闲连接数，池中保持的最小空闲连接数量
         */
        private Integer minimumIdle = 2;

        /**
         * 最大连接池大小，池中允许的最大连接数量
         */
        private Integer maximumPoolSize = 10;

        /**
         * 连接的最大生命周期（毫秒），连接在池中的最大存活时间
         */
        private Long maxLifetime = 600000L;

        /**
         * 验证超时时间（毫秒），连接验证的最大等待时间
         */
        private Long validationTimeout = 5000L;

        /**
         * 空闲连接超时时间（毫秒），连接在池中空闲的最大时间
         */
        private Long idleTimeout = 300000L;

        /**
         * 连接泄漏检测阈值（毫秒），检测连接泄漏的时间阈值
         */
        private Long leakDetectionThreshold = 500000L;


        /**
         * 构造器：初始化必要参数并进行基础校验
         *
         * @param url             数据库连接URL（数据不合法）
         * @param username        登录用户名（数据不合法）
         * @param password        登录密码（可为null）
         * @param driverClassName 驱动类名（数据不合法）
         */
        private Builder(String url, String username, String password, String driverClassName) {
            if (StringUtils.isEmpty(url)) {
                throw new UtilException("数据库URL数据不合法");
            }
            if (StringUtils.isEmpty(username)) {
                throw new UtilException("数据库用户名数据不合法");
            }
            if (StringUtils.isEmpty(password)) {
                throw new UtilException("数据库密码数据不合法");
            }
            if (StringUtils.isEmpty(driverClassName)) {
                throw new UtilException("数据库驱动类名数据不合法");
            }

            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClassName = driverClassName;
        }

        /**
         * 创建一个新的 Builder 实例，用于构建数据库连接配置
         *
         * @param dataSourceEntity 数据库链接对象
         * @return 返回一个新的 Builder 实例
         */
        public static Builder of(GenDataSourceEntity dataSourceEntity) {
            return of(dataSourceEntity.getUrl(), dataSourceEntity.getUsername(), dataSourceEntity.getPassword(), dataSourceEntity.getDbType().getDriverClassName());
        }

        /**
         * 创建一个新的 Builder 实例，用于构建数据库连接配置
         *
         * @param url             数据库连接URL
         * @param username        数据库用户名
         * @param password        数据库密码
         * @param driverClassName 数据库驱动类名
         * @return 返回一个新的 Builder 实例
         */
        public static Builder of(String url, String username, String password, String driverClassName) {
            return new Builder(url, username, password, driverClassName);
        }

        /**
         * 设置连接测试SQL
         *
         * @param connectionTestQuery 连接测试SQL语句
         * @return 建造者自身
         */
        public Builder connectionTestQuery(String connectionTestQuery) {
            if (Objects.isNull(connectionTestQuery) || connectionTestQuery.trim().isEmpty()) {
                throw new UtilException("连接测试SQL数据不合法");
            }
            this.connectionTestQuery = connectionTestQuery;
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectionTimeout 超时时间（毫秒），必须大于0
         * @return 建造者自身
         */
        public Builder connectionTimeout(long connectionTimeout) {
            if (connectionTimeout <= 0) {
                throw new UtilException("连接超时时间必须大于0（当前值：" + connectionTimeout + "）");
            }
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        /**
         * 设置最小空闲连接数
         *
         * @param minimumIdle 最小空闲数，不能为负数
         * @return 建造者自身
         */
        public Builder minimumIdle(int minimumIdle) {
            if (minimumIdle < 0) {
                throw new UtilException("最小空闲连接数不能为负数（当前值：" + minimumIdle + "）");
            }
            this.minimumIdle = minimumIdle;
            return this;
        }

        /**
         * 设置最大连接池大小
         *
         * @param maximumPoolSize 最大连接数，必须大于0
         * @return 建造者自身
         */
        public Builder maximumPoolSize(int maximumPoolSize) {
            if (maximumPoolSize <= 0) {
                throw new UtilException("最大连接池大小必须大于0（当前值：" + maximumPoolSize + "）");
            }
            this.maximumPoolSize = maximumPoolSize;
            return this;
        }

        /**
         * 设置连接最大存活时间
         *
         * @param maxLifetime 存活时间（毫秒），不能为负数
         * @return 建造者自身
         */
        public Builder maxLifetime(long maxLifetime) {
            if (maxLifetime < 0) {
                throw new UtilException("连接最大存活时间不能为负数（当前值：" + maxLifetime + "）");
            }
            this.maxLifetime = maxLifetime;
            return this;
        }

        /**
         * 设置连接验证超时时间
         *
         * @param validationTimeout 超时时间（毫秒），必须大于0
         * @return 建造者自身
         */
        public Builder validationTimeout(long validationTimeout) {
            if (validationTimeout <= 0) {
                throw new UtilException("连接验证超时必须大于0（当前值：" + validationTimeout + "）");
            }
            this.validationTimeout = validationTimeout;
            return this;
        }

        /**
         * 设置连接空闲超时时间
         *
         * @param idleTimeout 超时时间（毫秒），不能为负数
         * @return 建造者自身
         */
        public Builder idleTimeout(long idleTimeout) {
            if (idleTimeout < 0) {
                throw new UtilException("连接空闲超时不能为负数（当前值：" + idleTimeout + "）");
            }
            this.idleTimeout = idleTimeout;
            return this;
        }

        /**
         * 设置连接泄漏检测阈值
         *
         * @param leakDetectionThreshold 阈值（毫秒），不能为负数
         * @return 建造者自身
         */
        public Builder leakDetectionThreshold(long leakDetectionThreshold) {
            if (leakDetectionThreshold < 0) {
                throw new UtilException("泄漏检测阈值不能为负数（当前值：" + leakDetectionThreshold + "）");
            }
            this.leakDetectionThreshold = leakDetectionThreshold;
            return this;
        }

        /**
         * 构建配置对象
         *
         * @return 初始化完成的JDBCConfig实例
         */
        public JDBCConfig build() {
            return new JDBCConfig(this);
        }
    }
}
