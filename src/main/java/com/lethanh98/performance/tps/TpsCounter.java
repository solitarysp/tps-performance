package com.lethanh98.performance.tps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lethanh98.performance.tps.functional.NextCounter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class TpsCounter {
    private final LongAdder tps;
    private final Timer timer = new Timer();
    @Getter
    private final String name;
    @Setter
    private NextCounter nextCounter;


    public TpsCounter(String name, long duration, NextCounter nextCounter) {
        this(name, duration, TimeUnit.MILLISECONDS);
        this.nextCounter = nextCounter;
    }

    public TpsCounter(String name, long duration, TimeUnit timeUnit, NextCounter nextCounter) {
        this(name, duration, timeUnit);
        this.nextCounter = nextCounter;
    }

    public TpsCounter(String name, long duration) {
        this(name, duration, TimeUnit.MILLISECONDS);
    }

    public TpsCounter(String name, long duration, TimeUnit timeUnit) {
        this.name = name;
        tps = new LongAdder();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (nextCounter != null) {
                    nextCounter.onNext(name, tps.longValue());
                }
                log.debug("reset {}", name);
                tps.reset();
            }
        }, 0, timeUnit.toMillis(duration));
    }

    public void addTps() {
        tps.increment();
    }

    public long getTps() {
        return tps.longValue();
    }

    @Override
    public String toString() {
        try {
            return Utils.OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return "{}";
    }

    @Override
    protected void finalize() throws Throwable {
        log.debug("finalize {}", name);
        timer.cancel();
    }
}
