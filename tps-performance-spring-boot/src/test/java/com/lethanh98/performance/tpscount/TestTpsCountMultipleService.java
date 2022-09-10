package com.lethanh98.performance.tpscount;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsCountTraceAspect;
import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestTpsCountMultipleService {
    @TpsCountTraceAspect(name = "countmultiple", isMultiple = true)
    public void test() {
        log.info("router ++");
    }
}
