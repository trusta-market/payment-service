package com.trustamarket.paymentservice.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "payoutTaskExecutor")
    public ThreadPoolTaskExecutor payoutTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);           // 큐 사이즈 늘리기
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // AbortPolicy 대신
        executor.setThreadNamePrefix("payout-async-");
        executor.initialize();
        return executor;
    }
}
