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

    public GroupTpsCounter(String groupName, List<TpsCounter> tpsCounters, NextCounter nextCounter) {
        this(groupName, tpsCounters);
        addNextCounter(nextCounter);
    }

    public GroupTpsCounter(String groupName, NextCounter nextCounter, TpsCounter... tpsCounters) {
        this(groupName, Arrays.asList(tpsCounters));
        addNextCounter(nextCounter);
    }

    public GroupTpsCounter(String groupName, TpsCounter... tpsCounters) {
        this(groupName, Arrays.asList(tpsCounters));
    }

    public GroupTpsCounter(String groupName, List<TpsCounter> tpsCounters) {
        counterList.addAll(tpsCounters);
        this.groupName = groupName;
    }

    private void addNextCounter(NextCounter next) {
        counterList.forEach(tpsCounter -> tpsCounter.setNextCounter(next));
    }

    public void addTps() {
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
