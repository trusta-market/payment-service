package com.trustamarket.paymentservice.paymentservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "payoutTaskExecutor")
    public Executor payoutTaskExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
