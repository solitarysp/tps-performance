package com.lethanh98.performance.tps;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestTpsMultipleService {
    @TpsTraceAspect(name = "router", isMultiple = true)
    public void test() {
        log.info("router ++");
    }
}
