package com.you.meet.nice.starter.redis.config;

import com.you.meet.nice.starter.redis.pojo.queue.RedisDelayingQueue;
import com.you.meet.nice.starter.redis.service.RedisService;
import com.you.meet.nice.starter.redis.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: zhoujl
 * @Description:Redis配置文件
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }

    @Bean("redisService")
    @Primary
    public RedisService redisService(@Autowired RedisTemplate redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }

    @Bean
    public RedisDelayingQueue redisDelayingQueue(@Autowired RedisService redisService) {
        return new RedisDelayingQueue(redisService);
    }
}
