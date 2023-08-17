package com.you.meet.cloud.config;

import com.you.meet.cloud.tk_mybatis.config.AbstractMybatisConfig;
import com.you.meet.cloud.tk_mybatis.interceptor.SQLCostInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zhoujunlin
 * @date 2023/03/27
 **/
@Configuration
@MapperScan(basePackages = "com.you.meet.cloud.repository.db.test.mapper", annotationClass = Mapper.class,
        sqlSessionFactoryRef = "testSqlSessionFactory")
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class TestMybatisConfig extends AbstractMybatisConfig {

    public static final String DATA_SOURCE_PROPERTIES = "testDataSourceProperties";
    public static final String DATA_SOURCE = "testDataSource";
    public static final String SQL_SESSION_FACTORY = "testSqlSessionFactory";
    public static final String TRANSACTION_MANAGER = "testTransactionManager";

    private static final String MAPPER_LOCATION = "classpath:mybatis/test/*Mapper.xml";
    private static final String TYPE_ALIASES_PACKAGE = "com.you.meet.cloud.repository.db.test.model";

    @Override
    @Bean(DATA_SOURCE_PROPERTIES)
    @ConfigurationProperties("spring.datasource.test")
    protected DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Bean(DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.test.hikari")
    public HikariDataSource dataSource(@Autowired @Qualifier(DATA_SOURCE_PROPERTIES) DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Override
    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) throws Exception {
        return buildSqlSessionFactory(dataSource);
    }

    @Override
    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager platformTransactionManager(@Autowired @Qualifier(DATA_SOURCE) HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public String getMapperLocation() {
        return MAPPER_LOCATION;
    }

    @Override
    public String getTypeAliasesPackage() {
        return TYPE_ALIASES_PACKAGE;
    }

    @Override
    protected SQLCostInterceptor sqlCostInterceptor() {
        return null;
    }

}
