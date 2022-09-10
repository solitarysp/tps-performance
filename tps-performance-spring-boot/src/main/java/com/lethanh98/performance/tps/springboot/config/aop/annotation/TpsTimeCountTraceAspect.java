package com.lethanh98.performance.tps.springboot.config.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TpsTimeCountTraceAspect {
    boolean enabled() default true;

    boolean isMultiple() default false;

    String name();
}
