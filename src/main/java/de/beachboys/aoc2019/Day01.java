package de.beachboys.aoc2019;

import de.beachboys.Day;

import java.util.List;

public class Day01 extends Day {

    public Object part1(List<String> input) {
        return input.stream().mapToInt(Integer::valueOf).map(x -> x / 3 - 2).reduce(0, Integer::sum);
    }

    public Object part2(List<String> input) {
        return input.stream().mapToInt(Integer::valueOf).map(this::calculateFuel).reduce(0, Integer::sum);
    }

    private int calculateFuel(int i) {
        int totalFuel = 0;
        int currentValue = i;
        while (true) {
            currentValue = currentValue / 3 - 2;
            if (currentValue <= 0)
                break;
            totalFuel += currentValue;
        }
        return totalFuel;
    }


}
