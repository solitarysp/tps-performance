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

        int number = new Random().nextInt(50)+20;
        log.info("Number: {}", number);

        for (int i = 0; i < number; i++) {
            int time = new Random().nextInt(2000) + 200;
            tpsCounter.addTps(time);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(2000);
    }
}
