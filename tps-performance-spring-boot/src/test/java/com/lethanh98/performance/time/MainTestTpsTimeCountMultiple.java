package com.lethanh98.performance.time;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@TestPropertySource(properties = {"application.properties"})
public class MainTestTpsTimeCountMultiple {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    TestTpsTimeCountMultiple testTpsMultipleService;

    @Test
    public void testTpsMultiple() throws InterruptedException {
        new Thread(() -> {
            while (true) {
                try {
                    testTpsMultipleService.test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(10000);
    }
}
