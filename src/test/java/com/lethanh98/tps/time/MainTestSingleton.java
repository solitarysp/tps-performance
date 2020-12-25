package com.lethanh98.tps.time;

import com.lethanh98.performance.tps.time.GroupTpsTimeCounter;
import com.lethanh98.performance.tps.time.TpsTimeCounter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainTestSingleton {
    @Test
    public void testTps() throws InterruptedException {
        TpsTimeCounter tpsCounter = new TpsTimeCounter("Tps/5s", 5, TimeUnit.SECONDS);
        TpsTimeCounter tpsCounter2 = new TpsTimeCounter("Tps/10s", 10, TimeUnit.SECONDS);
        GroupTpsTimeCounter tpsCounters = new GroupTpsTimeCounter("TPS MainTestGroup", (name, counterTime, count) -> {
            log.info("New Start {} , ola counter time {} and count {}", name, counterTime, count);
        }, tpsCounter, tpsCounter2);

        while (true) {
            int number = new Random().nextInt(20);
            log.info("Number: {}", number);
            for (int i = 0; i < 5; i++) {
                tpsCounters.addTps(1000);
            }
            Thread.sleep(5000);
        }

    }
}
