package com.lethanh98.tps;

import com.lethanh98.performance.tps.TpsCounter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class mainTestSingleton {
    public static void main(String[] args) {
        new Thread(() -> {
            TpsCounter tpsCounter = new TpsCounter("Tps/5s", 5, TimeUnit.SECONDS);
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
                log.info("TPS: {}", tpsCounter.toString());
            }
        }).start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
