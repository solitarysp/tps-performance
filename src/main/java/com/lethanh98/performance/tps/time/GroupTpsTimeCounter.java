package com.lethanh98.performance.tps.time;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lethanh98.performance.tps.Utils;
import com.lethanh98.performance.tps.functional.NextCounter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class GroupTpsTimeCounter {
    @Getter
    private final List<TpsTimeCounter> counterList = new ArrayList<>();
    @Getter
    private String groupName;

    /**
     * @param groupName       name group
     * @param tpsTimeCounters list tpsTimeCounters
     * @param nextCounter     callback for nextCounter
     */
    public GroupTpsTimeCounter(String groupName, List<TpsTimeCounter> tpsTimeCounters, NextCounter nextCounter) {
        this(groupName, tpsTimeCounters);
        addNextCounter(nextCounter);
    }

    /**
     * @param groupName       name group
     * @param nextCounter     callback for nextCounter
     * @param tpsTimeCounters list tpsTimeCounters
     */
    public GroupTpsTimeCounter(String groupName, NextCounter nextCounter, TpsTimeCounter... tpsTimeCounters) {
        this(groupName, Arrays.asList(tpsTimeCounters));
        addNextCounter(nextCounter);
    }

    /**
     * @param groupName       name group
     * @param tpsTimeCounters list tpsTimeCounters
     */
    public GroupTpsTimeCounter(String groupName, TpsTimeCounter... tpsTimeCounters) {
        this(groupName, Arrays.asList(tpsTimeCounters));
    }

    /**
     * @param groupName       name group
     * @param tpsTimeCounters list tpsTimeCounters
     */
    public GroupTpsTimeCounter(String groupName, List<TpsTimeCounter> tpsTimeCounters) {
        counterList.addAll(tpsTimeCounters);
        this.groupName = groupName;
    }

    /**
     * Add callback for all counter
     *
     * @param next
     */
    private void addNextCounter(NextCounter next) {
        counterList.forEach(tpsTimeCounter -> tpsTimeCounter.setNextCounter(next));
    }

    /**
     * Add tps for list counter
     */
    public void addTps(long time) {
        this.add(time);
    }

    public void add(long time) {
        counterList.forEach(tpsTimeCounter -> tpsTimeCounter.add(time));

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
