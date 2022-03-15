package com.lethanh98.tps.time;

import com.lethanh98.performance.tps.time.TpsTimeCounter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainTestGroup {
    @Test
    public void testTps() throws InterruptedException {
        int number = new Random().nextInt(50) + 20;
        log.info("Number: {}", number);
        TpsTimeCounter tpsCounter = new TpsTimeCounter("Tps/5s", 5, TimeUnit.SECONDS, (name, counterTime, count) -> {
            log.info("{} : {}", counterTime, count);
        });
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
