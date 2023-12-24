package de.beachboys.aoc2015;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Consumer;

public class Day06 extends Day {

    public Object part1(List<String> input) {
        Set<Tuple2<Integer, Integer>> switchedOnLights = new HashSet<>();
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
        Map<Tuple2<Integer, Integer>, Integer> brightness = new HashMap<>();
        for (String instruction : input) {
            performActionForRangeIfPrefixMatches(instruction, "turn on ", p -> brightness.put(p, brightness.getOrDefault(p, 0) + 1));
            performActionForRangeIfPrefixMatches(instruction, "turn off ", p -> brightness.put(p, Math.max(0, brightness.getOrDefault(p, 0) - 1)));
            performActionForRangeIfPrefixMatches(instruction, "toggle ", p -> brightness.put(p, brightness.getOrDefault(p, 0) + 2));
        }
        return brightness.values().stream().mapToLong(Integer::longValue).sum();
    }

    private void performActionForRangeIfPrefixMatches(String instruction, String prefix, Consumer<Tuple2<Integer, Integer>> action) {
        if (instruction.startsWith(prefix)) {
            Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> minMax = getMinMax(instruction.substring(prefix.length()));
            Tuple2<Integer, Integer> min = minMax.v1;
            Tuple2<Integer, Integer> max = minMax.v2;
            for (int x = min.v1; x <= max.v1; x++) {
                for (int y = min.v2; y <= max.v2; y++) {
                    Tuple2<Integer, Integer> pair = Tuple.tuple(x, y);
                    action.accept(pair);
                }
            }
        }
    }

    private Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> getMinMax(String minMaxString) {
        String[] minAndMax = minMaxString.split(" through ");
        return Tuple.tuple(parsePairString(minAndMax[0]), parsePairString(minAndMax[1]));
    }

    private Tuple2<Integer, Integer> parsePairString(String string) {
        String[] splitString = string.split(",");
        return Tuple.tuple(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
    }

}
