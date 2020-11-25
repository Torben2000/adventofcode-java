package de.beachboys;

import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 implements Day {

    public String part1(List<String> input) {
        return Integer.valueOf(input.stream().mapToInt(Integer::valueOf).map(x -> x / 3 - 2).reduce(0, Integer::sum)).toString();
    }

    public String part2(List<String> input) {
        return Integer.valueOf(input.stream().mapToInt(Integer::valueOf).map(this::calculateFuel).reduce(0, Integer::sum)).toString();
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
