package com.lethanh98.performance.tps.springboot.config.config;

import com.lethanh98.performance.tps.springboot.config.config.properties.TpsCountConfigProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@Slf4j
@Getter
public class TpsAsyncConfig {
    @Autowired
    TpsCountConfigProperties tpsCountConfigProperties;

    @Bean("threadPoolAddTpsCount")
    public TaskExecutor threadPoolAddTps() {
        log.info(tpsCountConfigProperties.getAddTpsThread().getThreadNamePrefix() + ": corePoolSize : " + tpsCountConfigProperties.getAddTpsThread().getCorePoolSize() + ", MaxPoolSize : " + tpsCountConfigProperties.getAddTpsThread().getMaxPoolSize());
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(tpsCountConfigProperties.getAddTpsThread().getCorePoolSize());
        executor.setMaxPoolSize(tpsCountConfigProperties.getAddTpsThread().getMaxPoolSize());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix(tpsCountConfigProperties.getAddTpsThread().getThreadNamePrefix());
        return executor;
    }
}
