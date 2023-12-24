package de.beachboys.aoc2023;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day13 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private long runLogic(List<String> input, boolean withSmudge) {
        long result = 0;

        int firstLineOfCurrentPattern = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (line.isEmpty()) {
                Set<Tuple2<Integer, Integer>> rockSet = Util.buildConwaySet(input.subList(firstLineOfCurrentPattern, i), "#");
                result += findMirror(rockSet, withSmudge);
                firstLineOfCurrentPattern = i + 1;
            }
        }
        Set<Tuple2<Integer, Integer>> lastRockSet = Util.buildConwaySet(input.subList(firstLineOfCurrentPattern, input.size() - 1), "#");
        result += findMirror(lastRockSet, withSmudge);

        return result;
    }

    private long findMirror(Set<Tuple2<Integer, Integer>> set, boolean withSmudge) {
        int maxX = set.stream().map(Tuple2::v1).mapToInt(Integer::intValue).max().orElseThrow();
        int maxY = set.stream().map(Tuple2::v2).mapToInt(Integer::intValue).max().orElseThrow();
        long returnValue = getMirrorLine(set, withSmudge, maxX, Tuple2::v1, Tuple2::v2)
                + 100L * getMirrorLine(set, withSmudge, maxY, Tuple2::v2, Tuple2::v1);
        if (returnValue > 0) {
            return returnValue;
        }
        throw new IllegalArgumentException();
    }

    private static Integer getMirrorLine(Set<Tuple2<Integer, Integer>> set, boolean withSmudge, int maxLineValue, Function<Tuple2<Integer, Integer>, Integer> getLineValue, Function<Tuple2<Integer, Integer>, Integer> getOtherValue) {
        for (int i = 0; i < maxLineValue; i++) {
            int finalI = i;
            boolean match = true;
            boolean smudgeRemoved = false;
            for (int j = 0; j + i < maxLineValue && j <= i; j++) {
                int finalJ = j;
                Set<Integer> left = set.stream().filter(p -> getLineValue.apply(p) == finalI - finalJ).map(getOtherValue).collect(Collectors.toSet());
                Set<Integer> right = set.stream().filter(p -> getLineValue.apply(p) == finalI + 1 + finalJ).map(getOtherValue).collect(Collectors.toSet());
                if (!left.equals(right)) {
                    if (withSmudge && !smudgeRemoved) {
                        if (Math.abs(left.size() - right.size()) == 1) {
                            Set<Integer> commonRocks = new HashSet<>(right);
                            commonRocks.retainAll(left);
                            if (commonRocks.size() == left.size() || commonRocks.size() == right.size()) {
                                smudgeRemoved = true;
                                continue;
                            }
                        }
                    }
                    match = false;
                    break;
                }
            }
            if (match && (!withSmudge || smudgeRemoved)) {
                return i + 1;
            }
        }
        return 0;
    }

}
