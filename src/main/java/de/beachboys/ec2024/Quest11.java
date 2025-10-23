package de.beachboys.ec2024;

import de.beachboys.Quest;

import java.util.*;
import java.util.regex.Pattern;

public class Quest11 extends Quest {

    private final Map<String, List<String>> conversions = new HashMap<>();

    @Override
    public Object part1(List<String> input) {
        parseInput(input);
        return getPopulationSize("A", 4);
    }

    @Override
    public Object part2(List<String> input) {
        parseInput(input);
        return getPopulationSize("Z", 10);

    }

    @Override
    public Object part3(List<String> input) {
        parseInput(input);

        long min = Long.MAX_VALUE;
        long max = 0;
        for (String type : conversions.keySet()) {
            long size = getPopulationSize(type, 20);
            min = Math.min(min, size);
            max = Math.max(max, size);
        }
        return max - min;
    }

    private void parseInput(List<String> input) {
        conversions.clear();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(":"));
            conversions.put(split[0], Arrays.stream(split[1].split(Pattern.quote(","))).toList());
        }
    }

    private long getPopulationSize(String initialTermiteType, int days) {
        Map<String, Long> currentPopulation = new HashMap<>();
        currentPopulation.put(initialTermiteType, 1L);
        for (int i = 0; i < days; i++) {
            Map<String, Long> newPopulation = new HashMap<>();
            for (String type : currentPopulation.keySet()) {
                for (String newType : conversions.get(type)) {
                    newPopulation.put(newType, newPopulation.getOrDefault(newType, 0L) + currentPopulation.get(type));
                }
            }
            currentPopulation = newPopulation;
        }
        return currentPopulation.values().stream().reduce(Long::sum).orElseThrow();
    }
}
