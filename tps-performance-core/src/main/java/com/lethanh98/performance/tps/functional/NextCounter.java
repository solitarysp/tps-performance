package com.lethanh98.performance.tps.functional;

@FunctionalInterface
public interface NextCounter {
    void onNext(String name, long counter);
}
