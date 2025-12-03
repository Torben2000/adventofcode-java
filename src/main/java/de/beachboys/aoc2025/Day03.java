package de.beachboys.aoc2025;

import de.beachboys.Day;

import java.util.List;

public class Day03 extends Day {

    public Object part1(List<String> input) {
        return runLogic(input, 2);
    }

    public Object part2(List<String> input) {
        return runLogic(input, 12);
    }

    private static long runLogic(List<String> input, int numDigits) {
        long result = 0;
        for (String line : input) {
            StringBuilder joltageAsString = new StringBuilder();
            int indexOfPreviousBattery = -1;
            int indexOfCurrentBattery = -1;
            for (int i = 0; i < numDigits; i++) {
                int maxBattery = 0;
                for (int j = indexOfPreviousBattery + 1; j <= line.length() - numDigits + i; j++) {
                    if (line.charAt(j) > maxBattery) {
                        maxBattery = line.charAt(j);
                        indexOfCurrentBattery = j;
                    }
                }
                indexOfPreviousBattery = indexOfCurrentBattery;
                indexOfCurrentBattery = -1;
                joltageAsString.append((char) maxBattery);
            }
            result += Long.parseLong(joltageAsString.toString());
        }
        return result;
    }

}
