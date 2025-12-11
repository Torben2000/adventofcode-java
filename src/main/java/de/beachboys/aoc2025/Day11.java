package de.beachboys.aoc2025;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Pattern;

public class Day11 extends Day {

    private final Map<String, Set<String>> map = new HashMap<>();
    private final Map<Tuple3<String, Boolean, Boolean>, Long> cache = new HashMap<>();

    public Object part1(List<String> input) {
        parseInput(input);
        return getNumPathsToOutPart1("you");
    }

    public Object part2(List<String> input) {
        parseInput(input);
        cache.clear();
        return getNumPathsToOutPart2("svr", false, false);
    }

    private long getNumPathsToOutPart1(String device) {
        if ("out".equals(device)) {
            return 1;
        } else {
            long result = 0;
            for (String nextDevice : map.get(device)) {
                result += getNumPathsToOutPart1(nextDevice);
            }
            return result;
        }
    }

    private long getNumPathsToOutPart2(String device, boolean seenDac, boolean seenFft) {
        if ("out".equals(device)) {
            if (seenFft && seenDac) {
                return 1;
            } else {
                return 0;
            }
        }

        Tuple3<String, Boolean, Boolean> cacheKey = Tuple.tuple(device, seenDac, seenFft);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        long result = 0;
        if (map.containsKey(device)) {
            boolean newSeenFft = seenFft || "fft".equals(device);
            boolean newSeenDac = seenDac || "dac".equals(device);
            for (String nextDevice : map.get(device)) {
                result += getNumPathsToOutPart2(nextDevice, newSeenDac, newSeenFft);
            }
        }

        cache.put(cacheKey, result);
        return result;
    }

    private void parseInput(List<String> input) {
        map.clear();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(" "));
            Set<String> target = new HashSet<>(Arrays.asList(split).subList(1, split.length));
            map.put(split[0].substring(0, split[0].length() - 1), target);
        }
    }

}
