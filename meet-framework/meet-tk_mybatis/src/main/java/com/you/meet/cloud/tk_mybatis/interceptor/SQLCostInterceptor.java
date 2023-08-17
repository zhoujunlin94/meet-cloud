package com.you.meet.cloud.tk_mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * @author zhoujunlin
 * @date 2023年04月15日 15:58
 * @desc 统计sql执行时间拦截器
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class}),
})
public class SQLCostInterceptor implements Interceptor {

    private boolean enable = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();

            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

            // 格式化sql语句  去除换行符  替换参数
            log.info("执行SQL语句:{}", sql);
            log.info("执行耗时:{}ms", endTime - startTime);

        }
    }

    @Override
    public Object plugin(Object target) {
        if (enable && target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以给这个类定义一些成员比如开关啥的  实例化这个类的时候通过properties赋值
        try {
            this.enable = Boolean.parseBoolean(properties.getProperty("enable"));
        } catch (Exception e) {
            log.error("SQLCostInterceptor属性初始化出错", e);
        }
    }

}
