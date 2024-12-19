package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.*;

public class Day19 extends Day {

    private List<String> towelPatterns;
    private List<String> designs;

    public Object part1(List<String> input) {
        parseInput(input);
        long result = 0;
        for (String design : designs) {
            if (getNumberOfPossibleWays(design) > 0) {
                result++;
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);
        long result = 0;
        for (String design : designs) {
            result += getNumberOfPossibleWays(design);
        }
        return result;
    }

    Map<String, Long> cache = new HashMap<>();
    private long getNumberOfPossibleWays(String design) {
        if (design.isEmpty()) {
            return 1;
        }
        if (cache.containsKey(design)) {
            return cache.get(design);
        }
        long result = 0;
        for (String towelPattern : towelPatterns) {
            if (design.startsWith(towelPattern)) {
                result += getNumberOfPossibleWays(design.substring(towelPattern.length()));
            }
        }
        cache.put(design, result);
        return result;
    }

    private void parseInput(List<String> input) {
        towelPatterns = Util.parseToList(input.getFirst(), ", ");
        designs = input.subList(2, input.size());
    }

}
