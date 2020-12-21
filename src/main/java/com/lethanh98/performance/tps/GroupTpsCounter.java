package com.lethanh98.performance.tps;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public GroupTpsCounter(String groupName, List<TpsCounter> tpsCounters) {
        counterList.addAll(tpsCounters);
        this.groupName = groupName;
    }

    public GroupTpsCounter(String groupName, TpsCounter... tpsCounters) {
        this(groupName, Arrays.asList(tpsCounters));
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
