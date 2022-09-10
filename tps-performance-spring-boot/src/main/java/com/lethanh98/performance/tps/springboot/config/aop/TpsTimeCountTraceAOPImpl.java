package com.lethanh98.performance.tps.springboot.config.aop;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTimeCountTraceAspect;
import com.lethanh98.performance.tps.springboot.config.config.TpsTimeCountService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class TpsTimeCountTraceAOPImpl {
    @Autowired
    TpsTimeCountService tpsTimeCountService;

    @SuppressWarnings("java:S1186")
    @Pointcut("@annotation(tpsTimeCountTraceAspect)")
    public void pointcutAnnotation(TpsTimeCountTraceAspect tpsTimeCountTraceAspect) {
    }

    @Around("pointcutAnnotation(tpsTimeCountTraceAspect)")
    public Object aroundProcessAnnotation(ProceedingJoinPoint joinPoint, TpsTimeCountTraceAspect tpsTimeCountTraceAspect) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } finally {
            tpsTimeCountService.addTps(tpsTimeCountTraceAspect, System.currentTimeMillis() - start);
        }
        return result;
    }


}
