package com.lethanh98.performance.time;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTimeCountTraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestTpsTimeCountSingleton {
    @TpsTimeCountTraceAspect(name = "countsingleton", isMultiple = false)
    public void test() throws InterruptedException {
        Thread.sleep(2000);
        log.info("router ++");
    }
}
