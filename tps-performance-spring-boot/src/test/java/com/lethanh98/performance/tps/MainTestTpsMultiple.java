package com.lethanh98.performance.tps;

import com.lethanh98.performance.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Main.class)
@TestPropertySource(properties = {"application.properties"})
public class MainTestTpsMultiple {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    TestTpsMultipleService testTpsMultipleService;
    @Test
    public void testTpsMultiple() throws InterruptedException {
        for (int i = 0; i < 5000; i++) {
            testTpsMultipleService.test();
        }
        Thread.sleep(11000);
    }
}
