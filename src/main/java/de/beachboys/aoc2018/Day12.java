package de.beachboys.aoc2018;

import de.beachboys.Day;

import java.util.*;

public class Day12 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 20);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 50000000000L);
    }

    private long runLogic(List<String> input, long numberOfGenerations) {
        Set<Integer> currentState = parseInitialState(input);
        Map<Integer, Boolean> rules = parseRules(input);

        int currentDiff = 0;
        int currentSum = 0;
        int sumStreak = 0;
        for (long i = 0; i < numberOfGenerations; i++) {
            int sum = currentState.stream().mapToInt(Integer::intValue).sum();
            if (sum == currentSum + currentDiff) {
                sumStreak++;
                if (sumStreak > 100) {
                    return sum + currentDiff * (numberOfGenerations - i);
                }
            } else {
                sumStreak = 0;
                currentDiff = sum - currentSum;
            }
            currentSum = sum;
            currentState = getNextGeneration(currentState, rules);
        }
        return currentState.stream().mapToInt(Integer::intValue).sum();
    }

    private Set<Integer> getNextGeneration(Set<Integer> currentState, Map<Integer, Boolean> rules) {
        Set<Integer> newState = new HashSet<>();
        int min = currentState.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int max = currentState.stream().mapToInt(Integer::intValue).max().orElseThrow();
        for (int j = min - 2; j <= max + 2; j++) {
            int value = 0;
            for (int k = j - 2; k <= j + 2; k++) {
                value *= 2;
                if (currentState.contains(k)) {
                    value++;
                }
            }
            if (rules.getOrDefault(value, false)) {
                newState.add(j);
            }
        }
        currentState = newState;
        return currentState;
    }

    private Map<Integer, Boolean> parseRules(List<String> input) {
        Map<Integer, Boolean> rules = new HashMap<>();
        for (String line : input.subList(2, input.size())) {
            int value = 0;
            String[] splitLine = line.split(" => ");
            for (char character : splitLine[0].toCharArray()) {
                value *= 2;
                if ('#' == character) {
                    value++;
                }
            }
            rules.put(value, "#".equals(splitLine[1]));
        }
        return rules;
    }

    private Set<Integer> parseInitialState(List<String> input) {
        Set<Integer> currentState = new HashSet<>();
        char[] initialState = input.get(0).substring("initial state: ".length()).toCharArray();
        for (int i = 0; i < initialState.length; i++) {
            if('#' == initialState[i]) {
                currentState.add(i);
            }
        }
        return currentState;
    }

}
