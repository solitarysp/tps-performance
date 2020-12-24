package com.lethanh98.performance.tps.time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lethanh98.performance.tps.Utils;
import com.lethanh98.performance.tps.functional.NextCounter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class TpsTimeCounter {
    private final LongAdder tps;
    private final Timer timer = new Timer();
    @Getter
    private final String name;
    @Setter
    private NextCounter nextCounter;

    /**
     * @param name        Name of TpsTimeCounter
     * @param duration    Time reset Default MILLISECONDS
     * @param nextCounter callback when nextCounter
     */
    public TpsTimeCounter(String name, long duration, NextCounter nextCounter) {
        this(name, duration, TimeUnit.MILLISECONDS);
        this.nextCounter = nextCounter;
    }

    /**
     * @param name        Name of TpsTimeCounter
     * @param duration    Time reset
     * @param timeUnit    Type time duration
     * @param nextCounter callback when nextCounter
     */
    public TpsTimeCounter(String name, long duration, TimeUnit timeUnit, NextCounter nextCounter) {
        this(name, duration, timeUnit);
        this.nextCounter = nextCounter;
    }

    /**
     * @param name     Name of TpsTimeCounter
     * @param duration Time reset Default MILLISECONDS
     */
    public TpsTimeCounter(String name, long duration) {
        this(name, duration, TimeUnit.MILLISECONDS);
    }

    /**
     * @param name     Name of TpsTimeCounter
     * @param duration Time reset
     * @param timeUnit Type time duration
     */
    public TpsTimeCounter(String name, long duration, TimeUnit timeUnit) {
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

    /**
     * Add increment tps
     */
    public void addTps(long x) {
        this.add(x);
    }

    /**
     * Add increment tps
     */
    public void add(long x) {
        tps.add(x);
    }
    /**
     * Get cur tps
     */
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
