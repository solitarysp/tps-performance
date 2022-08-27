package com.lethanh98.performance.tps.time.functional;

@FunctionalInterface
public interface NextTimeCounter {
    void onNext(String name, long timeCounter,long count);
}
