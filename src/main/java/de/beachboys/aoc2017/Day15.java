package de.beachboys.aoc2017;

import de.beachboys.Day;

import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends Day {

    private static final Pattern INPUT_PATTERN = Pattern.compile("Generator ([AB]) starts with ([0-9]+)");

    public Object part1(List<String> input) {
        return runLogic(input, this::getNewAPart1, this::getNewBPart1, 40000000);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::getNewAPart2, this::getNewBPart2, 5000000);
    }

    private int runLogic(List<String> input, LongUnaryOperator getA, LongUnaryOperator getB, int cycles) {
        int bitCutoff = (int) Math.pow(2, 16);
        long a = getInitialValue(input.get(0));
        long b = getInitialValue(input.get(1));
        int matchCount = 0;
        for (int i = 0; i < cycles; i++) {
            a = getA.applyAsLong(a);
            b = getB.applyAsLong(b);
            if (a % bitCutoff == b % bitCutoff) {
                matchCount++;
            }
        }
        return matchCount;
    }

    private long getInitialValue(String inputLine) {
        Matcher m = INPUT_PATTERN.matcher(inputLine);
        if (m.matches()) {
            return Long.parseLong(m.group(2));
        }
        throw new IllegalArgumentException();
    }

    private long getNewAPart1(long a) {
        return getNewValuePart1(a, 16807);
    }

    private long getNewBPart1(long b) {
        return getNewValuePart1(b, 48271);
    }

    private long getNewValuePart1(long oldValue, int i) {
        return (oldValue * i) % 2147483647;
    }

    private long getNewAPart2(long a) {
        return getNewValuePart2(a, this::getNewAPart1, 4);
    }

    private long getNewBPart2(long b) {
        return getNewValuePart2(b, this::getNewBPart1, 8);
    }

    private long getNewValuePart2(long oldValue, LongUnaryOperator getNewValuePart1, int multiplesCheck) {
        long newValue = oldValue;
        do {
            newValue = getNewValuePart1.applyAsLong(newValue);
        } while (newValue % multiplesCheck != 0);
        return newValue;
    }

}
