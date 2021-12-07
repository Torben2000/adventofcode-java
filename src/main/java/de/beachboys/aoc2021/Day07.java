package de.beachboys.aoc2021;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;
import java.util.function.LongUnaryOperator;

public class Day07 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, this::incrementCalculatorPart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::incrementCalculatorPart2);
    }

    private long runLogic(List<String> input, LongUnaryOperator incrementCalculator) {
        List<Integer> crabs = Util.parseIntCsv(input.get(0));

        int minPos = crabs.stream().mapToInt(Integer::intValue).min().orElseThrow();
        int maxPos = crabs.stream().mapToInt(Integer::intValue).max().orElseThrow();

        long minSum = Long.MAX_VALUE;
        for (int i = minPos; i <= maxPos; i++) {
            long sum = 0L;
            for (Integer pos : crabs) {
                long diff = Math.abs(i - pos);
                sum += incrementCalculator.applyAsLong(diff);
            }
            minSum = Math.min(minSum, sum);
        }

        return minSum;
    }

    private long incrementCalculatorPart1(long diff) {
        return diff;
    }

    private long incrementCalculatorPart2(long diff) {
        return diff * (diff + 1) / 2;
    }

}
