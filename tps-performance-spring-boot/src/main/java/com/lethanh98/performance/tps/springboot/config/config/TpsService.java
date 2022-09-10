package com.lethanh98.performance.tps.springboot.config.config;

import com.lethanh98.performance.tps.GroupTpsCounter;
import com.lethanh98.performance.tps.TpsCounter;
import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTraceAspect;
import com.lethanh98.performance.tps.springboot.config.config.properties.TpsConfigProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;

@Configuration
@Slf4j
public class TpsService {
    @Autowired
    TpsConfigProperties tpsConfigProperties;
    @Getter
    Map<String, TpsCounter> singleton = new ConcurrentHashMap<>();
    @Getter
    Map<String, GroupTpsCounter> multiple = new ConcurrentHashMap<>();

    @Async("threadPoolAddTpsCount")
    public void addTps(TpsTraceAspect tpsTraceAspect) {
        try {
            if (!tpsTraceAspect.isMultiple()) {
                TpsCounter tpsCounter = getSingleton().get(tpsTraceAspect.name());
                if (Objects.nonNull(tpsCounter)) {
                    tpsCounter.addTps();
                } else {
                    log.warn("Tps not found {}", tpsTraceAspect.name());
                }
            } else {
                GroupTpsCounter groupTpsCounter = getMultiple().get(tpsTraceAspect.name());
                if(Objects.nonNull(groupTpsCounter)){
                    groupTpsCounter.addTps();
                }else {
                    log.warn("Tps not found group {}", tpsTraceAspect.name());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Autowired
    private void initSingleton() {
        if (Objects.nonNull(tpsConfigProperties)) {
            tpsConfigProperties.getSingletonConfig().entrySet().forEach(stringTpsConfigEntry -> {
                TpsCounter tpsCounter = new TpsCounter(
                        stringTpsConfigEntry.getKey(),
                        stringTpsConfigEntry.getValue().getDuration(),
                        stringTpsConfigEntry.getValue().getTimeUnit(), (s, l) -> {
                    long seconds = stringTpsConfigEntry.getValue().getTimeUnit().toSeconds(stringTpsConfigEntry.getValue().getDuration());
                    long tps = l / seconds;
                    log.info(stringTpsConfigEntry.getValue().getMsg().replace("%tps%", String.valueOf(tps > 0 ? tps : 0)).replace("%total%", String.valueOf(l)));
                });
                singleton.put(tpsCounter.getName(), tpsCounter);
            });
        }
    }

    @Autowired
    private void initMultiple() {
        if (Objects.nonNull(tpsConfigProperties)) {
            tpsConfigProperties.getMultipleConfig().entrySet().forEach(multipleTpsConfigEntry -> {
                List<TpsCounter> tpsCounters = new ArrayList<>();

                multipleTpsConfigEntry.getValue().getTpsConfigs().forEach(stringTpsConfigEntry -> {
                    TpsCounter tpsCounter = new TpsCounter(
                            stringTpsConfigEntry.getName(),
                            stringTpsConfigEntry.getDuration(),
                            stringTpsConfigEntry.getTimeUnit(), (s, l) -> {
                        long seconds = stringTpsConfigEntry.getTimeUnit().toSeconds(stringTpsConfigEntry.getDuration());
                        long tps = l / seconds;
                        log.info(stringTpsConfigEntry.getMsg().replace("%tps%", String.valueOf(tps > 0 ? tps : 0)).replace("%total%", String.valueOf(l)));
                    });
                    tpsCounters.add(tpsCounter);
                });
                GroupTpsCounter groupTpsCounter = new GroupTpsCounter(multipleTpsConfigEntry.getKey(), tpsCounters);
                multiple.put(multipleTpsConfigEntry.getKey(), groupTpsCounter);
            });
        }
    }
}
