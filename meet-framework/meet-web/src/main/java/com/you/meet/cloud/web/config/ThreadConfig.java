package com.you.meet.cloud.web.config;

import com.you.meet.cloud.common.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhoujunlin
 * @date 2023年03月26日 21:31
 * @desc
 */
@EnableAsync
@Configuration
public class ThreadConfig {

    /**
     * 当拒绝处理任务时的策略:由主线程处理该任务
     */
    @Primary
    @Bean(CommonConstant.MEET_THREAD_POOL)
    public ThreadPoolTaskExecutor meetThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数  IO密集型 2*n+1  CPU密集型  n+1
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        // 最大线程数 - 核心线程数 = 救急线程数
        executor.setMaxPoolSize(100);
        // 救急线程保活时间
        executor.setKeepAliveSeconds(300);
        // 队列容量  有界  LinkedBlockingQueue
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("meet-pool-");
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //用来设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
