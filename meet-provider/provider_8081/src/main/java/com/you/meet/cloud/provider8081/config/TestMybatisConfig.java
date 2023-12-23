package com.you.meet.cloud.provider8081.config;

import com.you.meet.nice.tk_mybatis.config.AbstractMybatisConfig;
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
@MapperScan(basePackages = TestMybatisConfig.MAPPER_PACKAGE, annotationClass = Mapper.class,
        sqlSessionFactoryRef = TestMybatisConfig.SQL_SESSION_FACTORY)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class TestMybatisConfig extends AbstractMybatisConfig {

    public static final String MAPPER_PACKAGE = "com.you.meet.cloud.provider8081.repository.db.test.mapper";
    public static final String SQL_SESSION_FACTORY = "testSqlSessionFactory";

    private static final String DATA_SOURCE_PROPERTIES_BEAN = "testDataSourceProperties";
    private static final String DATA_SOURCE_PROPERTIES_PREFIX = "spring.datasource.test";
    private static final String DATA_SOURCE_BEAN = "testDataSource";
    private static final String DATA_SOURCE_HIKARI_PREFIX = "spring.datasource.test.hikari";
    private static final String TRANSACTION_MANAGER = "testTransactionManager";

    private static final String MAPPER_XML_LOCATION = "classpath*:mybatis/test/*Mapper.xml";
    private static final String TYPE_ALIASES_PACKAGE = "com.you.meet.cloud.provider8081.repository.db.test.model";

    @Override
    @Bean(DATA_SOURCE_PROPERTIES_BEAN)
    @ConfigurationProperties(DATA_SOURCE_PROPERTIES_PREFIX)
    protected DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Override
    @Bean(DATA_SOURCE_BEAN)
    @ConfigurationProperties(prefix = DATA_SOURCE_HIKARI_PREFIX)
    public HikariDataSource dataSource(@Autowired @Qualifier(DATA_SOURCE_PROPERTIES_BEAN) DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Override
    @Bean(SQL_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier(DATA_SOURCE_BEAN) HikariDataSource dataSource) throws Exception {
        return buildSqlSessionFactory(dataSource);
    }

    @Override
    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager platformTransactionManager(@Autowired @Qualifier(DATA_SOURCE_BEAN) HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public String getMapperLocation() {
        return MAPPER_XML_LOCATION;
    }

    @Override
    public String getTypeAliasesPackage() {
        return TYPE_ALIASES_PACKAGE;
    }

}
