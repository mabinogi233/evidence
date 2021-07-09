package com.project.evidence.identifyModule.selectThread;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor selectThreadPool(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(16);
        //队列线程数目
        executor.setQueueCapacity(64);
        //最大线程数目
        executor.setMaxPoolSize(128);
        //空闲最大存活时间
        executor.setKeepAliveSeconds(60);
        //线程池满时回原线程调用
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
