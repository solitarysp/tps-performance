package com.lethanh98.performance.tps.springboot.config.config.properties;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.tps.time.counter")
@Configuration
@Data
public class TpsTimeCountConfigProperties {
    ThreadTaskConfig addTpsThread = new ThreadTaskConfig();
    Map<String, TpsConfig> singletonConfig = new Hashtable<>();
    Map<String, MultipleTpsConfig> multipleConfig = new Hashtable<>();

    @Data
    public static class TpsConfig {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        private long duration = 10000;
        /**
         * %tps% = total transaction
         */
        private String msg = "%tps%";
        private String name;
    }

    @Data
    public static class MultipleTpsConfig {
        List<TpsConfig> tpsConfigs = new ArrayList<>();
    }

    @Data
    public static class ThreadTaskConfig {
        int corePoolSize = 10;
        int maxPoolSize = 15;
        String threadNamePrefix = "tps-time-count";
    }
}
