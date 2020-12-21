package com.lethanh98.tps;

import com.lethanh98.performance.tps.GroupTpsCounter;
import com.lethanh98.performance.tps.TpsCounter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainTest {
    @Test
    public void testTps() {
        new Thread(() -> {
            TpsCounter tpsCounter = new TpsCounter("Tps/5s", 5, TimeUnit.SECONDS);

            GroupTpsCounter tpsCounters = new GroupTpsCounter("TPS MainTest",(name, counter) -> {
                log.info("New Start {} , ola counter {}", name, counter);
            }, tpsCounter, new TpsCounter("Tps/10s", 10, TimeUnit.SECONDS));

            while (true) {
                int number = new Random().nextInt(5000);
                for (int i = 0; i < number; i++) {
                    tpsCounters.addTps();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("TPS: " + tpsCounters.toStringCounter());
            }
        }).start();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
