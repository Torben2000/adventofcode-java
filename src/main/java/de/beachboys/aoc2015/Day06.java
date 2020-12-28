package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        Set<Pair<Integer, Integer>> switchedOnLights = new HashSet<>();
        for (String instruction : input) {
            performActionForRangeIfPrefixMatches(instruction, "turn on ", switchedOnLights::add);
            performActionForRangeIfPrefixMatches(instruction, "turn off ", switchedOnLights::remove);
            performActionForRangeIfPrefixMatches(instruction, "toggle ", p -> {
                if (!switchedOnLights.remove(p)) {
                    switchedOnLights.add(p);
                }
            });
        }
        return switchedOnLights.size();
    }

    public Object part2(List<String> input) {
        Map<Pair<Integer, Integer>, Integer> brightness = new HashMap<>();
        for (String instruction : input) {
            performActionForRangeIfPrefixMatches(instruction, "turn on ", p -> brightness.put(p, brightness.getOrDefault(p, 0) + 1));
            performActionForRangeIfPrefixMatches(instruction, "turn off ", p -> brightness.put(p, Math.max(0, brightness.getOrDefault(p, 0) - 1)));
            performActionForRangeIfPrefixMatches(instruction, "toggle ", p -> brightness.put(p, brightness.getOrDefault(p, 0) + 2));
        }
        return brightness.values().stream().mapToLong(Integer::longValue).sum();
    }

    private void performActionForRangeIfPrefixMatches(String instruction, String prefix, Consumer<Pair<Integer, Integer>> action) {
        if (instruction.startsWith(prefix)) {
            Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> minMax = getMinMax(instruction.substring(prefix.length()));
            Pair<Integer, Integer> min = minMax.getValue0();
            Pair<Integer, Integer> max = minMax.getValue1();
            for (int x = min.getValue0(); x <= max.getValue0(); x++) {
                for (int y = min.getValue1(); y <= max.getValue1(); y++) {
                    Pair<Integer, Integer> pair = Pair.with(x, y);
                    action.accept(pair);
                }
            }
        }
    }

    private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getMinMax(String minMaxString) {
        String[] minAndMax = minMaxString.split(" through ");
        return Pair.with(parsePairString(minAndMax[0]), parsePairString(minAndMax[1]));
    }

    private Pair<Integer, Integer> parsePairString(String string) {
        String[] splitString = string.split(",");
        return Pair.with(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
    }

}
