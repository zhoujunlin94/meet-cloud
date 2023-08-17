package com.you.meet.cloud.provider8081;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;

/**
 * @author zhoujunlin
 * @date 2023年08月16日 22:23
 * @desc
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication(exclude = {MapperAutoConfiguration.class, MybatisAutoConfiguration.class})
public class Provider8081Application {

    public static void main(String[] args) {
        SpringApplication.run(Provider8081Application.class, args);
    }

}
