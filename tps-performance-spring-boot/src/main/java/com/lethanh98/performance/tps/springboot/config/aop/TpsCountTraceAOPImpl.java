package com.lethanh98.performance.tps.springboot.config.aop;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsCountTraceAspect;
import com.lethanh98.performance.tps.springboot.config.config.TpsCountService;
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
public class TpsCountTraceAOPImpl {
    @Autowired
    TpsCountService tpsCountService;

    @SuppressWarnings("java:S1186")
    @Pointcut("@annotation(tpsCountTraceAspect)")
    public void pointcutAnnotation(TpsCountTraceAspect tpsCountTraceAspect) {
    }

    @Around("pointcutAnnotation(tpsCountTraceAspect)")
    public Object aroundProcessAnnotation(ProceedingJoinPoint joinPoint, TpsCountTraceAspect tpsCountTraceAspect) throws Throwable {
        try {
            tpsCountService.addTps(tpsCountTraceAspect);
        } catch (Exception e) {

        }
        return joinPoint.proceed();
    }


}
