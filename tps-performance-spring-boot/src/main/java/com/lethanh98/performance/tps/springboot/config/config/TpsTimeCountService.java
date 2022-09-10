package com.lethanh98.performance.tps.springboot.config.config;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTimeCountTraceAspect;
import com.lethanh98.performance.tps.springboot.config.config.properties.TpsTimeCountConfigProperties;
import com.lethanh98.performance.tps.time.GroupTpsTimeCounter;
import com.lethanh98.performance.tps.time.TpsTimeCounter;
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
public class TpsTimeCountService {

  @Autowired
  TpsTimeCountConfigProperties tpsTimeCountConfigProperties;
  @Getter
  Map<String, TpsTimeCounter> singleton = new ConcurrentHashMap<>();
  @Getter
  Map<String, GroupTpsTimeCounter> multiple = new ConcurrentHashMap<>();

  @Async("threadPoolAddTpsCount")
  public void addTps(TpsTimeCountTraceAspect tpsTimeCountTraceAspect, long time) {
    try {
      if (!tpsTimeCountTraceAspect.isMultiple()) {
        TpsTimeCounter tpsCounter = getSingleton().get(tpsTimeCountTraceAspect.name());
        if (Objects.nonNull(tpsCounter)) {
          tpsCounter.addTps(time);
        } else {
          log.warn("Tps time count not found {}", tpsTimeCountTraceAspect.name());
        }
      } else {
        GroupTpsTimeCounter groupTpsCounter = getMultiple().get(tpsTimeCountTraceAspect.name());
        if (Objects.nonNull(groupTpsCounter)) {
          groupTpsCounter.addTps(time);
        } else {
          log.warn("Tps time count not found group {}", tpsTimeCountTraceAspect.name());
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Autowired
  private void initSingleton(TpsTimeCountConfigProperties tpsTimeCountConfigProperties) {
    tpsTimeCountConfigProperties.getSingletonConfig().entrySet().forEach(stringTpsConfigEntry -> {
      TpsTimeCounter tpsCounter = new TpsTimeCounter(
          stringTpsConfigEntry.getKey(),
          stringTpsConfigEntry.getValue().getDuration(),
          stringTpsConfigEntry.getValue().getTimeUnit(), (s, cTime, count) -> {
        TpsTimeCountConfigProperties.TpsConfig tpsConfig = stringTpsConfigEntry.getValue();

        double tps = 0f;
        if (cTime > 0 && count > 0) {
          tps = cTime / (double) count / 1000000000;
        }
        String result = tpsConfig.getMsg().replace("%tps%",
            String.valueOf(tps));
        result = result.replace("%total-time%", String.valueOf(cTime));
        result = result.replace("%total%", String.valueOf(count));
        log.info(result);
      });
      singleton.put(tpsCounter.getName(), tpsCounter);
    });
  }

  @Autowired
  private void initMultiple(TpsTimeCountConfigProperties tpsTimeCountConfigProperties) {
    tpsTimeCountConfigProperties.getMultipleConfig().entrySet().forEach(multipleTpsConfigEntry -> {
      List<TpsTimeCounter> tpsTimeCounters = new ArrayList<>();

      multipleTpsConfigEntry.getValue().getTpsConfigs().forEach(stringTpsConfigEntry -> {
        TpsTimeCounter tpsCounter = new TpsTimeCounter(
            stringTpsConfigEntry.getName(),
            stringTpsConfigEntry.getDuration(),
            stringTpsConfigEntry.getTimeUnit(), (s, cTime, count) -> {

          double tps = 0f;
          if (cTime > 0 && count > 0) {
            tps = cTime / (double) count / 1000000000;
          }
          String result = stringTpsConfigEntry.getMsg().replace("%tps%",
              String.valueOf(tps));
          result = result.replace("%total-time%", String.valueOf(cTime));
          result = result.replace("%total%", String.valueOf(count));
          log.info(result);
        });
        tpsTimeCounters.add(tpsCounter);
      });
      GroupTpsTimeCounter groupTpsCounter = new GroupTpsTimeCounter(multipleTpsConfigEntry.getKey(),
          tpsTimeCounters);
      multiple.put(multipleTpsConfigEntry.getKey(), groupTpsCounter);
    });
  }
}
