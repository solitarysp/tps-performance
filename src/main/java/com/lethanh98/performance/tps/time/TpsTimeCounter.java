package com.lethanh98.performance.tps.time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lethanh98.performance.tps.Utils;
import com.lethanh98.performance.tps.time.functional.NextTimeCounter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class TpsTimeCounter {
    private final LongAdder totalTime;
    private final LongAdder count;
    private final Timer timer = new Timer();
    @Getter
    private final String name;
    @Setter
    private NextTimeCounter nextTimeCounter;

    /**
     * @param name        Name of TpsTimeCounter
     * @param duration    Time reset Default MILLISECONDS
     * @param nextTimeCounter callback when nextCounter
     */
    public TpsTimeCounter(String name, long duration, NextTimeCounter nextTimeCounter) {
        this(name, duration, TimeUnit.MILLISECONDS);
        this.nextTimeCounter = nextTimeCounter;
    }

    /**
     * @param name        Name of TpsTimeCounter
     * @param duration    Time reset
     * @param timeUnit    Type time duration
     * @param nextTimeCounter callback when nextCounter
     */
    public TpsTimeCounter(String name, long duration, TimeUnit timeUnit, NextTimeCounter nextTimeCounter) {
        this(name, duration, timeUnit);
        this.nextTimeCounter = nextTimeCounter;
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
        totalTime = new LongAdder();
        count = new LongAdder();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (nextTimeCounter != null) {
                    nextTimeCounter.onNext(name, totalTime.longValue(),count.longValue());
                }
                log.debug("reset {}", name);
                totalTime.reset();
                count.reset();
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
        totalTime.add(x);
        count.increment();
    }
    /**
     * Get cur tps
     */
    public long getTotalTime() {
        return totalTime.longValue();
    }

    public long getTotalCount() {
        return count.longValue();
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
