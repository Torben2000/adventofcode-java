package de.beachboys.aoc2021;

import de.beachboys.Day;

import java.util.List;
import java.util.stream.Collectors;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        List<Integer> intList = input.stream().map(Integer::valueOf).collect(Collectors.toList());

        int counter = 0;
        int previousValue = Integer.MAX_VALUE;
        for (Integer currentValue : intList) {
            if (currentValue > previousValue) {
                counter++;
            }
            previousValue = currentValue;
        }
        return counter;
    }

    public Object part2(List<String> input) {
         List<Integer> intList = input.stream().map(Integer::valueOf).collect(Collectors.toList());

        int counter = 0;
        int previousValue = Integer.MAX_VALUE;
        for (int i = 2; i < intList.size(); i++) {
            int currentValue = intList.get(i) + intList.get(i - 1) + intList.get(i - 2);
            if (currentValue > previousValue) {
                counter++;
            }
            previousValue = currentValue;
        }
        return counter;
    }

}
