package com.lethanh98.performance.tps.springboot.config.aop;

import com.lethanh98.performance.tps.springboot.config.aop.annotation.TpsTraceAspect;
import com.lethanh98.performance.tps.springboot.config.config.TpsService;
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
public class TpsTraceAOPImpl {
    @Autowired
    TpsService tpsService;

    @SuppressWarnings("java:S1186")
    @Pointcut("@annotation(tpsTraceAspect)")
    public void pointcutAnnotation(TpsTraceAspect tpsTraceAspect) {
    }

    @Around("pointcutAnnotation(tpsTraceAspect)")
    public Object aroundProcessAnnotation(ProceedingJoinPoint joinPoint, TpsTraceAspect tpsTraceAspect) throws Throwable {
        try {
            tpsService.addTps(tpsTraceAspect);
        } catch (Exception e) {

        }
        return joinPoint.proceed();
    }


}
