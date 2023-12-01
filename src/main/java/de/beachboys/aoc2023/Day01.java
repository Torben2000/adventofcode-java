package de.beachboys.aoc2023;

import de.beachboys.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends Day {

    private static final Map<String, Integer> digitMap = new HashMap<>();

    public Object part1(List<String> input) {
        return runLogic(input, false);
    }

    public Object part2(List<String> input) {
        return runLogic(input, true);
    }

    private static long runLogic(List<String> input, boolean withSpelledOutDigits) {
        if (withSpelledOutDigits) {
            initMap();
        }

        long result = 0;

        for (String line : input) {
            int firstDigitInLine = -1;
            int lastDigitInLine = -1;
            for (int i = 0; i < line.length(); i++) {
                try {
                    lastDigitInLine = Integer.parseInt(line.substring(i, i + 1));
                } catch (NumberFormatException e) {
                    if (withSpelledOutDigits) {
                        String subString = line.substring(0, i + 1);
                        for (String digit : digitMap.keySet()) {
                            if (subString.endsWith(digit)) {
                                lastDigitInLine = digitMap.get(digit);
                                break;
                            }
                        }
                    }
                }
                if (firstDigitInLine == -1) {
                    firstDigitInLine = lastDigitInLine;
                }

            }
            result += Integer.parseInt(firstDigitInLine + String.valueOf(lastDigitInLine));
        }

        return result;
    }

    private static void initMap() {
        digitMap.clear();
        digitMap.put("one", 1);
        digitMap.put("two", 2);
        digitMap.put("three", 3);
        digitMap.put("four", 4);
        digitMap.put("five", 5);
        digitMap.put("six", 6);
        digitMap.put("seven", 7);
        digitMap.put("eight", 8);
        digitMap.put("nine", 9);
    }

}
