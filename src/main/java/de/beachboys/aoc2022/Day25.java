package de.beachboys.aoc2022;

import de.beachboys.Day;

import java.util.List;

public class Day25 extends Day {

    public Object part1(List<String> input) {
        long sum = 0;
        for (String line : input) {
            sum += parseSNAFUNumber(line);
        }
        return buildSNAFUNumber(sum);
    }

    private static String buildSNAFUNumber(long number) {
        StringBuilder snafuString = new StringBuilder();
        while (number > 0) {
            long digit = number % 5;
            if (digit == 3) {
                snafuString.insert(0, "=");
                number += 5;
            } else if (digit == 4) {
                snafuString.insert(0, "-");
                number += 5;
            } else {
                snafuString.insert(0, digit);
            }
            number = number / 5;
        }
        return snafuString.toString();
    }

    private static long parseSNAFUNumber(String snafuString) {
        long number = 0;
        for (char c : snafuString.toCharArray()) {
            int digit = switch (c) {
                case '2' -> 2;
                case '1' -> 1;
                case '=' -> -2;
                case '-' -> -1;
                default -> 0;
            };
            number = number * 5 + digit;
        }
        return number;
    }

    public Object part2(List<String> input) {
        return "There is no puzzle! :-)";
    }

}
