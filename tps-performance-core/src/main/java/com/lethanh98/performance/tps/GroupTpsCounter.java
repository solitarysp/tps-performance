package com.lethanh98.performance.tps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lethanh98.performance.tps.functional.NextCounter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GroupTpsCounter {
    @Getter
    private final List<TpsCounter> counterList = new ArrayList<>();
    @Getter
    private String groupName;

    /**
     * @param groupName   name group
     * @param tpsCounters list tpsCounters
     * @param nextCounter callback for nextCounter
     */
    public GroupTpsCounter(String groupName, List<TpsCounter> tpsCounters, NextCounter nextCounter) {
        this(groupName, tpsCounters);
        addNextCounter(nextCounter);
    }

    /**
     * @param groupName   name group
     * @param nextCounter callback for nextCounter
     * @param tpsCounters list tpsCounters
     */
    public GroupTpsCounter(String groupName, NextCounter nextCounter, TpsCounter... tpsCounters) {
        this(groupName, Arrays.asList(tpsCounters));
        addNextCounter(nextCounter);
    }

    /**
     * @param groupName   name group
     * @param tpsCounters list tpsCounters
     */
    public GroupTpsCounter(String groupName, TpsCounter... tpsCounters) {
        this(groupName, Arrays.asList(tpsCounters));
    }

    /**
     * @param groupName   name group
     * @param tpsCounters list tpsCounters
     */
    public GroupTpsCounter(String groupName, List<TpsCounter> tpsCounters) {
        counterList.addAll(tpsCounters);
        this.groupName = groupName;
    }

    /**
     * Add callback for all counter
     *
     * @param next
     */
    private void addNextCounter(NextCounter next) {
        counterList.forEach(tpsCounter -> tpsCounter.addNextCounter(next));
    }

    /**
     * Add tps for list counter
     */
    public void addTps() {
        this.increment();
    }

    public void increment() {
        counterList.forEach(TpsCounter::addTps);

    }

    public String toStringCounter() {
        try {
            return Utils.OBJECT_MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return "{}";
    }
}
