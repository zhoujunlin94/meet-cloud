package com.you.meet.cloud.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.you.meet.cloud.lib.api.common.constant.CommonConstant;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author zhoujl
 * @date 2021/4/28 15:40
 * @desc
 */
@Configuration
@MapperScan(basePackages = "com.you.meet.cloud.infrastructure.mysql.base.mapper", sqlSessionFactoryRef = "baseSqlSessionFactory")
public class BaseDataSourceConfig {

    @Resource
    private ServiceConfig serviceConfig;

    @Primary
    @Bean(name = "baseSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        //配置myabtisSqlSession
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        // 指明mapper.xml位置(配置文件中指明的xml位置会失效用此方式代替，具体原因未知)
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/base/*/*.xml"));
        // 指明实体扫描(多个package用逗号或者分号分隔)
        sessionFactoryBean.setTypeAliasesPackage("com.you.meet.cloud.infrastructure.mysql.base.model");

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        //驼峰
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        //是否开启缓存
        mybatisConfiguration.setCacheEnabled(false);
        //多数据源下分页模式
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        mybatisConfiguration.addInterceptor(mybatisPlusInterceptor);
        if (CommonConstant.SWITCH_OPEN.equals(serviceConfig.getMybatisConf().getLogSwitch())) {
            // 配置打印sql语句
            mybatisConfiguration.setLogImpl(StdOutImpl.class);
        }
        sessionFactoryBean.setConfiguration(mybatisConfiguration);
        //数据源注入
        sessionFactoryBean.setDataSource(dataSource);
        //多数据源下 全局配置失效 需要手动设值
        sessionFactoryBean.setGlobalConfig(globalConfig());
        return sessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "baseSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        return globalConfig;
    }
}
