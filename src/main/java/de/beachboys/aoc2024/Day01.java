package de.beachboys.aoc2024;

import de.beachboys.Day;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends Day {

    private final List<Integer> leftNumbers = new ArrayList<>();
    private final List<Integer> rightNumbers = new ArrayList<>();

    public Object part1(List<String> input) {
        parseInput(input);

        long result = 0;
        leftNumbers.sort(Integer::compareTo);
        rightNumbers.sort(Integer::compareTo);
        for (int i = 0; i < leftNumbers.size(); i++) {
            result += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }
        return result;
    }

    public Object part2(List<String> input) {
        parseInput(input);

        long result = 0;
        for (int leftNumber : leftNumbers) {
            int count = 0;
            for (int rightNumber : rightNumbers) {
                if (leftNumber == rightNumber) {
                    count++;
                }
            }
            result += (long) leftNumber * count;
        }
        return result;
    }

    private void parseInput(List<String> input) {
        leftNumbers.clear();
        rightNumbers.clear();
        for (String line : input) {
            String[] split = line.split(" {3}");
            leftNumbers.add(Integer.parseInt(split[0]));
            rightNumbers.add(Integer.parseInt(split[1]));
        }
    }

}
