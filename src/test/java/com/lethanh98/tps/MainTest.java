package com.lethanh98.tps;

import com.lethanh98.performance.tps.GroupTpsCounter;
import com.lethanh98.performance.tps.TpsCounter;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainTest {
    @Test
    public void testTps() {
        new Thread(() -> {
            GroupTpsCounter tpsCounter = new GroupTpsCounter("TPS MainTest", new TpsCounter("Tps/5s", 5, TimeUnit.SECONDS), new TpsCounter("Tps/10s", 10, TimeUnit.SECONDS));
            while (true) {
                int number = new Random().nextInt(5000);
                for (int i = 0; i < number; i++) {
                    tpsCounter.addTps();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("TPS: " + tpsCounter.toStringCounter());
            }
        }).start();
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
