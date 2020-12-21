package com.lethanh98.performance.tps;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class TpsCounter {
    @Getter
    private final LongAdder tps;
    private final Timer timer = new Timer();
    @Getter
    private final String name;

    public TpsCounter(String name, long duration) {
        this(name, duration, TimeUnit.MILLISECONDS);
    }

    public TpsCounter(String name, long duration, TimeUnit timeUnit) {
        this.name = name;
        tps = new LongAdder();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
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
