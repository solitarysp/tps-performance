package com.lethanh98.tps.time;

import com.lethanh98.performance.tps.time.TpsTimeCounter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainTestGroup {
    @Test
    public void testTps() {
        TpsTimeCounter tpsCounter = new TpsTimeCounter("Tps/5s", 5, TimeUnit.SECONDS,(name, counterTime,count) -> {
            log.info("{} : {}",counterTime,count);
        });
        int number = new Random().nextInt(20);
        log.info("Number: {}", number);
        for (int i = 0; i < number; i++) {
            tpsCounter.addTps(1000);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
