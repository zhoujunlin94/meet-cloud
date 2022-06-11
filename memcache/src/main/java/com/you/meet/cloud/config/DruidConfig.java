package com.you.meet.cloud.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * @author zhoujl
 * @date 2021/4/28 15:26
 * @desc
 */
@Configuration
public class DruidConfig {

    /**
     * 数据源base配置   使用AtomikosDataSourceBean 支持多数据源事务
     *
     * @param env
     * @return Primary 指定主库  （必须指定一个主库 否则会报错）
     */
    @Bean(name = "baseDataSource")
    @Primary
    @Autowired
    public AtomikosDataSourceBean baseDataSource(Environment env) {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = build(env, "spring.datasource.druid.", "base");
        ds.setXaDataSourceClassName(env.getProperty("spring.datasource.type"));
        ds.setUniqueResourceName("baseDataSource");
        ds.setPoolSize(5);
        ds.setXaProperties(prop);
        return ds;
    }

    /**
     * 数据源meet配置  使用AtomikosDataSourceBean 支持多数据源事务
     *
     * @param env
     * @return
     */
    @Autowired
    @Bean(name = "meetDataSource")
    public AtomikosDataSourceBean meetDataSource(Environment env) {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        Properties prop = build(env, "spring.datasource.druid.", "meet");
        ds.setXaDataSourceClassName(env.getProperty("spring.datasource.type"));
        ds.setUniqueResourceName("meetDataSource");
        ds.setPoolSize(5);
        ds.setXaProperties(prop);
        return ds;
    }

    /**
     * 从配置文件中加载数据源信息
     *
     * @param env
     * @param prefix
     * @return
     */
    private Properties build(Environment env, String prefix, String dbName) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + dbName + ".url"));
        prop.put("username", env.getProperty(prefix + dbName + ".username"));
        prop.put("password", env.getProperty(prefix + dbName + ".password"));
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name", ""));
        prop.put("initialSize", env.getProperty(prefix + "initial-size", Integer.class));
        prop.put("maxActive", env.getProperty(prefix + "max-active", Integer.class));
        prop.put("minIdle", env.getProperty(prefix + "min-idle", Integer.class));
        prop.put("maxWait", env.getProperty(prefix + "max-wait", Integer.class));
        prop.put("poolPreparedStatements", env.getProperty(prefix + "pool-prepared-statements", Boolean.class));
        prop.put("maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(prefix + "max-pool-prepared-statement-per-connection-size", Integer.class));
        prop.put("maxOpenPreparedStatements",
                env.getProperty(prefix + "max-open-prepared-statements", Integer.class));
        prop.put("validationQuery", env.getProperty(prefix + "validation-query"));
        prop.put("validationQueryTimeout", env.getProperty(prefix + "validation-query-timeout", Integer.class));
        prop.put("testOnBorrow", env.getProperty(prefix + "test-on-borrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(prefix + "test-on-return", Boolean.class));
        prop.put("testWhileIdle", env.getProperty(prefix + "test-while-idle", Boolean.class));
        prop.put("timeBetweenEvictionRunsMillis",
                env.getProperty(prefix + "time-between-eviction-runs-millis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "min-evictable-idle-time-millis", Integer.class));
        String filters = env.getProperty(prefix + "filters");
        if (StrUtil.isNotBlank(filters)) {
            prop.put("filters", filters);
        }
        return prop;
    }
}
