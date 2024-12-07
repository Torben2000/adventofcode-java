package de.beachboys.aoc2024;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day07 extends Day {

    private boolean withConcatenation;

    public Object part1(List<String> input) {
        withConcatenation = false;
        return runLogic(input);
    }

    public Object part2(List<String> input) {
        withConcatenation = true;
        return runLogic(input);
    }

    private long runLogic(List<String> input) {
        long result = 0;

        for (String line : input) {
            String[] split = line.split(": ");
            long testValue = Long.parseLong(split[0]);
            List<Long> numbers = Util.parseToLongList(split[1], " ");

            Set<Long> possibleValues = calculatePossibleValues(numbers);
            if (possibleValues.contains(testValue)) {
                result += testValue;
            }
        }

        return result;
    }

    private Set<Long> calculatePossibleValues(List<Long> numbers) {
        if (numbers.size() == 1) {
            return Set.copyOf(numbers);
        }

        long lastNumber = numbers.getLast();
        Set<Long> possibleValuesWithoutLastNumber = calculatePossibleValues(numbers.subList(0, numbers.size() - 1));

        Set<Long> possibleValues = new HashSet<>();
        for (long possibleValueWithoutLastNumber : possibleValuesWithoutLastNumber) {
            possibleValues.add(possibleValueWithoutLastNumber * lastNumber);
            possibleValues.add(possibleValueWithoutLastNumber + lastNumber);

            if (withConcatenation) {
                long multiplier = 1;
                while (multiplier <= lastNumber) {
                    multiplier *= 10;
                }
                possibleValues.add(multiplier * possibleValueWithoutLastNumber + lastNumber);
            }
        }
        return possibleValues;
    }

}
