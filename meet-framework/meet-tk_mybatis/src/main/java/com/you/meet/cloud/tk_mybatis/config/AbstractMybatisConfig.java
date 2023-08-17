package com.you.meet.cloud.tk_mybatis.config;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInterceptor;
import com.you.meet.cloud.tk_mybatis.interceptor.SQLCostInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
public abstract class AbstractMybatisConfig {

    protected abstract DataSourceProperties dataSourceProperties();

    protected abstract HikariDataSource dataSource(DataSourceProperties dataSourceProperties);

    protected abstract PlatformTransactionManager platformTransactionManager(HikariDataSource dataSource);

    protected abstract SqlSessionFactory sqlSessionFactory(HikariDataSource dataSource) throws Exception;

    protected SqlSessionFactory buildSqlSessionFactory(HikariDataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(getTypeAliasesPackage());
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(getMapperLocation())
        );
        // 插件
        List<Interceptor> interceptors = new ArrayList<>();
        PageInterceptor pageInterceptor = pageInterceptor();
        if (Objects.nonNull(pageInterceptor)) {
            interceptors.add(pageInterceptor);
        }
        SQLCostInterceptor sqlCostInterceptor = sqlCostInterceptor();
        if (Objects.nonNull(sqlCostInterceptor)) {
            interceptors.add(sqlCostInterceptor);
        }

        if (CollUtil.isNotEmpty(interceptors)) {
            sqlSessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[]{}));
        }

        Configuration configuration = Objects.requireNonNull(sqlSessionFactoryBean.getObject()).getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    protected PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    protected SQLCostInterceptor sqlCostInterceptor() {
        SQLCostInterceptor sqlCostInterceptor = new SQLCostInterceptor();
        Properties properties = new Properties();
        properties.setProperty("enable", "true");
        sqlCostInterceptor.setProperties(properties);
        return sqlCostInterceptor;
    }

    protected abstract String getTypeAliasesPackage();

    protected abstract String getMapperLocation();

}
