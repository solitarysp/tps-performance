package com.lethanh98.performance.tps.springboot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "app.tps.enabled", havingValue = "true")
@ComponentScan({"com.lethanh98.performance.tps.springboot.config","com.lethanh98.performance.tps.springboot.config.config"})
public class PerformanceTpsConfig {

}
