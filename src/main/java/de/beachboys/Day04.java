package de.beachboys;

import java.util.*;

public class Day04 implements Day {

    @Override
    public String part1(List<String> input) {
        String[] splitString = input.get(0).split("-");
        int startValue = Integer.parseInt(splitString[0]);
        int endValue = Integer.parseInt(splitString[1]);
        int counter = 0;
        for (int i = startValue; i <= endValue; i++) {
            int[] digits = getDigits(i);
            if (hasAdjacentNumbers(digits) && doesNotDecrease(digits)) {
                counter++;
            }
        }

        return counter + "";
    }

    private int[] getDigits(int i) {
        int[] digits = new int[6];
        int curValue = i;
        digits[5] = curValue % 10;
        curValue = curValue / 10;
        digits[4] = curValue % 10;
        curValue = curValue / 10;
        digits[3] = curValue % 10;
        curValue = curValue / 10;
        digits[2] = curValue % 10;
        curValue = curValue / 10;
        digits[1] = curValue % 10;
        curValue = curValue / 10;
        digits[0] = curValue;
        return digits;
    }

    private boolean doesNotDecrease(int[] digits) {
        return (digits[0] <= digits[1] && digits[1] <= digits[2] && digits[2] <= digits[3] && digits[3] <= digits[4] && digits[4] <= digits[5]);
    }

    private boolean hasAdjacentNumbers(int[] digits) {
        return (digits[0] == digits[1] || digits[1] == digits[2] || digits[2] == digits[3] || digits[3] == digits[4] || digits[4] == digits[5]);
    }

    private boolean hasAdjacentNumbers2(int[] digits) {
        return (digits[0] == digits[1] && digits[1] != digits[2]|| digits[1] == digits[2] && digits[0] != digits[1]  && digits[2] != digits[3] || digits[2] == digits[3]  && digits[1] != digits[2]  && digits[3] != digits[4] || digits[3] == digits[4]  && digits[2] != digits[3]  && digits[4] != digits[5] || digits[4] == digits[5] && digits[3] != digits[4]);
    }

    @Override
    public String part2(List<String> input) {
        String[] splitString = input.get(0).split("-");
        int startValue = Integer.parseInt(splitString[0]);
        int endValue = Integer.parseInt(splitString[1]);
        int counter = 0;
        for (int i = startValue; i <= endValue; i++) {
            int[] digits = getDigits(i);
            if (hasAdjacentNumbers2(digits) && doesNotDecrease(digits)) {
                counter++;
            }
        }

        return counter + "";
    }
}
