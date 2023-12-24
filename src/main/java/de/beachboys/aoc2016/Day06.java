package de.beachboys.aoc2016;

import de.beachboys.Day;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, map -> map.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey());
    }

    public Object part2(List<String> input) {
        return runLogic(input, map -> map.entrySet().stream().min(Map.Entry.comparingByValue()).orElseThrow().getKey());
    }

    private String runLogic(List<String> input, Function<Map<Character, Integer>, Character> getCharacterToUse) {
        Map<Integer, Map<Character, Integer>> counter = new TreeMap<>();
        for (int i = 0; i < input.getFirst().length(); i++) {
            counter.put(i, new HashMap<>());
        }
        for (String line : input) {
            for (int i = 0; i < line.length(); i++) {
                counter.get(i).put(line.charAt(i), counter.get(i).getOrDefault(line.charAt(i), 0) + 1);
            }
        }

        return counter.values().stream().map(getCharacterToUse).map(c -> c + "").collect(Collectors.joining());
    }

}
