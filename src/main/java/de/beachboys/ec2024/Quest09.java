package de.beachboys.ec2024;

import de.beachboys.Quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quest09 extends Quest {

    private static final List<Integer> STAMPS_PART1 = List.of(1, 3, 5, 10);
    private static final List<Integer> STAMPS_PART2 = List.of(1, 3, 5, 10, 15, 16, 20, 24, 25, 30);
    private static final List<Integer> STAMPS_PART3 = List.of(1, 3, 5, 10, 15, 16, 20, 24, 25, 30, 37, 38, 49, 50, 74, 75, 100, 101);
    private final Map<Integer, Integer> cache = new HashMap<>();
    private List<Integer> availableStamps;

    @Override
    public Object part1(List<String> input) {
        availableStamps = STAMPS_PART1;
        return runLogicPart1And2(input);
    }

    @Override
    public Object part2(List<String> input) {
        availableStamps = STAMPS_PART2;
        return runLogicPart1And2(input);
    }

    @Override
    public Object part3(List<String> input) {
        availableStamps = STAMPS_PART3;
        List<Integer> brightnesses = parseInput(input);
        fillCache(brightnesses.stream().max(Integer::compareTo).orElseThrow() / 2 + 50);

        long totalBeetles = 0;
        for (int brightness : brightnesses) {
            int beetles = Integer.MAX_VALUE;
            for (int sphere = (brightness + 1) / 2 - 50; sphere <= brightness / 2; sphere++) {
                beetles = Math.min(beetles, getMinimumBeetlesForBrightness(sphere) + getMinimumBeetlesForBrightness(brightness - sphere));
            }
            totalBeetles += beetles;
        }
        return totalBeetles;
    }

    private long runLogicPart1And2(List<String> input) {
        List<Integer> brightnesses = parseInput(input);
        fillCache(brightnesses.stream().max(Integer::compareTo).orElseThrow());
        long totalBeetles = 0;
        for (int brightness : brightnesses) {
            totalBeetles += getMinimumBeetlesForBrightness(brightness);
        }
        return totalBeetles;
    }

    private List<Integer> parseInput(List<String> input) {
        List<Integer> brightnesses = new ArrayList<>(input.size()) ;
        for (String line : input) {
            brightnesses.add(Integer.parseInt(line));
        }
        return brightnesses;
    }

    private void fillCache(int maxValueToFill) {
        cache.clear();
        for (int i = 1; i <= maxValueToFill; i++) {
            getMinimumBeetlesForBrightness(i);
        }
    }

    private int getMinimumBeetlesForBrightness(int brightness) {
        if (brightness == 0) {
            return 0;
        }
        if (cache.containsKey(brightness)) {
            return cache.get(brightness);
        }

        int minimumBeetles = Integer.MAX_VALUE;
        for (int stamp : availableStamps) {
            if (brightness >= stamp) {
                minimumBeetles = Math.min(minimumBeetles, 1 + getMinimumBeetlesForBrightness(brightness - stamp));
            }
        }
        cache.put(brightness, minimumBeetles);
        return minimumBeetles;
     }
}
