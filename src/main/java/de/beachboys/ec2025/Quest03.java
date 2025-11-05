package de.beachboys.ec2025;

import de.beachboys.Quest;
import de.beachboys.Util;

import java.util.*;

public class Quest03 extends Quest {

    @Override
    public Object part1(List<String> input) {
        Set<Integer> set = new HashSet<>(Util.parseIntCsv(input.getFirst()));
        return set.stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public Object part2(List<String> input) {
        List<Integer> reducedList = new ArrayList<>(new HashSet<>(Util.parseIntCsv(input.getFirst())));
        reducedList.sort(Integer::compareTo);
        return reducedList.subList(0, 20).stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public Object part3(List<String> input) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Integer i : Util.parseIntCsv(input.getFirst())) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
        }
        return counts.values().stream().mapToInt(Integer::intValue).max().orElseThrow();
    }
}
