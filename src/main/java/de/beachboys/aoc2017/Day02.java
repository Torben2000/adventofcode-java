package de.beachboys.aoc2017;

import de.beachboys.Day;
import de.beachboys.Util;

import java.util.List;
import java.util.function.Function;

public class Day02 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, this::getRowValuePart1);
    }

    public Object part2(List<String> input) {
        return runLogic(input, this::getRowValuePart2);
    }

    private int runLogic(List<String> input, Function<List<Integer>, Integer> getRowValue) {
        int sum = 0;
        for (String row : input) {
            List<Integer> rowValues = Util.parseToIntList(row, "\t");
            sum += getRowValue.apply(rowValues);
        }
        return sum;
    }

    private Integer getRowValuePart2(List<Integer> rowValues) {
        for (Integer value1 : rowValues) {
            for (Integer value2 : rowValues) {
                if (!value1.equals(value2) && value1 % value2 == 0) {
                    return value1 / value2;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private Integer getRowValuePart1(List<Integer> rowValues) {
        int rowMax = rowValues.stream().mapToInt(Integer::intValue).max().orElseThrow();
        int rowMin = rowValues.stream().mapToInt(Integer::intValue).min().orElseThrow();
        return rowMax - rowMin;
    }

}
