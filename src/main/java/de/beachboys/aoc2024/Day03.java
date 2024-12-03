package de.beachboys.aoc2024;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Day {

    public static final Pattern INPUT_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public Object part1(List<String> input) {
        return runLogic(String.join("", input), List.of(Tuple.tuple(0, Integer.MAX_VALUE)));
    }

    public Object part2(List<String> input) {
        String wholeInput = String.join("", input);

        List<Tuple2<Integer, Integer>> enabledRegions = new ArrayList<>();
        int start = 0;
        while (start != -1) {
            int end = wholeInput.indexOf("don't()", start);
            if (end != -1) {
                enabledRegions.add(Tuple.tuple(start, end));
                start = wholeInput.indexOf("do()", end);
            } else {
                enabledRegions.add(Tuple.tuple(start, Integer.MAX_VALUE));
                start = -1;
            }
        }

        return runLogic(wholeInput, enabledRegions);
    }

    private static long runLogic(String wholeInput, List<Tuple2<Integer, Integer>> enabledRegions) {
        long result = 0;
        Matcher m = INPUT_PATTERN.matcher(wholeInput);
        for (MatchResult matchResult : m.results().toList()) {
            int first = Integer.parseInt(matchResult.group(1));
            int second = Integer.parseInt(matchResult.group(2));
            for (Tuple2<Integer, Integer> region : enabledRegions) {
                if (region.v1 <= matchResult.start() && region.v2 >= matchResult.end()) {
                    result += (long) first * second;
                    break;
                }
            }
        }
        return result;
    }

}
